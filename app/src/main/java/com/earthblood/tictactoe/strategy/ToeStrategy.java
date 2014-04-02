package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public interface ToeStrategy {

    public static final int[] ALL_BOXES = {1,2,3,4,5,6,7,8,9};
    public static final int[] DEFAULT_ZEROS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int getBoxId();
    public GameSymbol getSymbol();

}
