package com.github.liinx.command.exception;

public class CommandAlreadyRegisteredException extends Exception {

    public CommandAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }

}
