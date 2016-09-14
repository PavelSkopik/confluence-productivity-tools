package com.skopik.confluence.plugins.productivity.model;

import java.util.List;

public class PageData {

    private String body;
    private List<String> attachmentNames;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getAttachmentNames() {
        return attachmentNames;
    }

    public void setAttachmentNames(List<String> attachmentNames) {
        this.attachmentNames = attachmentNames;
    }
}
