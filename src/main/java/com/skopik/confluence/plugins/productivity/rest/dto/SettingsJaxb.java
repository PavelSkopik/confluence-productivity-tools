package com.skopik.confluence.plugins.productivity.rest.dto;

import com.skopik.confluence.plugins.productivity.page.PageOperationType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SettingsJaxb {

    @XmlElement
    private long pageId;
    @XmlElement
    private PageOperationType operationType;
    @XmlElement
    private boolean deleteJoinedPages;

    public SettingsJaxb(){

    }

    public long getPageId() {
        return pageId;
    }

    public PageOperationType getOperationType() {
        return operationType;
    }

    public boolean isDeleteJoinedPages() {
        return deleteJoinedPages;
    }
}
