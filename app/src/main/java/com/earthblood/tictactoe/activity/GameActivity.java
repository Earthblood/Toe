package com.earthblood.tictactoe.activity;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.strategy.ExplicitToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.util.GameBox;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.GameWinPattern;
import com.google.inject.Inject;

import java.util.Arrays;

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

    @InjectView(R.id.game_grid_layout)             GridLayout gridLayout;
    @InjectView(R.id.message_turn_indicator_value) TextView messageTurnIndicatorValue;

    @Inject ToeGame toeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void refreshUI(boolean allBoxesFilled, GameWinPattern gameWinPattern, GameSymbol winningSymbol) {

        if(gameWinPattern != null){
            //We Have a Winner
            //TODO: DISABLE ALL UNSELECTED BOXES
            messageTurnIndicatorValue.setText(winningSymbol.getValue() + " WINS!");
        }
        else if(allBoxesFilled){
            //Game Over: No winner
            messageTurnIndicatorValue.setText("DRAW!");
        }
        else{
            //Next Turn
            messageTurnIndicatorValue.setText(toeGame.getTurn().getValue());
        }
    }

    /**
     * UI Interactions
     */
    public void chooseBox(View view){
        String boxIdString = getResources().getResourceEntryName(view.getId());
        int boxId = Integer.parseInt(boxIdString.substring(boxIdString.length() - 1));

        ToeStrategy strategy = new ExplicitToeStrategy(boxId, toeGame.getTurn());
        toeGame.chooseBox(getContentResolver(), strategy);
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
            Button button = (Button) gridLayout.findViewById(GameBox.byLayoutId(boxId).getLayoutBoxId());
            button.setText(GameSymbol.byId(gameSymbolId).getValue());
            button.setEnabled(false);
            data.moveToNext();
        }

        Log.d(Toe.TAG, "XIDS = " + Arrays.toString(XIds));
        Log.d(Toe.TAG, "OIDS = " + Arrays.toString(OIds));

        GameSymbol gameSymbol = GameSymbol.X;
        boolean allBoxesFilled = data.getCount() == 9;
        GameWinPattern gameWinPattern = GameWinPattern.checkForWin(XIds);
        if(gameWinPattern == null){
            gameWinPattern = GameWinPattern.checkForWin(OIds);
            gameSymbol = GameSymbol.O;
        }
        refreshUI(allBoxesFilled, gameWinPattern, gameSymbol);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
