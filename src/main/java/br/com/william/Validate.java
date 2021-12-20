package br.com.william;

public interface Validate<T> {
    void validate(T object) throws RuntimeException;
}
