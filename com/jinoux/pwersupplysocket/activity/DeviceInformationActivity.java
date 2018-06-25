package com.jinoux.pwersupplysocket.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.jinoux.powersupplysocket.C0048R;
import com.android.jinoux.powersupplysocketlibrary.BluetoothLeService;
import com.android.jinoux.util.AesCipherAndInvCipher;
import com.android.jinoux.util.Log;
import com.android.jinoux.util.Tools;
import com.jinoux.pwersupplysocket.customdialog.CustomDialog.Builder;
import com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO;
import com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;
import com.jinoux.pwersupplysocket.modle.ScanDeviceInfo;

public class DeviceInformationActivity extends Activity {
    private static final int CONNECT_TIMEOUT = 10000;
    public static BLEDeviceInfoDAO bLEDeviceInfoDAO;
    public static BLEDeviceInfo connectBDI;
    public static Handler connectHnadler;
    public static int height;
    public static BluetoothLeService mBluetoothLeService;
    private static int manipulatenum = 0;
    public static int nm = 0;
    public static int socketid = 1;
    public static int width;
    private int action;
    private ImageView clickkgimage;
    private Runnable connectTimeoutRunnable = new C00672();
    private ControllockOnclick controllockOnclick;
    private TextView deivcePower_text1;
    private ImageView deivcePower_text2;
    private LinearLayout deivceinforlin1;
    private LinearLayout deivceinforlin2;
    private LinearLayout deivceinforlin3;
    private LinearLayout deivceinforlin4;
    private LinearLayout deivceinforlin5;
    private LinearLayout deivceinforlin6;
    private LinearLayout deivceinforlin7;
    private LinearLayout deivceinforlin8;
    private LinearLayout deivcelinearbottom1;
    private LinearLayout deivcelineartop1;
    private TextView deivcename_text1;
    private ImageView deivcename_text2;
    private LinearLayout deivcerelatlins1;
    private TextView deivcestate_text1;
    private ImageView deivcestate_text2;
    private TextView deivcetime_text1;
    private Button deivcetop_left_button;
    private Button deivcetop_right_bt;
    private TextView deivcetop_text;
    private DeletelockOnclick deletelockOnclick;
    @SuppressLint({"HandlerLeak"})
    public Handler deviceHandler = new C00661();
    private EditlockOnclick editlockOnclick;
    private HomepageOnclick homepageOnclick;
    private LayoutInflater inflater;
    private boolean isConnected = false;
    private final BroadcastReceiver mGattUpdateReceiver = new C00683();
    private DeviceInformationActivity ma;
    private MenrigthOnclick menrigthOnclick;
    private PopupWindow popupwindow;
    private byte[] rePairData;
    private ScanDeviceInfo scan;
    private ScanninglockOnclick scanninglockOnclick;
    private Button settimesbt;
    private TextView setworktimes_textg1;
    private TextView setworktimes_textg1s;
    private TextView setworktimes_textk1;
    private TextView setworktimes_textk1s;
    private Button setworktimesbt1;
    private TextView times_textf;
    private TextView times_texts;

