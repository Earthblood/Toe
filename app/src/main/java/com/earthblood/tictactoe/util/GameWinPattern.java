package com.earthblood.tictactoe.util;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;

import java.util.Arrays;
import java.util.Random;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public enum GameWinPattern {
    ONE_ACROSS(1,2,3),
    FOUR_ACROSS(4,5,6),
    SEVEN_ACROSS(7,8,9),
    ONE_DOWN(1,4,7),
    TWO_DOWN(2,5,8),
    THREE_DOWN(3,6,9),
    ONE_DIAGONAL(1,5,9),
    THREE_DIAGONAL(3,5,7);

    private int[] boxIds;

    private GameWinPattern(int id1, int id2, int id3){
        this.boxIds = new int[]{id1, id2, id3};
    }

    public int[] getBoxIds() {
        return boxIds;
    }

    public static GameWinPattern checkForWin(int[] boxIdsForGameSymbol){
        for (GameWinPattern gameWinPattern : values()) {
            int count =0;
            for (int boxId : gameWinPattern.boxIds) {
                for (int id : boxIdsForGameSymbol) {
                    count += (id == boxId ? 1 : 0 );
                }
            }
            if(count == 3){
                return gameWinPattern;
            }
        }
        return null;
    }

    public boolean containsBoxId(int boxId) {
        for (int bId : boxIds) {
            if(bId == boxId){
                return true;
            }
        }
        return false;
    }

    public int returnFirstMatch(int[] availableBoxes, int notFound) {
        for (int boxId : boxIds) {
            for (int availableBoxId : availableBoxes) {
                if(boxId == availableBoxId){
                    return boxId;
                }
            }
        }
        return notFound;
    }
    public static GameWinPattern[] randomBunchOfWinningPatterns(int minNumberOfPatters){

        Random random = new Random();

        int max = GameWinPattern.values().length;
        int numberOfPatterns = max;

        if(minNumberOfPatters < max){
            numberOfPatterns = random.nextInt(max -minNumberOfPatters) + minNumberOfPatters;
        }
        GameWinPattern[] returnedPatterns = new GameWinPattern[numberOfPatterns];
        int[] ordinalValues = {0,1,2,3,4,5,6,7};

        int n = max - 1;
        for(int i = n; i >= 1; i--){
            int r = random.nextInt(n);
            int tmpR = ordinalValues[r];
            int tmpN = ordinalValues[n];
            ordinalValues[r] = tmpN;
            ordinalValues[n] = tmpR;
        }
        Log.d(Toe.TAG, "Ordinal values are:" + Arrays.toString(ordinalValues));

        for (int p =0; p < numberOfPatterns; p++) {
            returnedPatterns[p] = GameWinPattern.values()[ordinalValues[p]];
        }
        return returnedPatterns;
    }
}
