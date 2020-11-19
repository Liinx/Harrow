package me.lynx.harrow.util;

public enum CallPriority {

    LOWEST(1),
    LOW(2),
    NORMAL(3),
    HIGH(4),
    HIGHEST(5);

    private int id;

    CallPriority(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name().substring(0,1) + name().substring(1).toLowerCase();
    }

}