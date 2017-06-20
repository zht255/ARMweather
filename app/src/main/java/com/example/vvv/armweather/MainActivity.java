package com.example.vvv.armweather;


import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.icu.text.DateFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter blueToothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /*public void initActionBar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle("ARM weather");
        setSupportActionBar(toolbar);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.scan:
                //扫描蓝牙方法
                scanBlue();
                break;
            case R.id.about:
                //关于
                AlertDialog.Builder bd = new AlertDialog.Builder(this);
                bd.setTitle("关于");
                bd.setMessage("ARM气象站 App v1.0 \n 小组成员：\n李恺杰\n吴帆\n唐浩然\n张昊天");
                bd.setPositiveButton("×", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog a = bd.create();
                a.show();
                break;
            case R.id.date:
                //日期
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("日期");
                Date date = new Date();
                builder.setMessage(date.toString());
                builder.setPositiveButton("×", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog b = builder.create();
                b.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void scanBlue(){
        //获取蓝牙适配器
        blueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断是否有蓝牙功能
        if(blueToothAdapter != null){
            //判断蓝牙是否开启
            if(!blueToothAdapter.isEnabled()) {
                TextView t = new TextView(this);
                t.setText("蓝牙未开启，是否允许开启蓝牙？");
                t.setGravity(Gravity.CENTER);
                t.setPadding(10,10,10,10);
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setView(t);
                ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        blueToothAdapter.enable();
                    }
                });
                ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
               AlertDialog b = ab.create();
                b.show();
            }
        }

    }
}
