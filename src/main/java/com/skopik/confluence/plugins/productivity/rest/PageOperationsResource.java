package com.skopik.confluence.plugins.productivity.rest;

import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;
import com.skopik.confluence.plugins.productivity.page.OperationResult;
import com.skopik.confluence.plugins.productivity.page.OperationSettings;
import com.skopik.confluence.plugins.productivity.page.PageOperation;
import com.skopik.confluence.plugins.productivity.page.PageOperationsFactory;
import com.skopik.confluence.plugins.productivity.rest.dto.OperationResultJaxb;
import com.skopik.confluence.plugins.productivity.rest.dto.SettingsJaxb;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/operations")
@Produces(MediaType.APPLICATION_JSON)
public class PageOperationsResource {

    @Autowired
    PageOperationsFactory pageOperationsFactory;

    @PUT
    @Path("/page")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response performOperation(SettingsJaxb settingsJaxb) throws UnsupportedPageOperationException {
        PageOperation operation = pageOperationsFactory.create(new OperationSettings(settingsJaxb));
        OperationResult result = (OperationResult) operation.run();

        return Response.ok().entity(new OperationResultJaxb(result)).build();
    }

}
