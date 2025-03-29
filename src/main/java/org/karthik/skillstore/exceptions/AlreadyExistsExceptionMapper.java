package org.karthik.skillstore.exceptions;

import org.karthik.skillstore.models.ErrorMessage;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyExistsExceptionMapper implements ExceptionMapper<AlreadyExistsException> {


    @Override
    public Response toResponse(AlreadyExistsException exception) {
        ErrorMessage errorMessage  = new ErrorMessage(exception.getMessage(), 409);
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
    }
}
