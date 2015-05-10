package com.example.shivam.clothes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shivam on 09/05/15 at 11:32 PM.
 */
public class PantORM {

    private static final String TAG = "PantORM";
    public static final String TABLE_NAME = "pant";
    private static final String COLUMN_URI = "uri";
    private DatabaseWrapper dw;
    SQLiteDatabase myDataBase;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_URI + " TEXT)";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public int addImage(Context c,String uri) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        long pantID = 0;
        if (isDatabaseOpened()) {
            ContentValues values = new ContentValues();
            values.put(PantORM.COLUMN_URI, uri);
            pantID = myDataBase.insert(PantORM.TABLE_NAME, "null", values);
            Log.e(TAG, "Inserted new pant with ID: " + pantID);
            myDataBase.close();
        }
        return (int) pantID;
    }

    public ArrayList<String> getUriFromDB(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        ArrayList<String> uris = new ArrayList<String>();
        Cursor cur = myDataBase.rawQuery("SELECT * from pant",null);
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
        {
            uris.add(cur.getString(0));
        }
        System.out.println(uris);
        return uris;
    }

    public String getRandomUri(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        String randomUri = new String();
        Cursor cur = myDataBase.rawQuery("SELECT * from pant ORDER BY RANDOM() LIMIT 1",null);
        cur.moveToFirst();
        randomUri = cur.getString(0);
        return randomUri;
    }

    public boolean isEmpty(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        Cursor cur = myDataBase.rawQuery("SELECT COUNT(*) FROM pant",null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                return true;
            }
        }
        return false;

    }


    public boolean isDatabaseOpened() {
        if (myDataBase == null) {
            return false;
        } else {
            return myDataBase.isOpen();
        }
    }

}
