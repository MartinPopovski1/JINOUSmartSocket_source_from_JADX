package com.jinoux.pwersupplysocket.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTLock";
    private static final int DATABASE_VERSION = 1;

    public interface BLEDeviceSchema {
        public static final String Address = "address";
        public static final String CheckKey = "checkKey";
        public static final String ID = "id";
        public static final String LastState = "lastState";
        public static final String Name = "name";
        public static final String Password = "password";
        public static final String Power = "power";
        public static final String Record = "record";
        public static final String Reset = "reset";
        public static final String SerialNumber = "serialNumber";
        public static final String TABLE_NAME = "BLEDevice";
        public static final String UserKey = "userKey";
    }

    public DBConnection(Context ctt) {
        super(ctt, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BLEDevice (id INTEGER primary key autoincrement, serialNumber , name , password , address , userKey , checkKey , record , reset , power , lastState );");
    }

    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
    }
}
