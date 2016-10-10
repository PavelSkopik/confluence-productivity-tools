package com.skopik.confluence.plugins.productivity.model;

import java.util.ArrayList;
import java.util.List;

public class PageData {

    private String id;
    private String parentId;
    private String title;
    private String body = "";
    private int level;
    private int position;
    private PageData parent;
    private List<String> attachmentNames;
    private List<PageData> children = new ArrayList<>();

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

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<PageData> getChildren() {
        return children;
    }

    public void addChild(PageData page) {
        children.add(page);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public PageData getParent() {
        return parent;
    }

    public void setParent(PageData parent) {
        this.parent = parent;
    }
}
