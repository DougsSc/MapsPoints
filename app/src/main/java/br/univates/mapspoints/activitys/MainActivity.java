package br.univates.mapspoints.activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.univates.mapspoints.R;
import br.univates.mapspoints.adapters.DividerItemDecoration;
import br.univates.mapspoints.adapters.RouteAdapter;
import br.univates.mapspoints.classes.Route;
import br.univates.mapspoints.database.DatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView list_rotas;
    private RouteAdapter routeAdapter;
//    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_nova_rota).setOnClickListener(this);
        findViewById(R.id.bt_mostrar_rotas).setOnClickListener(this);

        list_rotas = findViewById(R.id.list_rotas);

        DatabaseManager dbManager = new DatabaseManager(getApplicationContext());
        ContentValues contentValues = new ContentValues();
//        contentValues.put("routeid", 1);
//        dbManager.insert("route", contentValues);
//        db = this.openOrCreateDatabase("Dados", Context.MODE_PRIVATE, null);

        routeAdapter = new RouteAdapter();
        routeAdapter.setClickRun(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
        list_rotas.setHasFixedSize(true);
        list_rotas.setLayoutManager(new LinearLayoutManager(this));
        list_rotas.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        list_rotas.setAdapter(routeAdapter);

        Route route1 = new Route();
        route1.setCity("Lajeado");
        route1.setNickname("Por aí");
        Route route2 = new Route();
        route2.setCity("Estrela");
        route2.setNickname("Lindo");
        Route route3 = new Route();
        route3.setCity("Venâncio");
        route3.setNickname("Interior");
        Route route4 = new Route();
        route4.setCity("Arroio do Meio");
        route4.setNickname("Ao ar livre");
        routeAdapter.routes.add(route1);
        routeAdapter.routes.add(route2);
        routeAdapter.routes.add(route3);
        routeAdapter.routes.add(route4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_nova_rota:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;
            case R.id.bt_mostrar_rotas:

                break;
        }
    }
}
