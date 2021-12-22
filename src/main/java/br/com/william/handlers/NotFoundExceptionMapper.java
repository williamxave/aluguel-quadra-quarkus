package br.com.william.handlers;

import br.com.william.enums.FieldErrors;
import br.com.william.handlers.utils.ErrorsCaught;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        var error =
                new ErrorsCaught(Response.Status.NOT_FOUND.getStatusCode(),
                        "Object not found", System.currentTimeMillis());
        error.addErros(FieldErrors.OBJECT_NOT_FOUND.name(), e.getMessage());
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(error).build();
    }
}