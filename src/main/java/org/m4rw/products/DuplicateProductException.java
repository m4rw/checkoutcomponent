package org.m4rw.products;

public class DuplicateProductException extends RuntimeException {
    DuplicateProductException(String message){
        super(message);
    }
}
