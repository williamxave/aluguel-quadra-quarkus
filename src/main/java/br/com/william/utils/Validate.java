package br.com.william.utils;

public interface Validate<T> {
    void validate(T object) throws RuntimeException;
}
