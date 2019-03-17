package br.univates.mapspoints.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DBNAME = "MapsPoints.db";
    private static final int DBVERSION = 1;
    private Context context;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
//        db = SQLiteDatabase.openOrCreateDatabase(DBNAME,null);
        if (db == null) {
            try {
                db = this.getWritableDatabase();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS route(" +
                "routeid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nickname VARCHAR(30)," +
                "city VARCHAR(30));");

        db.execSQL("CREATE TABLE IF NOT EXISTS point (" +
                "pointid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ref_routeid INTEGER," +
                "latitude DOUBLE," +
                "longitude DOUBLE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE route;");
        db.execSQL("DROP TABLE point;");
        onCreate(db);
    }

    // TRANSACTIONS
    public void open() {
        db.beginTransaction();
    }

    public long insert(String tabela, ContentValues obj) {
        return db.insertOrThrow(tabela, null, obj);
    }

    public int update(String tabela, ContentValues values, String where){
        return db.update(tabela, values, where, null);
    }

    public Cursor select(String sql) {
        return db.rawQuery(sql, null);
    }

    public int delete(String tabela, String where) {
        return db.delete(tabela, where,null);
    }

    public void finish() {
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    // TRANSACTIONS

    public void close() {
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.close();
    }
}
