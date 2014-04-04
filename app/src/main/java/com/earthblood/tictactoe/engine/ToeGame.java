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
import com.earthblood.tictactoe.util.GamePreference;
import com.earthblood.tictactoe.util.GameStatus;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;
import com.google.inject.Inject;

public class ToeGame {

    private PreferenceHelper preferenceHelper;

    @Inject
    public ToeGame(PreferenceHelper preferenceHelper){
        this.preferenceHelper = preferenceHelper;
    }

    public String titleHack(String appName, String statusMessage){
        return "<font color=#CD5C5C><b>" + appName  + "</b></font><font color=#F5F5F5>&nbsp;&nbsp;&nbsp;" + statusMessage +"</font>";
    }

    public void setSkill(Skill skill){
        preferenceHelper.putPreference(skill.getId(), GamePreference.PREF_SKILL_ID.getKey(), Context.MODE_PRIVATE);
    }
    public void setNumOfPlayers(int numOfPlayers){
        preferenceHelper.putPreference(numOfPlayers, GamePreference.PREF_NUMBER_OF_PLAYERS.getKey(), Context.MODE_PRIVATE);
    }
    public Skill getSkill(){
        return Skill.byId(preferenceHelper.getPreference(GamePreference.PREF_SKILL_ID.getKey(), Skill.EASY.getId()));
    }
    public int getNumOfPlayers(){
        return preferenceHelper.getPreference(GamePreference.PREF_NUMBER_OF_PLAYERS.getKey(), 1);
    }
    public void setTurn(GameSymbol gameSymbol) {
        preferenceHelper.putPreference(gameSymbol.getId(), GamePreference.PREF_TURN.getKey(), Context.MODE_PRIVATE);
    }
    public GameSymbol getTurn(){
        return GameSymbol.byId(preferenceHelper.getPreference(GamePreference.PREF_TURN.getKey(), GameSymbol.X.getId()));
    }
    public boolean inProgress(){
        return preferenceHelper.getPreference(GamePreference.PREF_GAME_PROGRESS.getKey(), GameStatus.GAME_OVER.getStatus()) == GameStatus.GAME_IN_PROGRESS.getStatus();
    }
    public void setGameOver(){
        preferenceHelper.putPreference(GameStatus.GAME_OVER.getStatus(), GamePreference.PREF_GAME_PROGRESS.getKey(), Context.MODE_PRIVATE);
    }
    public void setGameInProgess(){
        preferenceHelper.putPreference(GameStatus.GAME_IN_PROGRESS.getStatus(), GamePreference.PREF_GAME_PROGRESS.getKey(), Context.MODE_PRIVATE);
    }
    public void advanceTurn(GameSymbol gameSymbol) {
        preferenceHelper.putPreference(gameSymbol == GameSymbol.X ? GameSymbol.O.getId() : GameSymbol.X.getId() , GamePreference.PREF_TURN.getKey(), Context.MODE_PRIVATE);
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
