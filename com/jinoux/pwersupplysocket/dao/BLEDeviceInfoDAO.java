package com.jinoux.pwersupplysocket.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;
import com.jinoux.pwersupplysocket.util.AES;
import com.jinoux.pwersupplysocket.util.DBConnection;
import com.jinoux.pwersupplysocket.util.DBConnection.BLEDeviceSchema;

public class BLEDeviceInfoDAO {
    private DBConnection helper;

    public BLEDeviceInfoDAO(Context act) {
        this.helper = new DBConnection(act);
    }

    public void updateByAddress(BLEDeviceInfo bdi) {
        SQLiteDatabase db = null;
        try {
            ContentValues values = new ContentValues();
            String SNResultStr = AES.encrypt(bdi.getSerialNumber().getBytes(), AES.AES_KEY);
            String NResultStr = AES.encrypt(bdi.getName().getBytes(), AES.AES_KEY);
            String PWResultStr = AES.encrypt(bdi.getPassword().getBytes(), AES.AES_KEY);
            String AResultStr = AES.encrypt(bdi.getAddress().getBytes(), AES.AES_KEY);
            String UKResultStr = AES.encrypt(bdi.getUserKey(), AES.AES_KEY);
            String CKResultStr = AES.encrypt(bdi.getCheckKey(), AES.AES_KEY);
            String RResultStr = AES.encrypt(bdi.getRecord().getBytes(), AES.AES_KEY);
            String ResetResultStr = AES.encrypt(bdi.getReset().getBytes(), AES.AES_KEY);
            String PowerResultStr = AES.encrypt(bdi.getPower().getBytes(), AES.AES_KEY);
            String LSResultStr = AES.encrypt(bdi.getLastState().getBytes(), AES.AES_KEY);
            values.put(BLEDeviceSchema.SerialNumber, SNResultStr);
            values.put(BLEDeviceSchema.Name, NResultStr);
            values.put(BLEDeviceSchema.Password, PWResultStr);
            values.put(BLEDeviceSchema.Address, AResultStr);
            values.put(BLEDeviceSchema.UserKey, UKResultStr);
            values.put(BLEDeviceSchema.CheckKey, CKResultStr);
            values.put(BLEDeviceSchema.Record, RResultStr);
            values.put(BLEDeviceSchema.Reset, ResetResultStr);
            values.put(BLEDeviceSchema.Power, PowerResultStr);
            values.put(BLEDeviceSchema.LastState, LSResultStr);
            String where = "address = \"" + AResultStr + "\"";
            db = this.helper.getWritableDatabase();
            db.update(BLEDeviceSchema.TABLE_NAME, values, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSQLiteDatabase(db);
        }
    }

    public void insert(BLEDeviceInfo bdi) {
        SQLiteDatabase db = null;
        try {
            ContentValues values = new ContentValues();
            String SNResultStr = AES.encrypt(bdi.getSerialNumber().getBytes(), AES.AES_KEY);
            String NResultStr = AES.encrypt(bdi.getName().getBytes(), AES.AES_KEY);
            String PWResultStr = AES.encrypt(bdi.getPassword().getBytes(), AES.AES_KEY);
            String AResultStr = AES.encrypt(bdi.getAddress().getBytes(), AES.AES_KEY);
            String UKResultStr = AES.encrypt(bdi.getUserKey(), AES.AES_KEY);
            String CKResultStr = AES.encrypt(bdi.getCheckKey(), AES.AES_KEY);
            String RResultStr = AES.encrypt(bdi.getRecord().getBytes(), AES.AES_KEY);
            String ResetResultStr = AES.encrypt(bdi.getReset().getBytes(), AES.AES_KEY);
            String PowerResultStr = AES.encrypt(bdi.getPower().getBytes(), AES.AES_KEY);
            String LSResultStr = AES.encrypt(bdi.getLastState().getBytes(), AES.AES_KEY);
            values.put(BLEDeviceSchema.SerialNumber, SNResultStr);
            values.put(BLEDeviceSchema.Name, NResultStr);
            values.put(BLEDeviceSchema.Password, PWResultStr);
            values.put(BLEDeviceSchema.Address, AResultStr);
            values.put(BLEDeviceSchema.UserKey, UKResultStr);
            values.put(BLEDeviceSchema.CheckKey, CKResultStr);
            values.put(BLEDeviceSchema.Record, RResultStr);
            values.put(BLEDeviceSchema.Reset, ResetResultStr);
            values.put(BLEDeviceSchema.Power, PowerResultStr);
            values.put(BLEDeviceSchema.LastState, LSResultStr);
            db = this.helper.getWritableDatabase();
            db.insert(BLEDeviceSchema.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSQLiteDatabase(db);
        }
    }

    public void deleteByAddress(String address) {
        SQLiteDatabase db = null;
        try {
            String where = "address=\"" + AES.encrypt(address.getBytes(), AES.AES_KEY) + "\"";
            db = this.helper.getWritableDatabase();
            db.delete(BLEDeviceSchema.TABLE_NAME, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSQLiteDatabase(db);
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = null;
        try {
            db = this.helper.getWritableDatabase();
            db.delete(BLEDeviceSchema.TABLE_NAME, null, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSQLiteDatabase(db);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.jinoux.pwersupplysocket.modle.BLEDeviceInfo> queryAll() {
        /*
        r31 = this;
        r2 = 0;
        r27 = 0;
        r30 = new java.util.ArrayList;
        r30.<init>();
        r0 = r31;
        r3 = r0.helper;	 Catch:{ Exception -> 0x0154 }
        r2 = r3.getWritableDatabase();	 Catch:{ Exception -> 0x0154 }
        r3 = 11;
        r4 = new java.lang.String[r3];	 Catch:{ Exception -> 0x0154 }
        r3 = 0;
        r5 = "id";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 1;
        r5 = "serialNumber";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 2;
        r5 = "name";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 3;
        r5 = "password";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 4;
        r5 = "address";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 5;
        r5 = "userKey";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 6;
        r5 = "checkKey";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 7;
        r5 = "record";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 8;
        r5 = "reset";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 9;
        r5 = "power";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = 10;
        r5 = "lastState";
        r4[r3] = r5;	 Catch:{ Exception -> 0x0154 }
        r3 = "BLEDevice";
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r27 = r2.query(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ Exception -> 0x014e }
        r27.moveToFirst();	 Catch:{ Exception -> 0x014e }
        r29 = 0;
    L_0x005e:
        r3 = r27.getCount();	 Catch:{ Exception -> 0x014e }
        r0 = r29;
        if (r0 < r3) goto L_0x0073;
    L_0x0066:
        r0 = r31;
        r1 = r27;
        r0.closeCursor(r1);
        r0 = r31;
        r0.closeSQLiteDatabase(r2);
    L_0x0072:
        return r30;
    L_0x0073:
        r3 = 1;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r25 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 2;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r20 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 3;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r21 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 4;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r17 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 5;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r26 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 6;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r18 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 7;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r23 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 8;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r24 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 9;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r22 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 10;
        r0 = r27;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x014e }
        r5 = "letsgetstart";
        r19 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r5);	 Catch:{ Exception -> 0x014e }
        r3 = 0;
        r0 = r27;
        r6 = r0.getInt(r3);	 Catch:{ Exception -> 0x014e }
        r7 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r25;
        r7.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r8 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r20;
        r8.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r9 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r21;
        r9.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r10 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r17;
        r10.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r11 = r26;
        r12 = r18;
        r13 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r23;
        r13.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r14 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r24;
        r14.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r15 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r22;
        r15.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r16 = new java.lang.String;	 Catch:{ Exception -> 0x014e }
        r0 = r16;
        r1 = r19;
        r0.<init>(r1);	 Catch:{ Exception -> 0x014e }
        r5 = new com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;	 Catch:{ Exception -> 0x014e }
        r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16);	 Catch:{ Exception -> 0x014e }
        r0 = r30;
        r0.add(r5);	 Catch:{ Exception -> 0x014e }
        r27.moveToNext();	 Catch:{ Exception -> 0x014e }
        r29 = r29 + 1;
        goto L_0x005e;
    L_0x014e:
        r28 = move-exception;
        r28.printStackTrace();	 Catch:{ Exception -> 0x0154 }
        goto L_0x0066;
    L_0x0154:
        r28 = move-exception;
        r28.printStackTrace();	 Catch:{ all -> 0x0166 }
        r0 = r31;
        r1 = r27;
        r0.closeCursor(r1);
        r0 = r31;
        r0.closeSQLiteDatabase(r2);
        goto L_0x0072;
    L_0x0166:
        r3 = move-exception;
        r0 = r31;
        r1 = r27;
        r0.closeCursor(r1);
        r0 = r31;
        r0.closeSQLiteDatabase(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO.queryAll():java.util.List<com.jinoux.pwersupplysocket.modle.BLEDeviceInfo>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.jinoux.pwersupplysocket.modle.BLEDeviceInfo> queryByAddress(java.lang.String r34) {
        /*
        r33 = this;
        r2 = 0;
        r29 = 0;
        r32 = new java.util.ArrayList;
        r32.<init>();
        r3 = r34.getBytes();	 Catch:{ Exception -> 0x0176 }
        r6 = "letsgetstart";
        r19 = com.jinoux.pwersupplysocket.util.AES.encrypt(r3, r6);	 Catch:{ Exception -> 0x0176 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0176 }
        r6 = "address=\"";
        r3.<init>(r6);	 Catch:{ Exception -> 0x0176 }
        r0 = r19;
        r3 = r3.append(r0);	 Catch:{ Exception -> 0x0176 }
        r6 = "\"";
        r3 = r3.append(r6);	 Catch:{ Exception -> 0x0176 }
        r5 = r3.toString();	 Catch:{ Exception -> 0x0176 }
        r0 = r33;
        r3 = r0.helper;	 Catch:{ Exception -> 0x0176 }
        r2 = r3.getWritableDatabase();	 Catch:{ Exception -> 0x0176 }
        r3 = 11;
        r4 = new java.lang.String[r3];	 Catch:{ Exception -> 0x0176 }
        r3 = 0;
        r6 = "id";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 1;
        r6 = "serialNumber";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 2;
        r6 = "name";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 3;
        r6 = "password";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 4;
        r6 = "address";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 5;
        r6 = "userKey";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 6;
        r6 = "checkKey";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 7;
        r6 = "record";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 8;
        r6 = "reset";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 9;
        r6 = "power";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = 10;
        r6 = "lastState";
        r4[r3] = r6;	 Catch:{ Exception -> 0x0176 }
        r3 = "BLEDevice";
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r29 = r2.query(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ Exception -> 0x0170 }
        r29.moveToFirst();	 Catch:{ Exception -> 0x0170 }
        r31 = 0;
    L_0x007e:
        r3 = r29.getCount();	 Catch:{ Exception -> 0x0170 }
        r0 = r31;
        if (r0 < r3) goto L_0x0093;
    L_0x0086:
        r0 = r33;
        r1 = r29;
        r0.closeCursor(r1);
        r0 = r33;
        r0.closeSQLiteDatabase(r2);
    L_0x0092:
        return r32;
    L_0x0093:
        r3 = 1;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r27 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 2;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r22 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 3;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r23 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 4;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r18 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 5;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r28 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 6;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r20 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 7;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r25 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 8;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r26 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 9;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r24 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 10;
        r0 = r29;
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x0170 }
        r6 = "letsgetstart";
        r21 = com.jinoux.pwersupplysocket.util.AES.decrypt(r3, r6);	 Catch:{ Exception -> 0x0170 }
        r3 = 0;
        r0 = r29;
        r7 = r0.getInt(r3);	 Catch:{ Exception -> 0x0170 }
        r8 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r27;
        r8.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r9 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r22;
        r9.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r10 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r23;
        r10.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r11 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r18;
        r11.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r12 = r28;
        r13 = r20;
        r14 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r25;
        r14.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r15 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r26;
        r15.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r16 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r16;
        r1 = r24;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0170 }
        r17 = new java.lang.String;	 Catch:{ Exception -> 0x0170 }
        r0 = r17;
        r1 = r21;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0170 }
        r6 = new com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;	 Catch:{ Exception -> 0x0170 }
        r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17);	 Catch:{ Exception -> 0x0170 }
        r0 = r32;
        r0.add(r6);	 Catch:{ Exception -> 0x0170 }
        r29.moveToNext();	 Catch:{ Exception -> 0x0170 }
        r31 = r31 + 1;
        goto L_0x007e;
    L_0x0170:
        r30 = move-exception;
        r30.printStackTrace();	 Catch:{ Exception -> 0x0176 }
        goto L_0x0086;
    L_0x0176:
        r30 = move-exception;
        r30.printStackTrace();	 Catch:{ all -> 0x0188 }
        r0 = r33;
        r1 = r29;
        r0.closeCursor(r1);
        r0 = r33;
        r0.closeSQLiteDatabase(r2);
        goto L_0x0092;
    L_0x0188:
        r3 = move-exception;
        r0 = r33;
        r1 = r29;
        r0.closeCursor(r1);
        r0 = r33;
        r0.closeSQLiteDatabase(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO.queryByAddress(java.lang.String):java.util.List<com.jinoux.pwersupplysocket.modle.BLEDeviceInfo>");
    }

    private void closeCursor(Cursor c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void closeSQLiteDatabase(SQLiteDatabase db) {
        if (db != null) {
            try {
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
