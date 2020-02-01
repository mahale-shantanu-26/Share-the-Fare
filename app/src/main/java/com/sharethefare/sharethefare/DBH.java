package com.sharethefare.sharethefare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBH extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Userx.db";
    public static final String TABLE_NAME = "user_info";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Mobile";
    public static final String COL_4 = "Email";
    public static final String COL_5 = "Password";
    public static final String COL_6 = "Pickup";
    public static final String COL_7 = "Destination";
    public static final String COL_8 = "Date";
    public static final String COL_9 = "Time";
    public static final String COL_10 = "Confirm";

    ArrayList<String> arr = new ArrayList<String>();
    public DBH(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (" + COL_1 +" " +
                "INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 +" TEXT,"
                + COL_3 +" TEXT," + COL_4 +" TEXT," + COL_5 + " TEXT,"+ COL_6
                + " TEXT,"+ COL_7 + " TEXT,"+ COL_8 +" TEXT,"
        + COL_9 + " TEXT,"+ COL_10 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String mobile,String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,mobile);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,password);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean check(String email, String password){
        String str;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+ COL_4+" from "+TABLE_NAME+" where "
                +COL_4+" ="+"'"+email+"' and "+COL_5+" ="+"'"+password+"'",null);

        if(res.moveToFirst()){
            return true;
        }
        else
            return false;

    }

    public boolean insertData1(String pickup, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("update  "+TABLE_NAME+" set " +COL_6 +
                "="+"'"+pickup+"' where "+COL_4+" ="+"'"+email+"'",null);
        if(res.moveToFirst()){
            return true;
        }
        else
            return false;

    }

    public boolean insertDate(String date,String time, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("update  "+TABLE_NAME+" set " +COL_8 +
                "="+"'"+date+"',"+COL_9 +
                "="+"'"+time+"' where "+COL_4+" ="+"'"+email+"'",null);
        if(res.moveToFirst()){
            return true;
        }
        else
            return false;

    }

    public boolean insertData2(String desti, String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("update  "+TABLE_NAME+" set " +COL_7 +
                "="+"'"+desti+"' where "+COL_4+" ="+"'"+email+"'",null);
        if(res.moveToFirst()){
            return true;
        }
        else
            return false;

    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+ COL_2 + "," + COL_3 + "," + COL_6
                + "," + COL_7 + "," + COL_8 + "," + COL_9 + " from "+TABLE_NAME,null);
        return res;
    }
}
