package com.earthblood.tictactoe.activity;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.strategy.ExplicitToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.util.GameBox;
import com.earthblood.tictactoe.util.GameSymbol;
import com.google.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */


public class GameActivity extends RoboFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SORT_DIRECTION = " ASC";

    @InjectView(R.id.game_grid_layout) GridLayout gridLayout;

    @Inject ToeGame toeGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        data.moveToFirst();
        while (data.isAfterLast() == false) {
            int boxId = data.getInt(1);
            Button button = (Button) gridLayout.findViewById(GameBox.byLayoutId(boxId).getLayoutBoxId());
            button.setText(GameSymbol.byId(data.getInt(2)).getValue());
            data.moveToNext();
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
