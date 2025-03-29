package org.karthik.skillstore.exceptions;

import org.karthik.skillstore.models.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 401);
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
    }
}
