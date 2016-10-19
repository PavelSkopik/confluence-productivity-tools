package com.skopik.confluence.plugins.productivity.rest;

import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.api.Settings;
import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;
import com.skopik.confluence.plugins.productivity.model.AsynchronousTask;
import com.skopik.confluence.plugins.productivity.model.OperationResult;
import com.skopik.confluence.plugins.productivity.page.PageOperationType;
import com.skopik.confluence.plugins.productivity.page.PageOperationsFactory;
import com.skopik.confluence.plugins.productivity.rest.dto.OperationResultJaxb;
import com.skopik.confluence.plugins.productivity.rest.dto.SettingsJaxb;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/operations")
public class PageOperationsResource implements AsynchronousTask {

    @Autowired
    PageOperationsFactory pageOperationsFactory;

    @PUT
    @Path("/page")
    public Response performOperation(SettingsJaxb settingsJaxb) throws UnsupportedPageOperationException {
        Operation operation = pageOperationsFactory.get(new Settings(settingsJaxb));
        OperationResult result = (OperationResult) operation.run();

        return Response.ok().entity(new OperationResultJaxb(result)).build();
    }

}
