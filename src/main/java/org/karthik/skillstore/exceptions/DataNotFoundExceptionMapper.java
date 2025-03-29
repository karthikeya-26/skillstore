package org.karthik.skillstore.exceptions;

import org.karthik.skillstore.models.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException exception) {
        System.out.println("DataNotFoundExceptionMapper :"+exception.getMessage());
        ErrorMessage errorMessage  = new ErrorMessage(exception.getMessage(), 404);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage).build();
    }
}
