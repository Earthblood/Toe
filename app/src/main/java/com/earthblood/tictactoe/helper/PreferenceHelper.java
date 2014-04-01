package com.earthblood.tictactoe.helper;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public interface PreferenceHelper {

    public int getPreference(String key, int defaultValue);
    public void putPreference(int value, String preferenceName, int mode);

}
