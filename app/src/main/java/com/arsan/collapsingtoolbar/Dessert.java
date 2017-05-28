package com.arsan.collapsingtoolbar;

/**
 * Created by root on 28/05/17.
 */

public class Dessert {
    private char letter;
    private String name;
    private String desc;

    public Dessert(){}

    public Dessert(char letter, String name, String desc) {
        this.letter = letter;
        this.name = name;
        this.desc = desc;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
