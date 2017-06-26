package com.example.vvv.armweather;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by vvv on 2017/6/26.
 */

public class ServerActivity extends AppCompatActivity {
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverset);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String address = bundle.getString("Ble");
       /* AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
        ad.setMessage(address);
        AlertDialog a = ad.create();
        a.show();*/
       Button b1 = (Button)findViewById(R.id.setIP);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.setview,(ViewGroup)findViewById(R.id.IPdialog));
                AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
                ad.setView(layout);
                Button b3 = (Button)layout.findViewById(R.id.useIPset);
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et1 = (EditText)layout.findViewById(R.id.newIP);
                        EditText et2 = (EditText)layout.findViewById(R.id.newPort);
                        String newip = et1.getText().toString();
                        String newport = et2.getText().toString();
                        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                        try{
                            final BluetoothSocket socket;
                            socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            socket.connect();
                            String ip = "server: " + newip + "\n";
                            String port = "port: " + newport + "\n";
                            OutputStream os = socket.getOutputStream();
                            os.write(ip.getBytes());
                            InputStream is = socket.getInputStream();
                            BufferedReader readerip = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb = new StringBuilder();
                            String input = null;
                            while (!(input = readerip.readLine()).equals("OK")) {
                                sb.append(input);
                            }
                            String statusip = sb.toString();
                            os.write(port.getBytes());
                            is = socket.getInputStream();
                            BufferedReader readerport = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb1 = new StringBuilder();
                            String input1 = null;
                            while (!(input1 = readerport.readLine()).equals("OK")) {
                                sb1.append(input1);
                            }
                            String statusport = sb1.toString();
                            os.write("conn\n".getBytes());
                            is = socket.getInputStream();
                            BufferedReader readerconn = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb2 = new StringBuilder();
                            String input2 = null;
                            while (!(input2 = readerconn.readLine()).equals("OK")) {
                                sb2.append(input2);
                            }
                            String conn = sb2.toString();
                            AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
                            ad.setMessage(statusip + statusport + conn);
                            AlertDialog a = ad.create();
                            a.show();
                            os.close();
                            is.close();
                            socket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog a = ad.create();
                a.show();
            }
        });
        Button b2 = (Button)findViewById(R.id.setWifi);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.wifiset,(ViewGroup)findViewById(R.id.Wifidialog));
                AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
                ad.setView(layout);
                Button b5 = (Button)layout.findViewById(R.id.reset);
                b5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                        try {
                            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            socket.connect();
                            OutputStream os = socket.getOutputStream();
                            os.write("reset\n".getBytes());
                            InputStream is = socket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb = new StringBuilder();
                            String input = null;
                            while (!(input = reader.readLine()).equals("OK")) {
                                sb.append(input);
                            }
                            AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
                            ad.setMessage(sb.toString());
                            AlertDialog a = ad.create();
                            a.show();
                            os.close();
                            is.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button b4 = (Button)layout.findViewById(R.id.useWifiset);
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et3 = (EditText)layout.findViewById(R.id.newSsid);
                        EditText et4 = (EditText)layout.findViewById(R.id.newPassword);
                        String newssid = et3.getText().toString();
                        String newpassword = et4.getText().toString();
                        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                        final BluetoothSocket socket;
                        try {
                            socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            socket.connect();
                            String ssid = "ssid: " + newssid + "\n";
                            String password = "pwd: " + newpassword + "\n";
                            OutputStream os = socket.getOutputStream();
                            os.write(ssid.getBytes());
                            InputStream is = socket.getInputStream();
                            BufferedReader readerssid = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb4 = new StringBuilder();
                            String input = null;
                            while (!(input = readerssid.readLine()).equals("OK")) {
                                sb4.append(input);
                            }
                            String statusssid = sb4.toString();
                            os.write(password.getBytes());
                            is = socket.getInputStream();
                            BufferedReader readerpwd = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb5 = new StringBuilder();
                            String input1 = null;
                            while (!(input1 = readerpwd.readLine()).equals("OK")) {
                                sb5.append(input1);
                            }
                            String statuspwd = sb5.toString();
                            os.write("link\n".getBytes());
                            is = socket.getInputStream();
                            BufferedReader readerlink = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb6 = new StringBuilder();
                            String input2 = null;
                            while (!(input2 = readerlink.readLine()).equals("OK")) {
                                sb6.append(input2);
                            }
                            String link = sb6.toString();
                            AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
                            ad.setMessage(statusssid + statuspwd + link);
                            AlertDialog a = ad.create();
                            a.show();
                            os.close();
                            is.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog a = ad.create();
                a.show();
            }
        });
       /* Button b3 = (Button)findViewById(R.id.useIPset);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText)findViewById(R.id.newIP);
                String newip = et1.getText().toString();
                AlertDialog.Builder ad = new AlertDialog.Builder(ServerActivity.this);
                ad.setMessage(newip);
                AlertDialog a = ad.create();
                a.show();
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.back,menu);
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
