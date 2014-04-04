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
package com.earthblood.tictactoe.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.earthblood.tictactoe.helper.GameDatabaseHelper;

import java.util.Arrays;
import java.util.HashSet;

public class GameContentProvider extends ContentProvider {

    private GameDatabaseHelper gameDatabaseHelper;

    private static final int TOTAL_BOXES = 10;
    private static final int BOX_ID = 20;

    private static final String AUTHORITY = "com.earthblood.tictactoe";
    private static final String BASE_PATH = "game";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, TOTAL_BOXES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", BOX_ID);
    }

    @Override
    public boolean onCreate() {
        gameDatabaseHelper = new GameDatabaseHelper(getContext());
        return gameDatabaseHelper != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        verifyProjection(projection);
        queryBuilder.setTables(GameDatabaseHelper.TABLE_GAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TOTAL_BOXES:
                break;
            case BOX_ID:
                queryBuilder.appendWhere(GameDatabaseHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase database = gameDatabaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase database = gameDatabaseHelper.getWritableDatabase();
        long id = 0;

        switch (uriType) {
            case TOTAL_BOXES:
                id = database.insert(GameDatabaseHelper.TABLE_GAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase database = gameDatabaseHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case TOTAL_BOXES:
                rowsDeleted = database.delete(GameDatabaseHelper.TABLE_GAME, selection, selectionArgs);
                break;
            case BOX_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = database.delete(GameDatabaseHelper.TABLE_GAME, GameDatabaseHelper.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = database.delete(GameDatabaseHelper.TABLE_GAME, GameDatabaseHelper.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase database = gameDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case TOTAL_BOXES:
                rowsUpdated = database.update(GameDatabaseHelper.TABLE_GAME, values, selection, selectionArgs);
                break;
            case BOX_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = database.update(GameDatabaseHelper.TABLE_GAME, values, GameDatabaseHelper.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = database.update(GameDatabaseHelper.TABLE_GAME, values, GameDatabaseHelper.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void verifyProjection(String[] projection) {
        String[] available = { GameDatabaseHelper.COLUMN_ID, GameDatabaseHelper.COLUMN_GAME_BOX_ID, GameDatabaseHelper.COLUMN_GAME_SYMBOL_ID };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Projection contains unknown columns!");
            }
        }
    }
}
