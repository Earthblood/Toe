/**
 * @author John Piser developer@earthblood.com
 *
 * Copyright (C) 2014 EARTHBLOOD, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.earthblood.tictactoe.engine;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;
import com.google.inject.Inject;

public class ToeGame {

    public static final String PREF_SKILL_ID = "PREF_SKILL_ID";
    public static final String PREF_NUMBER_OF_PLAYERS = "PREF_NUMBER_OF_PLAYERS";
    public static final String PREF_TURN = "PREF_TURN";
    public static final String PREF_GAME_PROGRESS = "PREF_GAME_PROGRESS";
    public static final int GAME_OVER = 0;
    public static final int GAME_IN_PROGRESS = 1;

    private PreferenceHelper preferenceHelper;

    @Inject
    public ToeGame(PreferenceHelper preferenceHelper){
        this.preferenceHelper = preferenceHelper;
    }

    public String titleHack(String appName, String statusMessage){
        return "<font color=#CD5C5C><b>" + appName  + "</b></font><font color=#F5F5F5>&nbsp;&nbsp;&nbsp;" + statusMessage +"</font>";
    }

    public void setSkill(Skill skill){
        preferenceHelper.putPreference(skill.getId(), PREF_SKILL_ID, Context.MODE_PRIVATE);
    }
    public void setNumOfPlayers(int numOfPlayers){
        preferenceHelper.putPreference(numOfPlayers, PREF_NUMBER_OF_PLAYERS, Context.MODE_PRIVATE);
    }
    public Skill getSkill(){
        return Skill.byId(preferenceHelper.getPreference(PREF_SKILL_ID, Skill.EASY.getId()));
    }
    public int getNumOfPlayers(){
        return preferenceHelper.getPreference(PREF_NUMBER_OF_PLAYERS, 1);
    }
    public void setTurn(GameSymbol gameSymbol) {
        preferenceHelper.putPreference(gameSymbol.getId(), PREF_TURN, Context.MODE_PRIVATE);
    }
    public GameSymbol getTurn(){
        return GameSymbol.byId(preferenceHelper.getPreference(PREF_TURN, GameSymbol.X.getId()));
    }
    public boolean inProgress(){
        return preferenceHelper.getPreference(PREF_GAME_PROGRESS, GAME_OVER) == GAME_IN_PROGRESS;
    }
    public void setGameOver(){
        preferenceHelper.putPreference(GAME_OVER, PREF_GAME_PROGRESS, Context.MODE_PRIVATE);
    }
    public void setGameInProgess(){
        preferenceHelper.putPreference(GAME_IN_PROGRESS, PREF_GAME_PROGRESS, Context.MODE_PRIVATE);
    }
    public void advanceTurn(GameSymbol gameSymbol) {
        preferenceHelper.putPreference(gameSymbol == GameSymbol.X ? GameSymbol.O.getId() : GameSymbol.X.getId() , PREF_TURN, Context.MODE_PRIVATE);
    }

    public void chooseBox(ContentResolver contentResolver, ToeStrategy strategy) {

        ContentValues values = new ContentValues();
        values.put(GameDatabaseHelper.COLUMN_GAME_BOX_ID, strategy.getBoxId());
        values.put(GameDatabaseHelper.COLUMN_GAME_SYMBOL_ID,strategy.getSymbol().getId());
        contentResolver.insert(GameContentProvider.CONTENT_URI, values);
        advanceTurn(strategy.getSymbol());
    }

    public void reset(ContentResolver contentResolver) {
        setGameOver();
        contentResolver.delete(GameContentProvider.CONTENT_URI, null, null);
    }

    public boolean isOnePlayerGame() {
        return getNumOfPlayers() == 1;
    }

    public boolean symbolIsAndroid(GameSymbol symbol){
        return isOnePlayerGame() && symbol == GameSymbol.O;
    }

    public boolean isAndroidTurn() {
        return isOnePlayerGame() && symbolIsAndroid(getTurn());
    }

    public void generateAndroidTurn(ContentResolver contentResolver, int[] selectedXBoxIds, int[] selectedOBoxIds) {
        ToeStrategy strategy = getSkill().strategy(selectedXBoxIds, selectedOBoxIds, GameSymbol.O);
        chooseBox(contentResolver, strategy);
    }
}
