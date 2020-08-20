package me.lynx.harrow.util.exception;

public class ChildCommandAlreadyRegisteredException extends Exception {

    public ChildCommandAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }

}