package com.skopik.confluence.plugins.productivity.page;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.xml.XhtmlEntityResolver;
import com.skopik.confluence.plugins.productivity.api.PageSplitter;
import com.skopik.confluence.plugins.productivity.model.PageData;
import org.jdom2.*;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultPageSplitter implements PageSplitter {

    private static final Logger log = LoggerFactory.getLogger(DefaultPageSplitter.class);

    private static final String STORAGE_FORMAT_NS_PREFIX = "ac";
    private static final String STORAGE_FORMAT_NS_URI = "http://www.confluence/storageFormat";
    private static final String RESOURCE_INDENTIFIER_NS_URI = "http://www.confluence/resourceIndentifiers";
    private static final String RESOURCE_INDENTIFIER_NS_PREFIX = "ri";
    private static final String WRAPPER_START_TAG = "<storage-format xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ac=\"http://www.confluence/storageFormat\" xmlns:ri=\"http://www.confluence/resourceIndentifiers\">";
    private static final String WRAPPER_END_TAG = "</storage-format>";
    private static final String HEADING_REGEX_PATTERN = "h([0-6])";

    private Pattern headingRegex;
    private XhtmlEntityResolver xhtmlResolver;
    private String entityDTD;
    private XMLOutputter xmlOutputter;
    private PageHierarchyBuilder hiearchyBuilder;

    @PostConstruct
    public void init() {
        this.xhtmlResolver = new XhtmlEntityResolver();
        this.entityDTD = xhtmlResolver.createDTD();
        this.xmlOutputter = new XMLOutputter();
        this.headingRegex = Pattern.compile(HEADING_REGEX_PATTERN);
        this.hiearchyBuilder = new PageHierarchyBuilder();
    }

    @Override
    public List<PageData> split(Page page) {
        return this.hiearchyBuilder.build(split(getDocument(page.getBodyAsString())));
    }

    /**
     * Splits a page into multiple pages. Split pages are represented by a list of {@link PageData objects}.
     *
     * @param document Page content as JDOM2 document.
     *
     * @return List of {@link PageData objects}.
     */
    private List<PageData> split(Document document) {
        List<PageData> pages = new ArrayList<>();
        List<Element> children = document.getRootElement().getChildren();
        PageData currentPage = null;

        for (int i = 0; i < children.size(); i++) {
            Element e = children.get(i);

            if (isHeading(e)) {
                int level = getHeadingLevel(e);

                currentPage = new PageData();
                currentPage.setPosition(i);
                currentPage.setLevel(level);
                currentPage.setTitle(e.getValue());
                currentPage.setId(UUID.randomUUID().toString());

                pages.add(currentPage);
            } else {
                if (currentPage != null) {
                    appendPageContent(currentPage, e);
                    currentPage.addAttachmentNames(getAttachmentNames(e));
                }
            }
        }

        return pages;
    }

    /**
     * Builds a JDOM2 document from Confluence storage format.
     *
     * @param storageFormat Page content in storage format.
     *
     * @return JDOM2 document.
     */
    private Document getDocument(String storageFormat) {
        Document document = null;
        String xmlString = "<!DOCTYPE xml [ " + entityDTD + "]>" + WRAPPER_START_TAG + storageFormat + WRAPPER_END_TAG;
        try {
            InputSource inputSource = new InputSource(new StringReader(xmlString));
            SAXBuilder documentBuilder = new SAXBuilder();
            documentBuilder.setEntityResolver(xhtmlResolver);
            documentBuilder.setExpandEntities(true);
            document = documentBuilder.build(inputSource);
        } catch (JDOMException e) {
            log.error("Cannot build JDom2 document: ", e);
        } catch (IOException e) {
            log.error("IOException while building JDom2 document.", e);
        }

        return document;
    }

    /**
     * Appends page content to given page.
     *
     * @param pageData {@link PageData PageData object to append content to.}
     * @param e        Element to extract XML as string from.
     */
    private void appendPageContent(PageData pageData, Element e) {
        pageData.setBody(pageData.getBody() + xmlOutputter.outputString(e));
    }

    /**
     * Retrieves a heading level..
     *
     * @param e Element.
     *
     * @return Heading level.
     */
    private int getHeadingLevel(Element e) {
        Matcher m = getMatcher(e);
        return m.find() ? Integer.valueOf(m.group(1)) : -1;
    }

    /**
     * Returns attachments iterator.
     *
     * @param e Element.
     *
     * @return Iterator.
     */
    private IteratorIterable<Element> getAttachmentsIterator(Element e) {
        Filter<Element> attachmentsFilter = (Filter<Element>) Filters.element("attachment", Namespace.getNamespace(RESOURCE_INDENTIFIER_NS_PREFIX, RESOURCE_INDENTIFIER_NS_URI)).or(Filters.element("structured-macro", Namespace.getNamespace(STORAGE_FORMAT_NS_PREFIX, STORAGE_FORMAT_NS_URI)));
        return e.getDescendants(attachmentsFilter);
    }

    /**
     * Returns list of attachment names.
     *
     * @param e Element.
     *
     * @return List of attachment names.
     */
    private List<String> getAttachmentNames(Element e) {
        List<String> attachments = new ArrayList<>();
        IteratorIterable<Element> attachmentsIterator = getAttachmentsIterator(e);

        while (attachmentsIterator.hasNext()) {
            Element element = attachmentsIterator.next();
            if (element.getName().endsWith("attachment")) {
                Attribute fileName = element.getAttribute("filename", Namespace.getNamespace(RESOURCE_INDENTIFIER_NS_PREFIX, RESOURCE_INDENTIFIER_NS_URI));
                if (fileName != null)
                    attachments.add(fileName.getValue());
            } else if (element.getName().equals("structured-macro") && isMacro(element, "gliffy")) {
                attachments.add(element.getChild("parameter", Namespace.getNamespace(STORAGE_FORMAT_NS_PREFIX, STORAGE_FORMAT_NS_URI)).getText());
            }
        }
        return attachments;
    }

    /**
     * Determines if an element is a given macro.
     *
     * @param e         Element.
     * @param macroName Macro name.
     *
     * @return Boolean value.
     */
    private boolean isMacro(Element e, String macroName) {
        Attribute name = e.getAttribute("name", Namespace.getNamespace(STORAGE_FORMAT_NS_PREFIX, STORAGE_FORMAT_NS_URI));
        return name != null && name.getValue().equals(macroName);
    }

    /**
     * Returns a regex matcher.
     *
     * @param e Element.
     *
     * @return Matcher.
     */
    private Matcher getMatcher(Element e) {
        return headingRegex.matcher(e.getName());
    }

    /**
     * Determines if an element is a heading.
     *
     * @param e Element.
     *
     * @return Booelan value.
     */
    private boolean isHeading(Element e) {
        return getMatcher(e).find();
    }


}
