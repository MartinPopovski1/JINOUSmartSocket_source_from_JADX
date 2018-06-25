package com.jinoux.pwersupplysocket.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.android.jinoux.powersupplysocket.C0048R;
import com.jinoux.pwersupplysocket.customdialog.CustomDialog.Builder;

public class WelcomeActivity extends Activity {
    private static final int REQUEST_ENABLE_BT = 2;
    private static final long TIME = 1000;
    private static boolean notWantBLE = false;
    public static Activity welcomeActivity;
    public static Context welcomeContext;
    private BluetoothAdapter mBluetoothAdapter = null;
    @SuppressLint({"HandlerLeak"})
    private Handler mhandle = new C00891();
    private boolean noBluetooth = false;

    class C00891 extends Handler {
        C00891() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent i = new Intent(WelcomeActivity.welcomeContext, MainActivity.class);
            i.setFlags(268435456);
            WelcomeActivity.this.startActivity(i);
            MainActivity.notWantBLE = WelcomeActivity.notWantBLE;
            WelcomeActivity.this.finish();
        }
    }

    class C00902 implements OnClickListener {
        C00902() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (WelcomeActivity.notWantBLE) {
                WelcomeActivity.this.goToMain();
            } else if (WelcomeActivity.this.noBluetooth) {
                WelcomeActivity.this.goToMain();
            } else {
                WelcomeActivity.this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
            }
        }
    }

    class C00913 implements OnClickListener {
        C00913() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            WelcomeActivity.notWantBLE = true;
            WelcomeActivity.this.goToMain();
        }
    }

    class C00924 implements Runnable {
        C00924() {
        }

        public void run() {
            try {
                Thread.sleep(WelcomeActivity.TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WelcomeActivity.this.mhandle.sendEmptyMessage(0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0048R.layout.activity_welcome);
        welcomeContext = this;
        welcomeActivity = this;
        checkBluetooth();
    }

    @SuppressLint({"NewApi"})
    private void checkBluetooth() {
        try {
            if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                this.mBluetoothAdapter = ((BluetoothManager) getSystemService("bluetooth")).getAdapter();
                if (this.mBluetoothAdapter == null) {
                    showAlertDialog(getResources().getString(C0048R.string.no_bluetooth), getResources().getString(C0048R.string.alertOneButtonTitle), null, 1);
                    this.noBluetooth = true;
                    return;
                } else if (this.mBluetoothAdapter.isEnabled()) {
                    goToMain();
                    return;
                } else {
                    showAlertDialog(getResources().getString(C0048R.string.openBluetoothMsg), getResources().getString(C0048R.string.alertTwoButtonTitle1), getResources().getString(C0048R.string.alertTwoButtonTitle2), 1);
                    return;
                }
            }
            this.noBluetooth = true;
            showAlertDialog(getResources().getString(C0048R.string.ble_not_supported), getResources().getString(C0048R.string.alertOneButtonTitle), null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlertDialog(String content, String oneButton, String twoButton, int theme) {
        Builder builder = new Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new C00902());
        builder.setNegativeButton(twoButton, new C00913());
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == 0) {
            notWantBLE = true;
            showAlertDialog(getResources().getString(C0048R.string.open_bluetooth_failed), getResources().getString(C0048R.string.alertOneButtonTitle), null, 1);
            return;
        }
        if (requestCode == 2 && resultCode == -1) {
            goToMain();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goToMain() {
        new Thread(new C00924()).start();
    }
}
