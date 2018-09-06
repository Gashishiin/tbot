package com.randominfo.bot.domain;

public enum Commands {

    TRANSLATE("/translate"),
    SWITCH("/switch"),
    START("/start");


    private final String name;

    Commands(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
