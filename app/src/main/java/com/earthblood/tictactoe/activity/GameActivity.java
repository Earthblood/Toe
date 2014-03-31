package com.earthblood.tictactoe.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;

import roboguice.activity.RoboActivity;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

public class GameActivity extends RoboActivity implements LoaderManager.LoaderCallbacks<Cursor> {






    /**
     * LoaderManager Callbacks
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
