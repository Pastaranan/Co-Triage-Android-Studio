package com.example.cotriage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Register.db";
    private static final String TABLE_NAME = "USERS";
    private static final String COL_1 = "Phone";
    private static final String COL_2 = "Password";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Phone TEXT ,Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(MyDB);

    }

    public boolean registerUser(String phone,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, phone);
        contentValues.put(COL_2, password);

        long result = MyDB.insert(TABLE_NAME,null,contentValues);
        if(result == -1) return false;
        else return true;
    }

    public boolean checkusername(String phone){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from phone where phone = ?",new String[] {phone});
        if (cursor.getCount() > 0) {return true;}
        else {return  false;}
    }

    public boolean checkusernamepassword(String phone,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from phone where phone = ? and password = ?",new String[] {phone,password});
        if (cursor.getCount() > 0 ) {return true;}
        else {return  false;}
    }

    public Cursor getAllDate (String phone,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor res = MyDB.rawQuery("Select * from phone where phone = ? and password",new String[] {phone,password});
        return res;
    }


}

