package br.com.william.handlers;

import br.com.william.domain.owner.dtos.OwnerDto;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

public class BadRequestExceptionCustom extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    private Set<ConstraintViolation<OwnerDto>> messages;

    public BadRequestExceptionCustom(Set<ConstraintViolation<OwnerDto>> messages) {
        this.messages = messages;
    }

    public Set<ConstraintViolation<OwnerDto>> getMessages() {
        return messages;
    }
}
