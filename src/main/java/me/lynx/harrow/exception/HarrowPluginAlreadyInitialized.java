package me.lynx.harrow.exception;

public class HarrowPluginAlreadyInitialized extends Exception {

    public HarrowPluginAlreadyInitialized(String errorMessage) {
        super(errorMessage);
    }
}