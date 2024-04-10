package com.example.pokedexmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class FavoritePokemonDB extends SQLiteOpenHelper implements Serializable {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PokemonDB";
    public static final String TABLE_FAVORITES = "favorites";
    public static final String KEY_ID = "id";

    public FavoritePokemonDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFavoritesTable = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY"+")";
        db.execSQL(createFavoritesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public void addFavoritePokemon(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public void removeFavoritePokemon(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
