package com.skopik.confluence.plugins.productivity.rest.dto;

import com.skopik.confluence.plugins.productivity.api.PluginSettings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PluginSettingsJaxb {

    @XmlElement
    private int maximumPageMergeCount;

    public PluginSettingsJaxb() {
    }

    public PluginSettingsJaxb(PluginSettings pluginSettings) {
        this.maximumPageMergeCount = pluginSettings.getMaximumPageMergeCount();
    }

    public PluginSettings getSettings() {
        PluginSettings settings = new PluginSettings();
        settings.setMaximumPageMergeCount(this.maximumPageMergeCount);
        return settings;
    }

}
