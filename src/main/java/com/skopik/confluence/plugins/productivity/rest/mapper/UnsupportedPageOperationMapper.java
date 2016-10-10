package com.skopik.confluence.plugins.productivity.rest.mapper;

import com.skopik.confluence.plugins.productivity.exception.UnsupportedPageOperationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnsupportedPageOperationMapper implements ExceptionMapper<UnsupportedPageOperationException> {

    @Override
    public Response toResponse(UnsupportedPageOperationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
