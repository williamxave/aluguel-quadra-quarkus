package br.com.william.handlers.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorsCaught extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ErrorsCaught(Integer status, String message, long timeStamp) {
        super(status, message, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErros(String fieldError, String messages){
        this.errors.add(new FieldMessage(fieldError, messages));
    }
}
