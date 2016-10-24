package com.skopik.confluence.plugins.productivity.dao;


import com.skopik.confluence.plugins.productivity.api.PluginSettings;

/**
 *
 */
public interface PluginSettingsDao {

    /**
     *
     * @param pluginSettings
     */
    void save(PluginSettings pluginSettings);

    /**
     *
     * @return
     */
    PluginSettings get();

}
