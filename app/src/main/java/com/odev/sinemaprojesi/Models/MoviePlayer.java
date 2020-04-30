package com.odev.sinemaprojesi.Models;

public class MoviePlayer {
    String name;
    String character;

    public MoviePlayer(String name, String character) {
        this.name = name;
        this.character = character;
    }

    public MoviePlayer() {
    }

    @Override
    public String toString() {
        return "MoviePlayer{" +
                "name='" + name + '\'' +
                ", character='" + character + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
