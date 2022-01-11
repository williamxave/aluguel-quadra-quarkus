package br.com.william.handlers;

import br.com.william.enums.FieldErrors;
import br.com.william.handlers.utils.ErrorsCaught;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        var error =
                new ErrorsCaught(Response.Status.BAD_REQUEST.getStatusCode(),
                        "Validation error", System.currentTimeMillis());
        error.addErros(FieldErrors.AUTHENTICATION_EXCEPTION.name(), e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(error).build();
    }
}