package com.rickyhopper.trirhomjaegerparty.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rickyhopper.trirhomjaegerparty.model.Jaeger;

/**
 * Created by Ricky on 8/31/14.
 */
public class LocalJaegerHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "triRhomJaegerList.db";

    public static final String LIST_TABLE_NAME = "jaegers";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    public static final String LIST_TABLE_CREATE =
            "CREATE TABLE " + LIST_TABLE_NAME + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT );";

    public LocalJaegerHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL(LIST_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LocalJaegerHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE_NAME + ";"); //delete old table
        onCreate(db); //recreate table
    }

    //add item to database
    public void addListItem(Jaeger j) {
        SQLiteDatabase db = this.getWritableDatabase(); //open writable db instance

        //item doesn't exist already
        //create database info from data
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, j.getName());

        //insert the row into the database
        db.insert(LIST_TABLE_NAME, null, values);
        db.close(); //close database
    }

    //get all items from db, return cursor object
    public Cursor getAllItemsAsCursor() {
        //get db instance
        SQLiteDatabase db = this.getReadableDatabase();

        //create query to get all items contained in database
        Cursor cursor = db.query(LIST_TABLE_NAME, new String[] { KEY_ID, KEY_NAME },
                null, null, null, null, null);

        //move cursor to first instance
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();

        return cursor;
    }

    //delete a group from database
    public void deleteListItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(LIST_TABLE_NAME, KEY_NAME + " = ?", new String[] { name });

        db.close();
    }

    //clear db
    public void clearLocalDB() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(LIST_TABLE_NAME, "1 = 1", new String[0]);

        db.close();
    }
}