    class C00661 extends Handler {
        C00661() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    DeviceInformationActivity.manipulatenum = 0;
                    DeviceInformationActivity.this.deivcename_text2.setImageResource(C0048R.drawable.white);
                    DeviceInformationActivity.this.deivcePower_text2.setImageResource(C0048R.drawable.white);
                    DeviceInformationActivity.this.deivcestate_text2.setImageResource(C0048R.drawable.white);
                    return;
                default:
                    return;
            }
        }
    }

    class C00672 implements Runnable {
        C00672() {
        }

        public void run() {
            if (DeviceInformationActivity.connectBDI != null) {
                DeviceInformationActivity.manipulatenum = 0;
                if (DeviceInformationActivity.mBluetoothLeService == null) {
                    DeviceInformationActivity.mBluetoothLeService = new BluetoothLeService();
                }
                DeviceInformationActivity.mBluetoothLeService.disconnects();
            }
        }
    }

    class C00683 extends BroadcastReceiver {
        C00683() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.m2i("action = " + action);
            if (!BluetoothLeService.ACTION_GATT_CONNECTED.equals(action) && !BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action) && !BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action) && !BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                if (BluetoothLeService.ACTION_GATT_HANDLERDATAERROR.equals(action)) {
                    DeviceInformationActivity.this.isConnected = false;
                    DeviceInformationActivity.manipulatenum = 0;
                } else if (BluetoothLeService.ACTION_GATT_READCHARACTERISTICSUCCESS.equals(action)) {
                    DeviceInformationActivity.this.isConnected = true;
                    DeviceInformationActivity.this.sendId();
                } else if (BluetoothLeService.ACTION_GATT_READCHARACTERISTICERROR.equals(action)) {
                    DeviceInformationActivity.this.isConnected = false;
                    DeviceInformationActivity.manipulatenum = 0;
                } else if (BluetoothLeService.ACTION_GATT_DATARECEIVED.equals(action)) {
                    byte[] by = intent.getByteArrayExtra(BluetoothLeService.DATA_NAME);
                    if (DeviceInformationActivity.connectBDI != null) {
                        DeviceInformationActivity.this.didDataReceived(by);
                    }
                }
            }
        }
    }

    class C00694 implements OnClickListener {
        C00694() {
        }

        public void onClick(DialogInterface dialog, int which) {
            DeviceInformationActivity.manipulatenum = 0;
            dialog.dismiss();
        }
    }

    class C00705 implements OnClickListener {
        C00705() {
        }

        public void onClick(DialogInterface dialog, int which) {
            DeviceInformationActivity.manipulatenum = 0;
            dialog.dismiss();
        }
    }

    private class ControllockOnclick implements View.OnClickListener {
        private ControllockOnclick() {
        }

        public void onClick(View v) {
            DeviceInformationActivity.this.dismiss();
        }
    }

    private class DeletelockOnclick implements View.OnClickListener {
        private DeletelockOnclick() {
        }

        public void onClick(View v) {
            DeviceInformationActivity.manipulatenum = 3;
            DeviceInformationActivity.this.dismiss();
            DeviceInformationActivity.this.showAlertDialog(DeviceInformationActivity.this.getResources().getString(C0048R.string.mainAlertDeleteMsg), DeviceInformationActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), DeviceInformationActivity.this.getResources().getString(C0048R.string.cancle), 0);
        }
    }

    private class EditlockOnclick implements View.OnClickListener {
        private EditlockOnclick() {
        }

        public void onClick(View v) {
            if (DeviceInformationActivity.connectBDI != null) {
                DeviceInformationActivity.this.dismiss();
                EditSocketActivity.connectBDI = DeviceInformationActivity.connectBDI;
                Intent intent = new Intent();
                intent.setClass(DeviceInformationActivity.this.getApplication(), EditSocketActivity.class);
                intent.putExtras(intent);
                DeviceInformationActivity.this.finish();
                DeviceInformationActivity.this.startActivity(intent);
            }
        }
    }

    private class HomepageOnclick implements View.OnClickListener {
        private HomepageOnclick() {
        }

        public void onClick(View v) {
            DeviceInformationActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(DeviceInformationActivity.this.getApplication(), MainActivity.class);
            intent.putExtras(intent);
            DeviceInformationActivity.this.finish();
            DeviceInformationActivity.this.startActivity(intent);
        }
    }

    private class MenrigthOnclick implements View.OnClickListener {
        private MenrigthOnclick() {
        }

        public void onClick(View v) {
            if (DeviceInformationActivity.this.popupwindow != null) {
                DeviceInformationActivity.this.dismiss();
                return;
            }
            View view = DeviceInformationActivity.this.inflater.inflate(C0048R.layout.popuplinear, null);
            DeviceInformationActivity.this.getpnpmenu(view);
            DeviceInformationActivity.this.popupwindow = new PopupWindow(view, (DeviceInformationActivity.width / 10) * 2, DeviceInformationActivity.height / 5);
            DeviceInformationActivity.this.popupwindow.showAsDropDown(v);
            DeviceInformationActivity.this.popupwindow.showAtLocation(DeviceInformationActivity.this.deivcetop_right_bt, 17, 20, 20);
        }
    }

    private class ScanninglockOnclick implements View.OnClickListener {
        private ScanninglockOnclick() {
        }

        public void onClick(View v) {
            DeviceInformationActivity.this.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.activity_deivceinformation);
        this.inflater = LayoutInflater.from(this);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        this.menrigthOnclick = new MenrigthOnclick();
        this.scanninglockOnclick = new ScanninglockOnclick();
        this.editlockOnclick = new EditlockOnclick();
        this.controllockOnclick = new ControllockOnclick();
        this.deletelockOnclick = new DeletelockOnclick();
        this.homepageOnclick = new HomepageOnclick();
        bLEDeviceInfoDAO = new BLEDeviceInfoDAO(this);
        this.ma = this;
        manipulatenum = 0;
        this.deivcerelatlins1 = (LinearLayout) findViewById(C0048R.id.deivcerelatlins1);
        this.deivcelineartop1 = (LinearLayout) findViewById(C0048R.id.deivcelineartop1);
        this.deivcelinearbottom1 = (LinearLayout) findViewById(C0048R.id.deivcelinearbottom1);
        this.deivcetop_left_button = (Button) findViewById(C0048R.id.deivcetop_left_button);
        this.deivcetop_text = (TextView) findViewById(C0048R.id.deivcetop_text);
        this.deivcetop_right_bt = (Button) findViewById(C0048R.id.deivcetop_right_bt);
        this.deivceinforlin1 = (LinearLayout) findViewById(C0048R.id.deivceinforlin1);
        this.deivceinforlin2 = (LinearLayout) findViewById(C0048R.id.deivceinforlin2);
        this.deivceinforlin3 = (LinearLayout) findViewById(C0048R.id.deivceinforlin3);
        this.deivcename_text1 = (TextView) findViewById(C0048R.id.deivcename_text1);
        this.deivcename_text2 = (ImageView) findViewById(C0048R.id.deivcename_text2);
        this.deivcePower_text1 = (TextView) findViewById(C0048R.id.deivcePower_text1);
        this.deivcePower_text2 = (ImageView) findViewById(C0048R.id.deivcePower_text2);
        this.deivcestate_text1 = (TextView) findViewById(C0048R.id.deivcestate_text1);
        this.deivcestate_text2 = (ImageView) findViewById(C0048R.id.deivcestate_text2);
        this.deivceinforlin4 = (LinearLayout) findViewById(C0048R.id.deivceinforlin4);
        this.deivceinforlin5 = (LinearLayout) findViewById(C0048R.id.deivceinforlin5);
        this.deivceinforlin6 = (LinearLayout) findViewById(C0048R.id.deivceinforlin6);
        this.deivceinforlin7 = (LinearLayout) findViewById(C0048R.id.deivceinforlin7);
        this.deivceinforlin8 = (LinearLayout) findViewById(C0048R.id.deivceinforlin8);
        this.deivcetime_text1 = (TextView) findViewById(C0048R.id.deivcetime_text1);
        this.settimesbt = (Button) findViewById(C0048R.id.settimesbt);
        this.setworktimesbt1 = (Button) findViewById(C0048R.id.setworktimesbt1);
        this.times_texts = (TextView) findViewById(C0048R.id.times_texts);
        this.times_textf = (TextView) findViewById(C0048R.id.times_textf);
        this.setworktimes_textk1 = (TextView) findViewById(C0048R.id.setworktimes_textk1);
        this.setworktimes_textk1s = (TextView) findViewById(C0048R.id.setworktimes_textk1s);
        this.setworktimes_textg1 = (TextView) findViewById(C0048R.id.setworktimes_textg1);
        this.setworktimes_textg1s = (TextView) findViewById(C0048R.id.setworktimes_textg1s);
        szlayoutParams();
        init();
        this.isConnected = false;
        manipulatenum = 1;
    }

    public void szlayoutParams() {
        LayoutParams relatlins1ip = this.deivcerelatlins1.getLayoutParams();
        relatlins1ip.width = width;
        relatlins1ip.height = height;
        this.deivcerelatlins1.setLayoutParams(relatlins1ip);
        LayoutParams lineartop1ip = this.deivcelineartop1.getLayoutParams();
        lineartop1ip.width = width;
        lineartop1ip.height = height / 10;
        this.deivcelineartop1.setLayoutParams(lineartop1ip);
        int wi = (width / 100) * 95;
        LayoutParams linearbottom1ip = this.deivcelinearbottom1.getLayoutParams();
        linearbottom1ip.width = wi;
        linearbottom1ip.height = (height / 100) * 88;
        this.deivcelinearbottom1.setLayoutParams(linearbottom1ip);
        LayoutParams top_left_buttonip = this.deivcetop_left_button.getLayoutParams();
        top_left_buttonip.width = (width / 10) * 2;
        top_left_buttonip.height = height / 10;
        this.deivcetop_left_button.setLayoutParams(top_left_buttonip);
        this.deivcetop_left_button.setOnClickListener(this.homepageOnclick);
        LayoutParams top_textip = this.deivcetop_text.getLayoutParams();
        top_textip.width = (width / 10) * 6;
        top_textip.height = height / 10;
        this.deivcetop_text.setLayoutParams(top_textip);
        LayoutParams top_right_buttonip = this.deivcetop_right_bt.getLayoutParams();
        top_right_buttonip.width = (width / 10) * 2;
        top_right_buttonip.height = height / 10;
        this.deivcetop_right_bt.setLayoutParams(top_right_buttonip);
        this.deivcetop_right_bt.setOnClickListener(this.menrigthOnclick);
        LayoutParams deivceinforlinip = this.deivceinforlin1.getLayoutParams();
        deivceinforlinip.width = wi;
        deivceinforlinip.height = height / 12;
        this.deivceinforlin1.setLayoutParams(deivceinforlinip);
        this.deivceinforlin2.setLayoutParams(deivceinforlinip);
        this.deivceinforlin3.setLayoutParams(deivceinforlinip);
        LayoutParams deivcename_textip = this.deivcename_text1.getLayoutParams();
        deivcename_textip.width = (wi / 10) * 7;
        deivcename_textip.height = height / 15;
        this.deivcename_text1.setLayoutParams(deivcename_textip);
        this.deivcePower_text1.setLayoutParams(deivcename_textip);
        this.deivcestate_text1.setLayoutParams(deivcename_textip);
        LayoutParams imagesip = this.deivcename_text2.getLayoutParams();
        imagesip.width = (wi / 10) * 3;
        imagesip.height = height / 15;
        this.deivcename_text2.setLayoutParams(imagesip);
        this.deivcePower_text2.setLayoutParams(imagesip);
        this.deivcestate_text2.setLayoutParams(imagesip);
        socketid = 3;
        if (connectBDI != null) {
            this.deivcetop_text.setText(connectBDI.getName());
            if (connectBDI.getLastState().equals("1")) {
                this.deivcename_text2.setImageResource(C0048R.drawable.green);
            } else {
                this.deivcename_text2.setImageResource(C0048R.drawable.white);
                this.deivcePower_text2.setImageResource(C0048R.drawable.white);
                this.deivcestate_text2.setImageResource(C0048R.drawable.white);
            }
            if (socketid == 1) {
                this.deivcePower_text1.setVisibility(8);
                this.deivcePower_text2.setVisibility(8);
                this.deivcestate_text1.setVisibility(8);
                this.deivcestate_text2.setVisibility(8);
            } else if (socketid == 2) {
                this.deivcestate_text1.setVisibility(8);
                this.deivcestate_text2.setVisibility(8);
            }
        }
        LayoutParams deivceinforlin4ip = this.deivceinforlin4.getLayoutParams();
        deivceinforlin4ip.width = wi;
        deivceinforlin4ip.height = height / 15;
        this.deivceinforlin4.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin5.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin6.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin7.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin8.setLayoutParams(deivceinforlin4ip);
        LayoutParams deivcetime_text1ip = this.deivcetime_text1.getLayoutParams();
        deivcetime_text1ip.width = wi / 2;
        deivcetime_text1ip.height = height / 15;
        this.deivcetime_text1.setLayoutParams(deivcetime_text1ip);
        LayoutParams settimesbtip = this.settimesbt.getLayoutParams();
        settimesbtip.width = wi - (wi / 2);
        settimesbtip.height = height / 15;
        this.settimesbt.setLayoutParams(settimesbtip);
        LayoutParams times_textsip = this.times_texts.getLayoutParams();
        times_textsip.width = wi / 2;
        times_textsip.height = height / 15;
        this.deivcetime_text1.setLayoutParams(times_textsip);
        this.times_texts.setLayoutParams(times_textsip);
        this.times_textf.setLayoutParams(times_textsip);
        this.setworktimes_textk1.setLayoutParams(times_textsip);
        this.setworktimes_textk1s.setLayoutParams(times_textsip);
        this.setworktimes_textg1.setLayoutParams(times_textsip);
        this.setworktimes_textg1s.setLayoutParams(times_textsip);
        LayoutParams setworktimesbt1ip = this.setworktimesbt1.getLayoutParams();
        setworktimesbt1ip.width = wi / 2;
        setworktimesbt1ip.height = height / 15;
        this.setworktimesbt1.setLayoutParams(setworktimesbt1ip);
    }

    public void ConnectDevice() {
        this.deviceHandler.removeCallbacks(this.connectTimeoutRunnable);
        this.deviceHandler.postDelayed(this.connectTimeoutRunnable, 10000);
        if (mBluetoothLeService != null) {
            mBluetoothLeService.connect(connectBDI.getAddress(), 5);
        }
    }

    public void getpnpmenu(View view) {
        Button bt1 = (Button) view.findViewById(C0048R.id.menupontbt1);
        Button bt2 = (Button) view.findViewById(C0048R.id.menupontbt2);
        Button bt3 = (Button) view.findViewById(C0048R.id.menupontbt3);
        LayoutParams bt1ip = bt1.getLayoutParams();
        bt1ip.width = (width / 10) * 2;
        bt1ip.height = height / 15;
        bt1.setLayoutParams(bt1ip);
        bt2.setLayoutParams(bt1ip);
        bt3.setLayoutParams(bt1ip);
        bt1.setText(getResources().getString(C0048R.string.menupont4));
        bt2.setText(getResources().getString(C0048R.string.queryButtonText));
        bt3.setText(getResources().getString(C0048R.string.menupont6));
        bt1.setOnClickListener(this.editlockOnclick);
        bt2.setOnClickListener(this.scanninglockOnclick);
        bt3.setOnClickListener(this.deletelockOnclick);
    }

    public void dismiss() {
        if (this.popupwindow != null) {
            this.popupwindow.dismiss();
            this.popupwindow = null;
        }
    }

    private void showReset() {
        if (connectBDI != null) {
            connectBDI.setReset("1");
            bLEDeviceInfoDAO.updateByAddress(connectBDI);
            this.deivcename_text2.setImageResource(C0048R.drawable.white);
            this.deivcePower_text2.setImageResource(C0048R.drawable.white);
            this.deivcestate_text2.setImageResource(C0048R.drawable.white);
        }
    }

    private void sendId() {
        byte[] id = BluetoothLeService.myId;
        if (id == null || id.length != 6) {
            Log.m2i("Not found bluetooth address!");
            id = new byte[]{(byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1};
        }
        if (connectBDI != null) {
            byte[] checkKey = connectBDI.getCheckKey();
            if (checkKey != null && checkKey.length == 2) {
                byte[] idByte = new byte[16];
                idByte[0] = (byte) 74;
                idByte[1] = (byte) 73;
                idByte[2] = (byte) 78;
                idByte[3] = (byte) 79;
                idByte[4] = (byte) 85;
                idByte[5] = checkKey[0];
                idByte[6] = checkKey[1];
                idByte[7] = (byte) 1;
                idByte[8] = (byte) 1;
                idByte[9] = id[0];
                idByte[10] = id[1];
                idByte[11] = id[2];
                idByte[12] = id[3];
                idByte[13] = id[4];
                idByte[14] = id[5];
                sendData(AesCipherAndInvCipher.EecryptToBytes(idByte, connectBDI.getUserKey()));
            }
        }
    }

    private void didDataReceived(byte[] data) {
        if (this.rePairData == null) {
            this.rePairData = data;
        } else if (this.rePairData.length > 500) {
            this.rePairData = data;
        } else {
            byte[] temp = this.rePairData;
            this.rePairData = new byte[(temp.length + data.length)];
            System.arraycopy(temp, 0, this.rePairData, 0, temp.length);
            System.arraycopy(data, 0, this.rePairData, temp.length, data.length);
        }
        if (this.rePairData.length == 16) {
            analysis(this.rePairData);
            this.rePairData = null;
        } else if (this.rePairData.length == 32) {
            analysis(this.rePairData);
            this.rePairData = null;
        }
    }

    private void analysis(byte[] data) {
        if (data != null) {
            byte[] datays = data;
            if (connectBDI != null) {
                data = AesCipherAndInvCipher.Decryptbyte(data, connectBDI.getUserKey());
            } else {
                data = AesCipherAndInvCipher.Decryptbyte(data, null);
            }
            Log.m2i("str====" + Tools.bytesToHexString(data));
            if (data[0] != (byte) 74 || data[1] != (byte) 73 || data[2] != (byte) 78 || data[3] != (byte) 79 || data[4] != (byte) 85) {
                data = AesCipherAndInvCipher.Decryptbyte(datays, null);
                if (data[7] == (byte) 7 && data[8] == (byte) 1) {
                    showReset();
                } else {
                    showFindLog();
                }
            } else if (data[7] == (byte) 7 && data[8] == (byte) 1) {
                showReset();
            } else {
                showFindLog();
            }
        }
    }

    private void sendData(byte[] data) {
        if (this.isConnected && mBluetoothLeService != null) {
            mBluetoothLeService.wirte(data);
        }
    }

    private void showFindLog() {
        try {
            if (manipulatenum == 1) {
                manipulatenum = 0;
                if (mBluetoothLeService == null) {
                    mBluetoothLeService = new BluetoothLeService();
                }
                this.deivcename_text2.setImageResource(C0048R.drawable.white);
                this.deivcePower_text2.setImageResource(C0048R.drawable.white);
                this.deivcestate_text2.setImageResource(C0048R.drawable.white);
                mBluetoothLeService.disconnects();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"InlinedApi"})
    private void init() {
        registerReceiver(this.mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DATARECEIVED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_READCHARACTERISTICERROR);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_READCHARACTERISTICSUCCESS);
        return intentFilter;
    }

    public void showAlertDialog(String content, String oneButton, String twoButton, int theme) {
        Builder builder = new Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new C00694());
        builder.setNegativeButton(twoButton, new C00705());
        builder.create().show();
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mGattUpdateReceiver);
        mBluetoothLeService = null;
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0048R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
