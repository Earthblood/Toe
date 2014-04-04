package com.earthblood.tictactoe.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class GameDatabaseHelper extends SQLiteOpenHelper{

    public static final String TABLE_GAME = "game";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GAME_BOX_ID = "game_box_id";
    public static final String COLUMN_GAME_SYMBOL_ID = "game_symbol_id";
    public static final String SORT_DIRECTION = " ASC";
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;



    private static final String DATABASE_CREATE = "create table "
            + TABLE_GAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_GAME_BOX_ID + " integer not null, " +
            COLUMN_GAME_SYMBOL_ID + " text not null);";

    public GameDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GameDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        onCreate(db);
    }
}
