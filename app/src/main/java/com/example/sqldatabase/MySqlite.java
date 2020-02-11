package com.example.sqldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlite extends SQLiteOpenHelper {

    Context context;

    String TAG = "MySqlite";

    private final static String DBNAME = "mydb";
    private final static int DBVERSION = 1;

    private final static String MYTABLE = "mytable";

    public final static String COL_ID = "SNO";
    public final static String COL_NAME = "Name";
    public final static String COL_EMAIL = "Email";
    public final static String COL_MOBILE = "Mobile";



    public MySqlite(Context context) {
        super(context, DBNAME, null, DBVERSION);

        Log.d(TAG,"MySqlite constructor called");
        getWritableDatabase();
    }

///DATABASE  Table create
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(TAG,"onCreate called");

        String query = "CREATE TABLE mytable (sno integer PRIMARY KEY AUTOINCREMENT ,name TEXT , email TEXT, mobile INTEGER)";
        sqLiteDatabase.execSQL(query);
/// application install 1 time only
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        Log.d(TAG,"onUpgrade called");
        String query = "DROP TABLE IF EXISTS mytable";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
/// when version change to upgrade
    }

    public void insertData(String name, String email, String mobile) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME , name);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_MOBILE,mobile);

       long response = sqLiteDatabase.insert("mytable",null,contentValues);

       if (response == -1){
           Log.d(TAG,"insertData Failuer");
       }else {
           Log.d(TAG,"insertData Success!");

       }
    }

    public String readData(){

        String query = "SELECT * FROM mytable";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if (cursor != null){

            cursor.moveToFirst();
//            cursor.moveToNext();
//            cursor.moveToLast();

            while (cursor.moveToNext()){

                String sno,name,email,mobile;

                sno = cursor.getString(cursor.getColumnIndex("sno"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                email = cursor.getString(cursor.getColumnIndex("email"));
                mobile = cursor.getString(cursor.getColumnIndex("mobile"));


                Log.d(TAG,"----------------------------------");
                Log.d(TAG,"sno :"+sno);
                Log.d(TAG,"name :"+name);
                Log.d(TAG,"email :"+ email);
                Log.d(TAG, "mobile :"+mobile);
                Log.d(TAG,"-----------------------------------");
            }
        }
        return query;
    }


    public boolean updateData(String name, String email, String mobile,String sno) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_MOBILE,mobile);
        contentValues.put(COL_ID,sno);
        sqLiteDatabase.update(MYTABLE,contentValues,"SNO=?",new String[]{sno});

        Log.d(TAG,"name :"+name);
        Log.d(TAG,"email :"+email);
        Log.d(TAG,"mobile :"+mobile);

        return true;
    }

    public Cursor getAllData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT*FROM "+MYTABLE,null);
        return cursor;

    }
}
