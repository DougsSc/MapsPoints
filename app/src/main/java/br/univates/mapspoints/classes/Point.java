package br.univates.mapspoints.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.univates.mapspoints.database.DatabaseManager;

public class Point {
    private int pointid;
    private int refRouteid;
    private double latitude;
    private double longitude;

    private static final String TABLENAME = "point";
    private DatabaseManager databaseManager;

    public Point(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    private Point() {
    }

    public boolean createPoint() {
        boolean sucesso;
        ContentValues c = new ContentValues();
        c.put("ref_routeid", this.refRouteid);
        c.put("latitude", this.latitude);
        c.put("longitude", this.longitude);

        try {
            databaseManager.open();
            this.pointid = (int) databaseManager.insert(TABLENAME, c);
            databaseManager.finish();
            sucesso =  true;
        } catch (Exception ex) {
            ex.printStackTrace();
            sucesso = false;
        }

        return sucesso;
    }

    public ArrayList<Point> getPointsByRefRouteid(int refRouteid) {
        ArrayList<Point> points = new ArrayList<>();

        String sql = "SELECT * FROM point WHERE ref_routeid = " + refRouteid;

        databaseManager.open();
        Cursor c = databaseManager.select(sql);
        databaseManager.finish();

        while (c.moveToNext()) {
            Point point = new Point();

            point.setPointid(c.getInt(c.getColumnIndex("pointid")));
            point.setRefRouteid(c.getInt(c.getColumnIndex("ref_routeid")));
            point.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
            point.setLongitude(c.getDouble(c.getColumnIndex("longitude")));

            points.add(point);
        }

        databaseManager.close();
        c.close();

        return points;
    }

    public void closeConnection() {
        databaseManager.close();
    }

    public int getPointid() {
        return pointid;
    }

    public void setPointid(int pointid) {
        this.pointid = pointid;
    }

    public int getRefRouteid() {
        return refRouteid;
    }

    public void setRefRouteid(int refRouteid) {
        this.refRouteid = refRouteid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
