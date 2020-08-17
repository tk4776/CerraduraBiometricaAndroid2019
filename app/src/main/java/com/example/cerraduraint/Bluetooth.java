package com.example.cerraduraint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class Bluetooth extends AppCompatActivity {

    //1))
    //DEPURACION DE LOGCAT
    private static final String TAG = "DispositivosBluetooth";
    ListView IdLista;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    //Declaracion de campos
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter mPairedDeviceArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bluetooth);
    }

    @Override
    public void onResume(){
        super.onResume();
        VerificarEstadoBT();

        //Inicializa el array que contendra la lista de los dispositivos Bluetooth
        mPairedDeviceArrayAdapter = new ArrayAdapter(this,R.layout.activity_nombre_dispositivos);

        IdLista = (ListView) findViewById(R.id.IdLista);
        IdLista.setAdapter(mPairedDeviceArrayAdapter);
        IdLista.setOnItemClickListener(mDeviceClickListener);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        //--->
        Set <BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                mPairedDeviceArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            //OBTENER MAC DEL DISPOSITIVO
            String info = ((TextView)v).getText().toString();
            String address = info.substring(info.length()-17);

            Intent i = new Intent(Bluetooth.this, activar.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS,address);
            startActivity(i);
        }
    };

    private void VerificarEstadoBT(){
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBtAdapter==null){
            Toast.makeText(getBaseContext(), "El Dispositivo No Soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }   else{
            if(mBtAdapter.isEnabled()){
                Log.d(TAG, "...Bluetooth Activado...");
            }   else{
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
}