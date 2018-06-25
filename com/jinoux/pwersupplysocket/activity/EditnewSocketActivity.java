package com.jinoux.pwersupplysocket.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.jinoux.powersupplysocket.C0048R;
import com.android.jinoux.util.Log;
import com.jinoux.pwersupplysocket.customdialog.CustomDialog.Builder;
import com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO;
import com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;

public class EditnewSocketActivity extends Activity {
    public static BLEDeviceInfoDAO bLEDeviceInfoDAO;
    public static BLEDeviceInfo connectBDI;
    public static int height;
    public static int width;
    private EditText edit_device_name;
    private TextView edit_device_serial_number;
    private LinearLayout editnewlocklinear;
    private LinearLayout editnewlocklinear_lin1;
    private LinearLayout editnewlocklinear_lin2;
    private LinearLayout editnewlocklineartop1;
    private LinearLayout editnewlocklins1;
    private Button editnewlocktop_left_button;
    private Button editnewlocktop_right_bt;
    private TextView editnewlocktop_text;
    private TextView lock_device_name;
    private TextView lock_series_number1;
    private SaveOnclick saveOnclick;

    class C00771 implements OnClickListener {
        C00771() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C00782 implements OnClickListener {
        C00782() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class SaveOnclick implements View.OnClickListener {
        private SaveOnclick() {
        }

        public void onClick(View v) {
            if (EditnewSocketActivity.connectBDI != null) {
                String xname = EditnewSocketActivity.this.edit_device_name.getText().toString();
                if (xname.equals("")) {
                    EditnewSocketActivity.this.showAlertDialog(EditnewSocketActivity.this.getResources().getString(C0048R.string.saveDeviceFail), EditnewSocketActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null, 0);
                    return;
                }
                EditnewSocketActivity.connectBDI.setName(xname);
                EditnewSocketActivity.bLEDeviceInfoDAO.updateByAddress(EditnewSocketActivity.connectBDI);
                Intent intent = new Intent();
                intent.setClass(EditnewSocketActivity.this.getApplication(), AddSocketActivity.class);
                intent.putExtras(intent);
                EditnewSocketActivity.this.finish();
                EditnewSocketActivity.this.startActivity(intent);
                return;
            }
            EditnewSocketActivity.this.showAlertDialog(EditnewSocketActivity.this.getResources().getString(C0048R.string.saveDeviceFail), EditnewSocketActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null, 0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.activity_editnewsocket);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Log.m2i("宽：" + width + "  高：" + height);
        bLEDeviceInfoDAO = new BLEDeviceInfoDAO(this);
        this.editnewlocklins1 = (LinearLayout) findViewById(C0048R.id.editnewlocklins1);
        this.editnewlocklineartop1 = (LinearLayout) findViewById(C0048R.id.editnewlocklineartop1);
        this.editnewlocklinear = (LinearLayout) findViewById(C0048R.id.editnewlocklinear);
        this.editnewlocklinear_lin1 = (LinearLayout) findViewById(C0048R.id.editnewlocklinear_lin1);
        this.editnewlocklinear_lin2 = (LinearLayout) findViewById(C0048R.id.editnewlocklinear_lin2);
        this.editnewlocktop_left_button = (Button) findViewById(C0048R.id.editnewlocktop_left_button);
        this.editnewlocktop_right_bt = (Button) findViewById(C0048R.id.editnewlocktop_right_bt);
        this.editnewlocktop_text = (TextView) findViewById(C0048R.id.editnewlocktop_text);
        this.lock_series_number1 = (TextView) findViewById(C0048R.id.lock_series_number1);
        this.edit_device_serial_number = (TextView) findViewById(C0048R.id.edit_device_serial_number);
        this.lock_device_name = (TextView) findViewById(C0048R.id.lock_device_name);
        this.edit_device_name = (EditText) findViewById(C0048R.id.edit_device_name);
        this.saveOnclick = new SaveOnclick();
        LayoutParams relatlins1ip = this.editnewlocklins1.getLayoutParams();
        relatlins1ip.width = width;
        relatlins1ip.height = height;
        this.editnewlocklins1.setLayoutParams(relatlins1ip);
        LayoutParams lineartop1ip = this.editnewlocklineartop1.getLayoutParams();
        lineartop1ip.width = width;
        lineartop1ip.height = height / 10;
        this.editnewlocklineartop1.setLayoutParams(lineartop1ip);
        LayoutParams editnewlocklinearip = this.editnewlocklinear.getLayoutParams();
        editnewlocklinearip.width = width;
        editnewlocklinearip.height = (height / 10) * 9;
        this.editnewlocklinear.setLayoutParams(editnewlocklinearip);
        LayoutParams editnewlocklinear_lin1ip = this.editnewlocklinear_lin1.getLayoutParams();
        editnewlocklinear_lin1ip.width = width;
        editnewlocklinear_lin1ip.height = height / 10;
        this.editnewlocklinear_lin1.setLayoutParams(editnewlocklinear_lin1ip);
        this.editnewlocklinear_lin2.setLayoutParams(editnewlocklinear_lin1ip);
        LayoutParams top_left_buttonip = this.editnewlocktop_left_button.getLayoutParams();
        top_left_buttonip.width = (width / 10) * 2;
        top_left_buttonip.height = height / 10;
        this.editnewlocktop_left_button.setLayoutParams(top_left_buttonip);
        this.editnewlocktop_right_bt.setLayoutParams(top_left_buttonip);
        this.editnewlocktop_right_bt.setOnClickListener(this.saveOnclick);
        LayoutParams top_textip = this.editnewlocktop_text.getLayoutParams();
        top_textip.width = (width / 10) * 6;
        top_textip.height = height / 10;
        this.editnewlocktop_text.setLayoutParams(top_textip);
        LayoutParams lock_series_number1ip = this.lock_series_number1.getLayoutParams();
        lock_series_number1ip.width = (width / 10) * 4;
        lock_series_number1ip.height = height / 10;
        this.lock_series_number1.setLayoutParams(lock_series_number1ip);
        this.lock_device_name.setLayoutParams(lock_series_number1ip);
        LayoutParams edit_device_serial_numberip = this.edit_device_serial_number.getLayoutParams();
        edit_device_serial_numberip.width = (width / 10) * 6;
        edit_device_serial_numberip.height = height / 10;
        this.edit_device_serial_number.setLayoutParams(edit_device_serial_numberip);
        LayoutParams edit_device_nameip = this.edit_device_name.getLayoutParams();
        edit_device_nameip.width = (width / 10) * 6;
        edit_device_nameip.height = height / 15;
        this.edit_device_name.setLayoutParams(edit_device_nameip);
        if (connectBDI != null) {
            this.edit_device_serial_number.setText(connectBDI.getSerialNumber());
            this.edit_device_name.setText(connectBDI.getName());
        }
    }

    public void showAlertDialog(String content, String oneButton, String twoButton, int theme) {
        Builder builder = new Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new C00771());
        builder.setNegativeButton(twoButton, new C00782());
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
