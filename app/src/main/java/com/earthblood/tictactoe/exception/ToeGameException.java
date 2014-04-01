package com.earthblood.tictactoe.exception;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ToeGameException extends Throwable {

    private String message;

    public ToeGameException(String s) {
        this.message = s;
    }

    @Override
    public String toString() {
        return "ToeGameException{" +
                "message='" + message + '\'' +
                '}';
    }
}
