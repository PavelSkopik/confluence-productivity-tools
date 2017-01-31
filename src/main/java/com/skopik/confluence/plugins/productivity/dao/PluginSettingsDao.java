package com.skopik.confluence.plugins.productivity.dao;


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
