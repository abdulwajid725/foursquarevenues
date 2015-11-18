package com.example.abdulwajid.bluegapefoods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by abdul wajid on 11/9/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    MainActivity mainActivity;
    ArrayList<VenuePrototype> arrayList;
    private static final String DB_NAME = "BlueGape_DataBase";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "venues";
    public static final String TABLE_NAME1 = "venuesupdated";
    public static final String COL_NAME = "Name";
    public static final String COL_Distance = "Distance";
    private static final String LAT="Lat";
    private static final String LNG="Lng";
    private static final String ADDRESS="Address";
    String DATA_ID="dataid";
    String id;
    String name;
    String distance;
    String address;
    String lat;
    String lng;
    String STRING_CREATE = "CREATE TABLE "+TABLE_NAME1+" ("+DATA_ID+" TEXT PRIMARY KEY , " +COL_NAME+
        " TEXT, "+COL_Distance+" TEXT, " +ADDRESS+" TEXT, " +LAT+" TEXT, " +LNG+" TEXT);";

    String DELETE="DELETE FROM "+TABLE_NAME+";";
    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(STRING_CREATE);

    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again/db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");
       // onCreate(db);
      /*  if(newVersion>oldVersion)
        {
            db.execSQL("ALTER TABLE "+TABLE_NAME1+" ADD COLUMN ");
        }*/
    }

    public void delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE);
    }
    public void insert(String id,String name,String distance,String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATA_ID,id);
        values.put(COL_NAME,name);
        values.put(COL_Distance,distance);
        values.put(ADDRESS, address);
        //values.put(LAT,lat);
        //values.put(LNG,lng);
        db.insert(TABLE_NAME, null, values);

    }

    public ArrayList<FetchFromDATABASEList> getAllVenues()
    {
        ArrayList<FetchFromDATABASEList> list=new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                FetchFromDATABASEList fetchFromDATABASEList=new FetchFromDATABASEList();
                fetchFromDATABASEList.setId(cursor.getString(0));
                fetchFromDATABASEList.setName(cursor.getString(1));
                fetchFromDATABASEList.setDistance(cursor.getString(2));
                fetchFromDATABASEList.setAddress(cursor.getString(3));
                //fetchFromDATABASEList.setLat(cursor.getString(4));
                //fetchFromDATABASEList.setLng(cursor.getString(5));
                // Adding contact to list
                if (fetchFromDATABASEList!= null)
                    list.add(fetchFromDATABASEList);


            }
            while (cursor.moveToNext());
        }

        return list;
    }


}
