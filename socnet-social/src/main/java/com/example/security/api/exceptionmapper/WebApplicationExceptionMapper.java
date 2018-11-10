package com.example.security.api.exceptionmapper;

import com.example.security.api.errors.ApiErrorDetails;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper for {@link WebApplicationException}s.
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(WebApplicationException exception) {

        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(exception.getResponse().getStatus());
        errorDetails.setTitle(Response.Status.fromStatusCode(exception.getResponse().getStatus()).getReasonPhrase());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(exception.getResponse().getStatus()).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
    }
}