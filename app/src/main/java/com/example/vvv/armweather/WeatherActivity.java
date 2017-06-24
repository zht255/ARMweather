package com.example.vvv.armweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;

/**
 * Created by vvv on 2017/6/24.
 */

public class WeatherActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String s = bundle.getString("Ble");
        AlertDialog.Builder ad = new AlertDialog.Builder(WeatherActivity.this);
        ad.setMessage(s);
        AlertDialog a = ad.create();
        a.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.weathermenu,menu);
        return true;

    }
}
