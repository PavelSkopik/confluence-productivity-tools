package com.skopik.confluence.plugins.productivity.rest;

import com.skopik.confluence.plugins.productivity.api.Operation;
import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;
import com.skopik.confluence.plugins.productivity.model.AsynchronousTask;
import com.skopik.confluence.plugins.productivity.page.PageOperationType;
import com.skopik.confluence.plugins.productivity.page.PageOperationsFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/pages")
public class PageOperationsResource implements AsynchronousTask {

    @Autowired
    PageOperationsFactory pageOperationsFactory;

    @PUT
    @Path("/{type}/{pageId}")
    public Response performOperation(@PathParam("type") String type, @PathParam("pageId") long pageId) throws UnsupportedPageOperationException {
        Operation<Boolean> operation = pageOperationsFactory.get(null);




        return null;
    }


}
