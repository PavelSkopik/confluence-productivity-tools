package com.skopik.confluence.plugins.productivity.rest;

import com.skopik.confluence.plugins.productivity.dao.PluginSettings;
import com.skopik.confluence.plugins.productivity.dao.PluginSettingsDao;
import com.skopik.confluence.plugins.productivity.rest.dto.PluginSettingsJaxb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
public class PluginSettingsResource {

    private static final Logger logger = LoggerFactory.getLogger(PluginSettingsResource.class);

    @Autowired
    private PluginSettingsDao pluginSettingsDao;

    @GET
    public Response getSettings() {
        PluginSettings pluginSettings = pluginSettingsDao.get();
        return Response.ok().entity(new PluginSettingsJaxb(pluginSettings)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveSettings(PluginSettingsJaxb settingsJaxb) {
        pluginSettingsDao.save(settingsJaxb.getSettings());
        return Response.ok().build();
    }


}
