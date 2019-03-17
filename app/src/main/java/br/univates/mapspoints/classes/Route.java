package br.univates.mapspoints.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.univates.mapspoints.database.DatabaseManager;

public class Route {
    private int routeId;
    private String nickname;
    private String city;

    private static final String TABLENAME = "route";
    private DatabaseManager databaseManager;

    public Route(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    private Route() {
    }

    public boolean createRoute(String city, String nickname) {

        boolean sucesso;
        ContentValues c = new ContentValues();
        c.put("city", city);
        c.put("nickname", nickname);

        try {
            databaseManager.open();
            this.routeId = (int) databaseManager.insert(TABLENAME, c);
            databaseManager.finish();
            sucesso =  true;
        } catch (Exception ex) {
            ex.printStackTrace();
            sucesso = false;
        }

        return sucesso;
    }

    public ArrayList<Route> getRotas() {

        ArrayList<Route> rotas = new ArrayList<>();

        String sql = "SELECT * FROM route";

        databaseManager.open();
        Cursor c = databaseManager.select(sql);
        databaseManager.finish();

        while (c.moveToNext()) {
            Route route = new Route();

            route.setRouteId(c.getInt(c.getColumnIndex("routeid")));
            route.setCity(c.getString(c.getColumnIndex("city")));
            route.setNickname(c.getString(c.getColumnIndex("nickname")));

            rotas.add(route);
        }

        databaseManager.close();
        c.close();

        return rotas;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
