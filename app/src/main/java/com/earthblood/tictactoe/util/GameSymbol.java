package com.earthblood.tictactoe.util;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public enum GameSymbol {
    X(1,"X"),
    O(2,"O");

    private int id;
    private String value;

    private GameSymbol(int id, String value){
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static GameSymbol byId(int id) {
        for (GameSymbol gameSymbol : values()) {
            if(gameSymbol.getId() == id){
                return gameSymbol;
            }
        }
        return null;
    }
}
