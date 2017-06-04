package com.example.x.lekcja11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "telefon.db";
    private static final String DB_TABLE = "telefony";
    private static final String DB_CREATE_TABLE = "CREATE TABLE "+DB_TABLE+"(id INTEGER PRIMARY KEY AUTOINCREMENT, nazwa TEXT NOT NULL, model TEXT NOT NULL, opis TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int Version) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }

    public boolean insertTelefon(Telefon telefon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nazwa", telefon.getNazwa());
        contentValues.put("model", telefon.getModel());
        contentValues.put("opis", telefon.getOpis());
        return db.insert(DB_TABLE, null, contentValues) > 0;
    }

    public SQLiteCursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return (SQLiteCursor) db.rawQuery("SELECT * FROM "+DB_TABLE,null);
    }

    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (db.delete(DB_TABLE,"id = ?", new String[]{ id }) >0 )
            return true;
        else
            return false;
    }
}
