package com.jinoux.pwersupplysocket.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.jinoux.powersupplysocket.C0048R;
import com.android.jinoux.util.Log;
import com.jinoux.pwersupplysocket.customdialog.CustomDialog.Builder;
import com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO;
import com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;

@SuppressLint({"NewApi"})
public class EditSocketActivity extends Activity {
    public static BLEDeviceInfoDAO bLEDeviceInfoDAO;
    public static BLEDeviceInfo connectBDI;
    public static int height;
    public static int width;
    private DeleterecordOnclick deleterecordOnclick;
    private Button deleterecordbt;
    private LinearLayout deleterecordlin;
    @SuppressLint({"HandlerLeak"})
    public Handler deviceHandler = new C00711();
    private EditText edit_device_name;
    private TextView edit_device_serial_number;
    private LinearLayout editlocklinear;
    private LinearLayout editlocklinear_lin1;
    private LinearLayout editlocklinear_lin2;
    private LinearLayout editlocklineartop1;
    private LinearLayout editlocklins1;
    private Button editlocktop_left_button;
    private Button editlocktop_right_bt;
    private TextView editlocktop_text;
    private HomepageOnclick homepageOnclick;
    private TextView lock_device_name;
    private TextView lock_series_number1;
    private SaveOnclick saveOnclick;

