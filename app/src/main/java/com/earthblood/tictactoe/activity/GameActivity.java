package com.earthblood.tictactoe.activity;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.util.GameBox;
import com.earthblood.tictactoe.util.GameSymbol;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */


public class GameActivity extends RoboFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SORT_DIRECTION = " ASC";

    @InjectView(R.id.game_grid_layout) GridLayout gridLayout;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportLoaderManager().initLoader(0, null, this);
        clearDatabase();
    }



    /**
     * UI Interactions
     */
    public void chooseBox(View view){
        loadTestData();
    }

    private void loadTestData() {
        clearDatabase();
        for (int i =1; i<10; i++) {
            ContentValues values = new ContentValues();
            values.put(GameDatabaseHelper.COLUMN_GAME_BOX_ID, i);
            values.put(GameDatabaseHelper.COLUMN_GAME_SYMBOL_ID, i%2==0 ? GameSymbol.X.getId() : GameSymbol.O.getId());
            getContentResolver().insert(GameContentProvider.CONTENT_URI, values);
        }
    }


    private int clearDatabase() {
        return getContentResolver().delete(GameContentProvider.CONTENT_URI, null, null);
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

        Log.d(Toe.TAG, "DATA size =  =" + data.getCount());

        data.moveToFirst();
        int count = 1;
        while (data.isAfterLast() == false) {
            Button button = (Button) gridLayout.findViewById(GameBox.byLayoutId(count).getLayoutBoxId());
            Log.d(Toe.TAG, "count =" + count);
            button.setText(GameSymbol.byId(data.getInt(2)).getValue());
            data.moveToNext();
            count++;
        }

    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
