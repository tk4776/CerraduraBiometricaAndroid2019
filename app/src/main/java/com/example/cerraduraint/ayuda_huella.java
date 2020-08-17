package com.example.cerraduraint;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import android.hardware.fingerprint.FingerprintManager;

public class ayuda_huella extends FingerprintManager.AuthenticationCallback {

    private Context mContext;
    private CancellationSignal cancellationSignal;

    public ayuda_huella(Context mContext) {
        this.mContext = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.showInformationMessage("ERROR DE IDENTIFICACIÓN" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.showInformationMessage("¿AYUDA PARA IDENTIFICAR?\n"+ helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.showInformationMessage("FALLO DE IDENTIFICACIÓN");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.showInformationMessage("IDENTIFICACIÓN EXITOSA");
        mContext.startActivity(new Intent(mContext, Bluetooth.class));
    }

    private void showInformationMessage(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}