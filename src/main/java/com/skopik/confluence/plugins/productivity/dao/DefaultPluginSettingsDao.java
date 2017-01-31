package com.skopik.confluence.plugins.productivity.dao;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultPluginSettingsDao implements PluginSettingsDao {

    private static final String SETTINGS_KEY = "com.skopik.confluence.plugins.productivity";
    private PluginSettings settings;

    @Autowired
    private BandanaManager bandanaManager;

    @Override
    public void save(PluginSettings pluginSettings) {
        this.settings = pluginSettings;
        this.bandanaManager.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, pluginSettings);

    }

    @Override
    public PluginSettings get() {
        if (this.settings != null)
            return this.settings;

        this.settings = (PluginSettings) bandanaManager.getValue(new ConfluenceBandanaContext(), SETTINGS_KEY, false);

        if (this.settings == null)
            return new PluginSettings();

        return this.settings;
    }
}
