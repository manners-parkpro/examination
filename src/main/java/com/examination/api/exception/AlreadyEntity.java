package com.examination.api.exception;

public class AlreadyEntity extends Exception {
    public AlreadyEntity(String entityName) {
        super(entityName);
    }
}
