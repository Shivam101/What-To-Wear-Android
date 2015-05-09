package com.example.shivam.clothes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shivam on 10/05/15 at 2:54 AM.
 */
public class FavoriteORM {

    private static final String TAG = "FavoriteORM";
    public static final String TABLE_NAME = "favorite";
    private static final String COLUMN_URI = "uriShirt";
    private static final String COLUMN_URI_2 = "uriPant";
    private DatabaseWrapper dw;
    SQLiteDatabase myDataBase;

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_URI + " TEXT, "
                    + COLUMN_URI_2 + " TEXT)";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public int addImage(Context c,String uriShirt,String uriPant) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        long favoriteID = 0;
        if (isDatabaseOpened()) {
            ContentValues values = new ContentValues();
            values.put(FavoriteORM.COLUMN_URI, uriShirt);
            values.put(FavoriteORM.COLUMN_URI_2,uriPant);
            favoriteID = myDataBase.insert(FavoriteORM.TABLE_NAME, "null", values);
            Log.e(TAG, "Inserted new Favorite with ID: " + favoriteID);
            myDataBase.close();
        }
        return (int) favoriteID;
    }

    public ArrayList<String> getShirtUriFromDB(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        ArrayList<String> uris = new ArrayList<String>();
        Cursor cur = myDataBase.rawQuery("SELECT * from favorite",null);
        Log.e("COUNT",String.valueOf(cur.getCount()));
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
        {
            uris.add(cur.getString(0));
        }
        System.out.println(uris);
        return uris;
    }

    public ArrayList<String> getPantUriFromDB(Context c)
    {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(c);
        myDataBase = databaseWrapper.getWritableDatabase();
        ArrayList<String> uris = new ArrayList<String>();
        Cursor cur = myDataBase.rawQuery("SELECT * from favorite",null);
        Log.e("COUNT",String.valueOf(cur.getCount()));
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
        {
            uris.add(cur.getString(1));
        }
        System.out.println(uris);
        return uris;
    }


    public boolean isDatabaseOpened() {
        if (myDataBase == null) {
            return false;
        } else {
            return myDataBase.isOpen();
        }
    }



}