    class C00711 extends Handler {
        C00711() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            }
        }
    }

    class C00722 implements Runnable {
        C00722() {
        }

        public void run() {
            EditSocketActivity.this.inputMethodManager(EditSocketActivity.this.edit_device_name);
        }
    }

    class C00733 implements OnClickListener {
        C00733() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C00744 implements OnClickListener {
        C00744() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C00755 implements OnClickListener {
        C00755() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (EditSocketActivity.connectBDI != null) {
                EditSocketActivity.bLEDeviceInfoDAO.deleteByAddress(EditSocketActivity.connectBDI.getAddress());
                Intent intent = new Intent();
                intent.setClass(EditSocketActivity.this.getApplication(), MainActivity.class);
                intent.putExtras(intent);
                EditSocketActivity.this.finish();
                EditSocketActivity.this.startActivity(intent);
            }
            dialog.dismiss();
        }
    }

    class C00766 implements OnClickListener {
        C00766() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class DeleterecordOnclick implements View.OnClickListener {
        private DeleterecordOnclick() {
        }

        public void onClick(View v) {
            EditSocketActivity.this.showAlertDialogde(EditSocketActivity.this.getResources().getString(C0048R.string.mainAlertDeleteMsg), EditSocketActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), EditSocketActivity.this.getResources().getString(C0048R.string.cancle), 0);
        }
    }

    private class HomepageOnclick implements View.OnClickListener {
        private HomepageOnclick() {
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(EditSocketActivity.this.getApplication(), MainActivity.class);
            intent.putExtras(intent);
            EditSocketActivity.this.finish();
            EditSocketActivity.this.startActivity(intent);
        }
    }

    private class SaveOnclick implements View.OnClickListener {
        private SaveOnclick() {
        }

        public void onClick(View v) {
            String xname = EditSocketActivity.this.edit_device_name.getText().toString();
            if (EditSocketActivity.connectBDI == null) {
                EditSocketActivity.this.showAlertDialog(EditSocketActivity.this.getResources().getString(C0048R.string.saveDeviceFail), EditSocketActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null, 0);
            } else if (xname.equals("")) {
                EditSocketActivity.this.showAlertDialog(EditSocketActivity.this.getResources().getString(C0048R.string.saveDeviceFail), EditSocketActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null, 0);
            } else {
                EditSocketActivity.connectBDI.setName(xname);
                EditSocketActivity.bLEDeviceInfoDAO.updateByAddress(EditSocketActivity.connectBDI);
                Intent intent = new Intent();
                intent.setClass(EditSocketActivity.this.getApplication(), MainActivity.class);
                intent.putExtras(intent);
                EditSocketActivity.this.finish();
                EditSocketActivity.this.startActivity(intent);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.activity_editsocket);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Log.m2i("宽：" + width + "  高：" + height);
        this.editlocklins1 = (LinearLayout) findViewById(C0048R.id.editlocklins1);
        this.editlocklineartop1 = (LinearLayout) findViewById(C0048R.id.editlocklineartop1);
        this.editlocklinear = (LinearLayout) findViewById(C0048R.id.editlocklinear);
        this.editlocklinear_lin1 = (LinearLayout) findViewById(C0048R.id.editlocklinear_lin1);
        this.editlocklinear_lin2 = (LinearLayout) findViewById(C0048R.id.editlocklinear_lin2);
        this.deleterecordlin = (LinearLayout) findViewById(C0048R.id.deleterecordlin);
        this.editlocktop_left_button = (Button) findViewById(C0048R.id.editlocktop_left_button);
        this.editlocktop_right_bt = (Button) findViewById(C0048R.id.editlocktop_right_bt);
        this.editlocktop_text = (TextView) findViewById(C0048R.id.editlocktop_text);
        this.lock_series_number1 = (TextView) findViewById(C0048R.id.lock_series_number1);
        this.edit_device_serial_number = (TextView) findViewById(C0048R.id.edit_device_serial_number);
        this.lock_device_name = (TextView) findViewById(C0048R.id.lock_device_name);
        this.edit_device_name = (EditText) findViewById(C0048R.id.edit_device_name);
        this.deleterecordbt = (Button) findViewById(C0048R.id.deleterecordbt);
        bLEDeviceInfoDAO = new BLEDeviceInfoDAO(this);
        this.deleterecordOnclick = new DeleterecordOnclick();
        this.homepageOnclick = new HomepageOnclick();
        this.saveOnclick = new SaveOnclick();
        szlayoutParams();
        this.editlocktop_left_button.setOnClickListener(this.homepageOnclick);
    }

    public void szlayoutParams() {
        LayoutParams relatlins1ip = this.editlocklins1.getLayoutParams();
        relatlins1ip.width = width;
        relatlins1ip.height = height;
        this.editlocklins1.setLayoutParams(relatlins1ip);
        LayoutParams lineartop1ip = this.editlocklineartop1.getLayoutParams();
        lineartop1ip.width = width;
        lineartop1ip.height = (height / 100) * 10;
        this.editlocklineartop1.setLayoutParams(lineartop1ip);
        LayoutParams editnewlocklinearip = this.editlocklinear.getLayoutParams();
        editnewlocklinearip.width = width;
        editnewlocklinearip.height = (height / 100) * 80;
        this.editlocklinear.setLayoutParams(editnewlocklinearip);
        LayoutParams editnewlocklinear_lin1ip = this.editlocklinear_lin1.getLayoutParams();
        editnewlocklinear_lin1ip.width = width;
        editnewlocklinear_lin1ip.height = height / 10;
        this.editlocklinear_lin1.setLayoutParams(editnewlocklinear_lin1ip);
        this.editlocklinear_lin2.setLayoutParams(editnewlocklinear_lin1ip);
        LayoutParams deleterecordlinip = this.deleterecordlin.getLayoutParams();
        deleterecordlinip.width = width;
        deleterecordlinip.height = (height / 100) * 9;
        this.deleterecordlin.setLayoutParams(deleterecordlinip);
        LayoutParams top_left_buttonip = this.editlocktop_left_button.getLayoutParams();
        top_left_buttonip.width = (width / 10) * 2;
        top_left_buttonip.height = height / 10;
        this.editlocktop_left_button.setLayoutParams(top_left_buttonip);
        this.editlocktop_right_bt.setLayoutParams(top_left_buttonip);
        this.editlocktop_left_button.setOnClickListener(this.homepageOnclick);
        this.editlocktop_right_bt.setOnClickListener(this.saveOnclick);
        LayoutParams top_textip = this.editlocktop_text.getLayoutParams();
        top_textip.width = (width / 10) * 6;
        top_textip.height = height / 10;
        this.editlocktop_text.setLayoutParams(top_textip);
        LayoutParams lock_series_number1ip = this.lock_series_number1.getLayoutParams();
        lock_series_number1ip.width = (width / 10) * 4;
        lock_series_number1ip.height = height / 15;
        this.lock_series_number1.setLayoutParams(lock_series_number1ip);
        this.lock_device_name.setLayoutParams(lock_series_number1ip);
        LayoutParams edit_device_serial_numberip = this.edit_device_serial_number.getLayoutParams();
        edit_device_serial_numberip.width = (width / 10) * 6;
        edit_device_serial_numberip.height = height / 15;
        this.edit_device_serial_number.setLayoutParams(edit_device_serial_numberip);
        LayoutParams edit_device_nameip = this.edit_device_name.getLayoutParams();
        edit_device_nameip.width = (width / 10) * 6;
        edit_device_nameip.height = height / 15;
        this.edit_device_name.setLayoutParams(edit_device_nameip);
        LayoutParams scanninglockbtip = this.deleterecordbt.getLayoutParams();
        scanninglockbtip.width = width / 2;
        scanninglockbtip.height = height / 15;
        this.deleterecordbt.setLayoutParams(scanninglockbtip);
        this.deleterecordbt.setOnClickListener(this.deleterecordOnclick);
        if (connectBDI != null) {
            this.edit_device_serial_number.setText(connectBDI.getSerialNumber());
            this.edit_device_name.setText(connectBDI.getName());
        }
        Runnable r = new C00722();
        this.deviceHandler.removeCallbacks(r);
        this.deviceHandler.postDelayed(r, 500);
    }

    private void inputMethodManager(View v) {
        ((InputMethodManager) v.getContext().getSystemService("input_method")).showSoftInput(v, 0);
    }

    public void showAlertDialog(String content, String oneButton, String twoButton, int theme) {
        Builder builder = new Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new C00733());
        builder.setNegativeButton(twoButton, new C00744());
        builder.create().show();
    }

    public void showAlertDialogde(String content, String oneButton, String twoButton, int theme) {
        Builder builder = new Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new C00755());
        builder.setNegativeButton(twoButton, new C00766());
        builder.create().show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0048R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
