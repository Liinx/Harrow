package com.github.liinx.util;

public enum CallPriority {

    LOWEST(1),
    LOW(2),
    NORMAL(3),
    HIGH(4),
    HIGHEST(5);

    private int numValue;

    CallPriority(int numValue) {
        this.numValue = numValue;
    }

    public int getNumValue() {
        return numValue;
    }

    public String getName() {
        return name().substring(0,1) + name().substring(1).toLowerCase();
    }

}