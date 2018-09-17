package com.randominfo.bot.commands;

public enum CommandEnum {

    TRANSLATE("/translate"),
    SWITCH("/switch"),
    START("/start"),
    WEATHER("/weather"),
    CHANGE_CITY("/change_city");



    private final String name;

    CommandEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
