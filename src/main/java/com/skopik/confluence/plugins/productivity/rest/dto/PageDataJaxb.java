package com.skopik.confluence.plugins.productivity.rest.dto;

import com.skopik.confluence.plugins.productivity.model.PageData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PageDataJaxb {

    @XmlElement
    private long originalPageId;

    @XmlElement
    private long newPageId;

    @XmlElement
    private String title;

    public PageDataJaxb(PageData pageData) {
        this.originalPageId = pageData.getOriginalPageId();
        this.newPageId = pageData.getNewPageId();
        this.title = pageData.getTitle();
    }

}
