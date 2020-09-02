package br.univates.mapspoints.activitys;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.univates.mapspoints.R;
import br.univates.mapspoints.classes.Route;

public class ConfigRouteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_config_nickname, et_config_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_route);

        findViewById(R.id.iv_config_back).setOnClickListener(this);
        findViewById(R.id.bt_config_start).setOnClickListener(this);

        et_config_nickname = findViewById(R.id.et_config_nickname);
        et_config_city = findViewById(R.id.et_config_city);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_config_back:
                onBackPressed();
                break;
            case R.id.bt_config_start:
                String city = et_config_city.getText().toString();
                String nickname = et_config_nickname.getText().toString();
                if (!city.isEmpty()) {
                    if (!nickname.isEmpty()) {
                        Route route = new Route(this);
                        route.createRoute(city, nickname);
                        startActivity(new Intent(ConfigRouteActivity.this, MapsActivity.class)
                                .putExtra("routeid", route.getRouteId()));
                    } else {
                        Toast.makeText(this, "Preencha o apelido!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Preencha a cidade!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
