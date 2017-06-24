package com.example.vvv.armweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by vvv on 2017/6/24.
 */

public class WeatherActivity extends AppCompatActivity {
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.Back:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
