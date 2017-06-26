package com.example.vvv.armweather;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by vvv on 2017/6/24.
 */

public class WeatherActivity extends AppCompatActivity {
    private BluetoothDevice device;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String s = bundle.getString("Ble");
        String[] ns = s.split("\n");
        final String address = ns[1];
        Button bset = (Button)findViewById(R.id.set);
        bset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this,ServerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Ble",address);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Button b = (Button)findViewById(R.id.connect);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    device = bluetoothAdapter.getRemoteDevice(address);
                    final BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    socket.connect();
                    String s = "info\n";
                    OutputStream os = socket.getOutputStream();
                    os.write(s.getBytes());
                    InputStream is = socket.getInputStream();
                    InputStream is1 = is;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is1));
                    StringBuilder sb = new StringBuilder();
                    String input = null;

                    while (!(input = reader.readLine()).equals("OK")) {
                        sb.append(input + "\n");
                    }
                    String string = sb.toString();
                    String[] info = string.split("\n");
                    TextView t1 = (TextView)findViewById(R.id.Ssid);
                    TextView t2 = (TextView)findViewById(R.id.Server);
                    TextView t3 = (TextView)findViewById(R.id.Temp);
                    TextView t4 = (TextView)findViewById(R.id.Hum);
                    TextView t5 = (TextView)findViewById(R.id.Press);
                    TextView t6 = (TextView)findViewById(R.id.Air);
                    t1.setText(info[0].split(",")[0]);
                    t2.setText(info[1].split(",")[0]);
                    t3.setText(info[2]);
                    t4.setText(info[3]);
                    t5.setText(info[4]);
                    t6.setText(info[5]);
                    /*AlertDialog.Builder ac = new AlertDialog.Builder(WeatherActivity.this);
                    ac.setMessage(info[5]);
                    AlertDialog ag = ac.create();
                    ag.show();*/
                    os.close();
                    is.close();
                    socket.close();
                   /* final Button b1 = (Button) findViewById(R.id.close);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/

                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

       /* AlertDialog.Builder ad = new AlertDialog.Builder(WeatherActivity.this);
        ad.setMessage(ns[1]);
        AlertDialog a = ad.create();
        a.show();*/
    }
    public String connect(String address)throws IOException{
        device = bluetoothAdapter.getRemoteDevice(address);
        BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        socket.connect();
        /*if (socket.isConnected()) {
            AlertDialog.Builder ad = new AlertDialog.Builder(WeatherActivity.this);
            ad.setMessage("Connected");

            ad.setPositiveButton("×", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog a = ad.create();
            a.show();
        }*/

       /* StringBuilder s = Socket(socket);
        TextView t = (TextView)findViewById(R.id.temp);
        t.setText(s);*/
        String str = Socket(socket);
        return str;
    }
    public String Socket(BluetoothSocket socket)throws IOException{
        String s = "info\n";
        OutputStream os = socket.getOutputStream();
        os.write(s.getBytes());
        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String input = null;

        while ((input = reader.readLine()) != null) {
            sb.append(input);
        }
        is.close();
        return sb.toString();

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
