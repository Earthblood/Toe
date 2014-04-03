package com.earthblood.tictactoe.activity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayout;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.helper.HapticFeedbackHelper;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategyExplicit;
import com.earthblood.tictactoe.util.GameBox;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.GameWinPattern;
import com.google.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

@ContentView(R.layout.activity_game)
public class GameActivity extends RoboFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SORT_DIRECTION = " ASC";
    public static final int TOTAL_NUMBER_OF_BOXES = 9;


    @InjectView(R.id.game_grid_layout)             GridLayout gridLayout;
    @InjectView(R.id.message_turn_indicator_value) TextView messageTurnIndicatorValue;
    @InjectView(R.id.new_game_button)              Button newGameButton;

    @Inject ToeGame toeGame;
    @Inject HapticFeedbackHelper hapticFeedbackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleHack();
        initializeButtonFeedback();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void titleHack() {
        String gameType = toeGame.isOnePlayerGame()? toeGame.getSkill().toString() : getString(R.string.two_player);
        String title = "<font color=#CD5C5C><b>" + getString(R.string.app_name)  + "</b></font><font color=#F5F5F5>&nbsp;&nbsp;&nbsp;" + gameType +"</font>";
        setTitle(Html.fromHtml(title));
    }

    private void initializeButtonFeedback(){
        for (GameBox gameBox : GameBox.values()) {
            Button button = (Button)gridLayout.findViewById(gameBox.layoutBoxId());
            hapticFeedbackHelper.addFeedbackToButton(button, HapticFeedbackHelper.VIBE_PATTERN_SHORT,HapticFeedbackHelper.VIBE_PATTERN_NO_REPEAT);
        }
        hapticFeedbackHelper.addFeedbackToButton(newGameButton, HapticFeedbackHelper.VIBE_PATTERN_SHORT, HapticFeedbackHelper.VIBE_PATTERN_NO_REPEAT);
    }

    private void highlightWinningPattern(GameWinPattern gameWinPattern) {
        for (int boxPosition : gameWinPattern.getBoxIds()) {
            Button button = (Button)gridLayout.findViewById(GameBox.byBoxPosition(boxPosition).layoutBoxId());
            button.setBackgroundResource(toeGame.getNumOfPlayers() == 1 ? R.drawable.custom_btn_seagull : R.drawable.custom_btn_orange);
        }
    }

    private void disableAllBoxes(){
        for (GameBox gameBox : GameBox.values()) {
            Button b = (Button)gridLayout.findViewById(gameBox.layoutBoxId());
            b.setEnabled(false);
        }
    }

    private void enableOpenBoxes(int[] selectedXBoxIds, int[] selectedOBoxIds) {
        int[] selectedBoxes = ArrayUtils.addAll(selectedXBoxIds, selectedOBoxIds);
        for (GameBox gameBox : GameBox.values()) {
            if(! ArrayUtils.contains(selectedBoxes, gameBox.boxPosition())){
                Button b = (Button)gridLayout.findViewById(gameBox.layoutBoxId());
                b.setEnabled(true);
            }
        }
    }

    private void startTurn(final int[] selectedXBoxIds, final int[] selectedOBoxIds) {
        if(toeGame.isAndroidTurn()){
            disableAllBoxes();

            //** SIMULATE AI
            Toast.makeText(this, R.string.computer_thinking, Toast.LENGTH_SHORT).show();
            final Runnable r = new Runnable() {
                public void run() {
                    toeGame.generateAndroidTurn(getContentResolver(), selectedXBoxIds, selectedOBoxIds);
                    hapticFeedbackHelper.vibrate(HapticFeedbackHelper.VIBE_PATTERN_SHORT, HapticFeedbackHelper.VIBE_PATTERN_NO_REPEAT);
                    enableOpenBoxes(selectedXBoxIds, selectedOBoxIds);
                }
            };
            gridLayout.postDelayed(r, 2000);
        }
    }

    private void endTurn(int[] selectedXBoxIds, int[] selectedOBoxIds, int totalBoxesSelected) {

        GameSymbol winningSymbol = GameSymbol.X;
        boolean allBoxesFilled = totalBoxesSelected == TOTAL_NUMBER_OF_BOXES;
        GameWinPattern gameWinPattern = GameWinPattern.checkForWin(selectedXBoxIds);
        if(gameWinPattern == null){
            gameWinPattern = GameWinPattern.checkForWin(selectedOBoxIds);
            winningSymbol = GameSymbol.O;
        }
        refreshUI(allBoxesFilled, gameWinPattern, winningSymbol, selectedXBoxIds, selectedOBoxIds);
    }

    protected void refreshUI(boolean gameOverNoWinner, GameWinPattern gameWinPattern, GameSymbol winningSymbol, int[] selectedXBoxIds, int[] selectedOBoxIds) {

        if(gameWinPattern != null){
            //We Have a Winner
            hapticFeedbackHelper.vibrate(hapticFeedbackHelper.getWinningPattern(toeGame.symbolIsAndroid(winningSymbol)), HapticFeedbackHelper.VIBE_PATTERN_NO_REPEAT);
            disableAllBoxes();
            highlightWinningPattern(gameWinPattern);
            messageTurnIndicatorValue.setText(getString(R.string.game_message_wins, winningSymbol.getValue()));
        }
        else if(gameOverNoWinner){
            messageTurnIndicatorValue.setText(getString(R.string.game_message_draw));
        }
        else{
            //Next Turn
            messageTurnIndicatorValue.setText(toeGame.getTurn().getValue());
            startTurn(selectedXBoxIds, selectedOBoxIds);
        }
    }

    /**
     * UI Interactions
     */
    public void chooseBox(View view){
        String boxIdString = getResources().getResourceEntryName(view.getId());
        int boxId = Integer.parseInt(boxIdString.substring(boxIdString.length() - 1));

        ToeStrategy strategy = new ToeStrategyExplicit(boxId, toeGame.getTurn());
        toeGame.chooseBox(getContentResolver(), strategy);
    }
    public void newGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * LoaderManager Callbacks
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {GameDatabaseHelper.COLUMN_ID, GameDatabaseHelper.COLUMN_GAME_BOX_ID, GameDatabaseHelper.COLUMN_GAME_SYMBOL_ID};
        CursorLoader cursorLoader = new CursorLoader(this, GameContentProvider.CONTENT_URI, projection, null, null, GameDatabaseHelper.COLUMN_GAME_BOX_ID + SORT_DIRECTION);
        return cursorLoader;

    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int[] XIds = new int[9];
        int countX = 0;
        int[] OIds = new int[9];
        int countO = 0;

        data.moveToFirst();

        while (data.isAfterLast() == false) {

            int boxId = data.getInt(1);
            int gameSymbolId = data.getInt(2);

            if(GameSymbol.X.getId() == gameSymbolId){
                XIds[countX++] = boxId;
            }
            else{
                OIds[countO++] = boxId;
            }
            Button button = (Button) gridLayout.findViewById(GameBox.byLayoutId(boxId).layoutBoxId());
            button.setText(GameSymbol.byId(gameSymbolId).getValue());
            button.setEnabled(false);
            data.moveToNext();
        }
        if(toeGame.inProgress()){
            endTurn(XIds, OIds, data.getCount());
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
