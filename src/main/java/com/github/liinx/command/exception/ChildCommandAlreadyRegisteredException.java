package com.github.liinx.command.exception;

public class ChildCommandAlreadyRegisteredException extends  Exception {

    public ChildCommandAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }

}