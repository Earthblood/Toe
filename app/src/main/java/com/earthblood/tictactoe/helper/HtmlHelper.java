package com.earthblood.tictactoe.helper;

import android.text.Html;
import android.text.Spanned;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class HtmlHelper {

    public Spanned fromHtml(String s){
        return Html.fromHtml(s);
    }
}
