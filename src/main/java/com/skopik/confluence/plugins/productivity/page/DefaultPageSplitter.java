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
     *
     * @param document
     * @return
     */
    private List<PageData> split(Document document) {
        List<PageData> pages = new ArrayList<>();
        List<Element> children = document.getRootElement().getChildren();
        PageData currentPage = null;

        for (int i = 0; i < children.size(); i++) {
            if (isHeading(children.get(i))) {
                int level = getHeadingLevel(children.get(i));

                currentPage = new PageData();
                currentPage.setPosition(i);
                currentPage.setLevel(level);
                currentPage.setTitle(children.get(i).getValue());
                currentPage.setId(UUID.randomUUID().toString());

                pages.add(currentPage);
            } else {
                appendContentToBody(currentPage, children.get(i));
            }
        }

        return pages;
    }

    /**
     * @param storageFormat
     *
     * @return
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

    private void appendContentToBody(PageData pageData, Element e) {
        if (pageData != null)
            pageData.setBody(pageData.getBody() + xmlOutputter.outputString(e));
    }

    /**
     * @param e
     *
     * @return
     */
    private int getHeadingLevel(Element e) {
        Matcher m = getMatcher(e);
        return m.find() ? Integer.valueOf(m.group(1)) : -1;
    }

    private IteratorIterable<Element> getAttachmentsIterator(Element e) {
        Filter<Element> attachmentsFilter = (Filter<Element>) Filters.element("attachment", Namespace.getNamespace(RESOURCE_INDENTIFIER_NS_PREFIX, RESOURCE_INDENTIFIER_NS_PREFIX)).or(Filters.element("structured-macro", Namespace.getNamespace(STORAGE_FORMAT_NS_PREFIX, STORAGE_FORMAT_NS_PREFIX)));
        return e.getDescendants(attachmentsFilter);
    }

    /**
     *
     * @param e
     * @return
     */
    private List<String> getAttachments(Element e) {
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
     *
     * @param e
     * @param macroName
     * @return
     */
    private boolean isMacro(Element e, String macroName) {
        Attribute name = e.getAttribute("name", Namespace.getNamespace(STORAGE_FORMAT_NS_PREFIX, STORAGE_FORMAT_NS_URI));
        return name != null && e.getName().equals(macroName);
    }

    /**
     *
     * @param e
     * @return
     */
    private Matcher getMatcher(Element e) {
        return headingRegex.matcher(e.getName());
    }

    /**
     * @param e
     *
     * @return
     */
    private boolean isHeading(Element e) {
        return getMatcher(e).find();
    }


}
