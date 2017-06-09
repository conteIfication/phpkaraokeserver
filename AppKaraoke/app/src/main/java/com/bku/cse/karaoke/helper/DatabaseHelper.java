package com.bku.cse.karaoke.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bku.cse.karaoke.model.FavoriteSong;
import com.bku.cse.karaoke.model.RecordedSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thonghuynh on 5/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_karaoke";

    // Login table name
    private static final String TABLE_RECORDED_SONG = "recorded_song";
    private static final String TABLE_FAVORITE_KS = "favorite_ks";

    // RECORDED_SONG Table Columns names
    private static final String KEY_RS_ID = "rsid";
    private static final String KEY_RS_SCORE = "score";
    private static final String KEY_RS_PATH = "path";
    private static final String KEY_RS_RECORD_TYPE = "record_type";
    private static final String KEY_RS_IS_SHARED = "is_shared";
    private static final String KEY_RS_UP_TIME= "up_time";
    private static final String KEY_RS_KID = "kid";
    private static final String KEY_RS_SRID = "srid";

    // FAVORITE_KS Table Columns names
    private static final String KEY_FKS_ID = "fid";
    private static final String KEY_FKS_UP_TIME = "up_time";
    private static final String KEY_FKS_KID = "kid";

    private static final String CREATE_RECORDED_SONG_TABLE = "CREATE TABLE " + TABLE_RECORDED_SONG + "("
            + KEY_RS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_RS_SCORE + " INT,"
            + KEY_RS_PATH + " TEXT,"
            + KEY_RS_RECORD_TYPE + " TEXT,"
            + KEY_RS_IS_SHARED + " INT,"
            + KEY_RS_UP_TIME + " DATETIME,"
            + KEY_RS_KID + " INT,"
            + KEY_RS_SRID + " INT"
            + ")";
    private static final String CREATE_FAVORTIE_KS_TABLE = "CREATE TABLE " + TABLE_FAVORITE_KS + "("
            + KEY_FKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_FKS_UP_TIME + " DATETIME,"
            + KEY_FKS_KID + " INTEGER"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORDED_SONG_TABLE);
        db.execSQL(CREATE_FAVORTIE_KS_TABLE);
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDED_SONG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_KS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void add_RecordedSong(RecordedSong rs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_RS_ID, 1); // Name
        values.put(KEY_RS_SCORE, rs.getScore());
        values.put(KEY_RS_PATH, rs.getPath());
        values.put(KEY_RS_RECORD_TYPE, rs.getRecord_type());
        values.put(KEY_RS_IS_SHARED, rs.getIs_shared() ? "1" : "0");
        values.put(KEY_RS_UP_TIME, rs.getUp_time());
        values.put(KEY_RS_KID, rs.getKid());
        values.put(KEY_RS_SRID, rs.getSrid());

        // Inserting Row
        long id = db.insert(TABLE_RECORDED_SONG, null, values);

        Log.d(TAG, "New recorded song inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public List<RecordedSong> get_AllRecordedSong() {
        List<RecordedSong> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_RECORDED_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            RecordedSong newRS = new RecordedSong();

            newRS.setRsid( cursor.getInt(0) );
            newRS.setScore( cursor.getInt(1) );
            newRS.setPath( cursor.getString(2) );
            newRS.setRecord_type( cursor.getString(3) );
            newRS.setIs_shared( (cursor.getInt(4) == 1) ? true : false );
            newRS.setUp_time( cursor.getString(5) );
            newRS.setKid( cursor.getInt(6) );
            newRS.setSrid( cursor.getInt(7) );

            list.add(newRS);
        }

        cursor.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + list.size());

        return list;
    }

    public void update_RecordedSong_path(int rsid, String newPath) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RS_PATH, newPath);

        db.update(TABLE_RECORDED_SONG, contentValues, KEY_RS_ID + "=?", new String[]{ String.valueOf(rsid) });
        Log.d(TAG, "Updated rsid: " + rsid + " newPath: " + newPath);
    }
    public void update_RecordedSong_sharing_status(int rsid, boolean isShared, int srid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RS_IS_SHARED, isShared ? 1 : 0);
        contentValues.put(KEY_RS_SRID, isShared ? srid : 0 );

        db.update(TABLE_RECORDED_SONG, contentValues, KEY_RS_ID + "=?", new String[]{ String.valueOf(rsid) });
        Log.d(TAG, "Updated rsid: " + rsid + " isShared: " + isShared );
    }

    public void delete_RecordedSong(int rsid) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RECORDED_SONG, KEY_RS_ID + "=?", new String[]{ String.valueOf(rsid) });
        Log.d(TAG, "Deleted recorded song with id : " + rsid);
    }
    public void delete_allRecordedSong() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RECORDED_SONG, null, null);
        Log.d(TAG, "Deleted all recorded song in tabe " + TABLE_RECORDED_SONG);
    }

    // FAVORITE Karaoke Song table
    public void add_FavoriteKS(FavoriteSong fsong) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FKS_UP_TIME, fsong.getUp_time());
        contentValues.put(KEY_FKS_KID, fsong.getKid());

        long id = db.insert(TABLE_FAVORITE_KS, null, contentValues);

        Log.d(TAG, "New favorite song inserted into sqlite: " + id);
    }

    public void remove_FavoriteKS(int kid) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITE_KS, KEY_FKS_KID + "=?", new String[]{ String.valueOf(kid) });
        Log.d(TAG, "Deleted favorite song with kid : " + kid);
    }

    public FavoriteSong get_FavoriteKS(int kid) {
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE_KS + " WHERE kid=" + kid;
        FavoriteSong favoriteSong = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if ( cursor.moveToFirst() ) {
            favoriteSong = new FavoriteSong(
                    cursor.getInt( cursor.getColumnIndex("fid") ),
                    cursor.getString( cursor.getColumnIndex("up_time") ),
                    cursor.getInt( cursor.getColumnIndex("kid") )
            );
        }

        return favoriteSong;
    }
    public List<FavoriteSong> get_AllFavoriteSong() {
        List<FavoriteSong> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE_KS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            FavoriteSong newFS = new FavoriteSong();

            newFS.setFid( cursor.getInt( cursor.getColumnIndex( KEY_FKS_ID ) ) );
            newFS.setUp_time( cursor.getString( cursor.getColumnIndex( KEY_FKS_UP_TIME ) ) );
            newFS.setKid( cursor.getInt( cursor.getColumnIndex( KEY_FKS_KID ) ) );

            list.add(newFS);
        }

        cursor.close();
        // return user
        Log.d(TAG, "Fetching fs from Sqlite: " + list.size());

        return list;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
