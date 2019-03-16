package br.univates.mapspoints.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DBNAME = "SQLite.db";
    private static final int DBVERSION = 1;
    private Context context;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
//        db = SQLiteDatabase.openOrCreateDatabase(DBNAME,null);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String createSQL = "CREATE TABLE \"route\"(\n" +
                "\t\"routeid\" Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"apelido\" Varchar,\n" +
                "\t\"cidade\" Varchar);";
        db.execSQL(createSQL);

//        db.execSQL("CREATE TABLE route (" +
//                "routeid Integer PRIMARY KEY AUTOINCREMENT," +
//                "apelido varchar," +
//                "cidade varchar)" +
//                "");
        db.execSQL("CREATE TABLE location (" +
                "locationid int," +
                "ref_routeid int," +
                "latitude double," +
                "longitude double);" +
                "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE route;");
        db.execSQL("DROP TABLE location;");
        onCreate(db);
    }

    public void insert(String table, ContentValues content) {
        ContentValues c = new ContentValues();
        c.put("routeid", 1);
        c.put("apelido", "Teste");
        c.put("cidade", "Lajeado");
        db.insertOrThrow("route",null, c);
//        db.insertOrThrow(table, null, content);
        Cursor cursor = db.rawQuery("SELECT * FROM route", null);

        if (cursor.moveToNext()) {
            System.out.println(cursor.getString(cursor.getColumnIndex("apelido")));
        }
    }
}
