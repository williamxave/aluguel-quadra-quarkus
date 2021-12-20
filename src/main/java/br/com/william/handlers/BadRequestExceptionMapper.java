package br.com.william.handlers;

import br.com.william.dtos.OwnerDto;
import br.com.william.handlers.utils.ErrorsCaught;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestExceptionCustom> {
    @Override
    public Response toResponse(BadRequestExceptionCustom e) {
        var error =
                new ErrorsCaught(Response.Status.BAD_REQUEST.getStatusCode(),
                        "Validation error", System.currentTimeMillis());
        for(ConstraintViolation<OwnerDto> x : e.getMessages()){
            error.addErros(x.getPropertyPath().toString(), x.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
