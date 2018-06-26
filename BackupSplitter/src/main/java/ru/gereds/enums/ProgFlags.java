package ru.gereds.enums;

public enum ProgFlags {
    PROGEND("M30"), COMMENTSTART("("), COMMENTEND(")"), PROGSTART("O");
    public String flag;
    ProgFlags(String flag) {
        this.flag = flag;
    }
}