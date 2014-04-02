package com.earthblood.tictactoe.util;

import com.earthblood.tictactoe.R;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public enum GameBox {
    ONE(R.id.box_1),
    TWO(R.id.box_2),
    THREE(R.id.box_3),
    FOUR(R.id.box_4),
    FIVE(R.id.box_5),
    SIX(R.id.box_6),
    SEVEN(R.id.box_7),
    EIGHT(R.id.box_8),
    NINE(R.id.box_9);

    private int layoutBoxId;

    private GameBox(int layoutBoxId){
        this.layoutBoxId = layoutBoxId;
    }

    public int layoutBoxId() {
        return layoutBoxId;
    }

    public int boxPosition(){
        return ordinal() +1;
    }
    public static GameBox byLayoutId(int id) {
        for (GameBox gameBox : GameBox.values()) {
            if(gameBox.ordinal() +1 == id){
                return gameBox;
            }
        }
        return null;
    }
    public static GameBox byBoxPosition(int i){
        for (GameBox gameBox : GameBox.values()) {
            if(gameBox.ordinal() +1 == i){
                return gameBox;
            }
        }
        return null;
    }
}
