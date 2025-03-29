package org.karthik.skillstore.exceptions;

import org.karthik.skillstore.models.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {


    @Override
    public Response toResponse(BadRequestException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),400);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
     }
}
