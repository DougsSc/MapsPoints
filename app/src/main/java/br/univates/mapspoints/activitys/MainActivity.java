package br.univates.mapspoints.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.univates.mapspoints.R;
import br.univates.mapspoints.adapters.DividerItemDecoration;
import br.univates.mapspoints.adapters.RouteAdapter;
import br.univates.mapspoints.classes.Route;
import br.univates.mapspoints.database.DatabaseManager;
import br.univates.mapspoints.utils.Permissao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView list_rotas;
    private RouteAdapter routeAdapter;
    private DatabaseManager dbManager;

    private final int REQUEST_GPS_ACCESS = 1;
    private String[] permissoesNecessarias = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.iv_main_close).setOnClickListener(this);
        findViewById(R.id.bt_nova_rota).setOnClickListener(this);
        findViewById(R.id.bt_mostrar_rotas).setOnClickListener(this);

        list_rotas = findViewById(R.id.list_rotas);

        dbManager = new DatabaseManager(getApplicationContext());

        routeAdapter = new RouteAdapter();
        routeAdapter.setClickRun(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, MapsActivity.class)
                                .putExtra("routeid", routeAdapter.getItemClicked().getRouteId())
                                .putExtra("history", true));
                    }
                });
            }
        });
        list_rotas.setHasFixedSize(true);
        list_rotas.setLayoutManager(new LinearLayoutManager(this));
        list_rotas.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        list_rotas.setAdapter(routeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_main_close:
                finish();
                break;
            case R.id.bt_nova_rota:
                if (Permissao.validarPermissoes(this, permissoesNecessarias, REQUEST_GPS_ACCESS)) {
                    startActivity(new Intent(MainActivity.this, ConfigRouteActivity.class));
                }
                break;
            case R.id.bt_mostrar_rotas:

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_GPS_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    findViewById(R.id.bt_nova_rota).callOnClick();
                    return;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initListRoutes();
    }

    private void initListRoutes() {
        routeAdapter.routes.clear();
        routeAdapter.routes.addAll(new Route(this).getRotas());
        routeAdapter.notifyDataSetChanged();
    }
}
