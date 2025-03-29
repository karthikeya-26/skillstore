package org.karthik.skillstore.exceptions;

import org.karthik.skillstore.models.ErrorMessage;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {
    @Override
    public Response toResponse(InternalServerErrorException exception) {
        ErrorMessage errorMessage = new ErrorMessage("Something went wrong, Please try again later.",500);
        return Response.serverError().entity(errorMessage).build();
    }
}
