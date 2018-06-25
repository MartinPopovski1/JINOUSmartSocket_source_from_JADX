package com.jinoux.pwersupplysocket.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.jinoux.powersupplysocket.C0048R;
import com.android.jinoux.powersupplysocketlibrary.BluetoothLeService;
import com.android.jinoux.powersupplysocketlibrary.BluetoothLeService.LocalBinder;
import com.android.jinoux.util.AesCipherAndInvCipher;
import com.android.jinoux.util.Log;
import com.jinoux.pwersupplysocket.customdialog.CustomDialog;
import com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO;
import com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;
import com.jinoux.pwersupplysocket.modle.ScanDeviceInfo;
import com.jinoux.pwersupplysocket.service.BluetoothLeScanService;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AddSocketActivity extends Activity {
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int SCAN_PERIOD = 5000;
    public static BLEDeviceInfoDAO bLEDeviceInfoDAO;
    public static BluetoothLeScanService bluetoothLeScanService;
    public static Handler connectHnadler = new C00626();
    public static int height;
    public static BluetoothLeService mBluetoothLeService;
    public static AddSocketActivity ma;
    public static int nm = 0;
    private static Integer scanningnum = Integer.valueOf(0);
    public static int width;
    private AddLockMenLeftOnclick addLockmenLeftOnclick;
    private AddLockMenrigthOnclick addLockmenrigthOnclick;
    private AddLockSoftwareEditionOnclick addLocksoftwareEditionOnclick;
    private AddLockUseExplainOnclick addLockuseExplainOnclick;
    private LinearLayout addlocklins1;
    private ProgressBar addlocktop_pb_connecting;
    private BeginMatchingOnclick beginMatchingOnclick;
    private BLEDeviceInfo connectBDI;
    private Runnable connectTimeoutRunnable = new C00615();
    @SuppressLint({"HandlerLeak"})
    public Handler deviceHandler = new C00571();
    private HomepageOnclick homepageOnclick;
    private LayoutInflater inflater;
    private boolean isConnected = false;
    private int isPairing = 0;
    private LinearLayout linearbottom1;
    private LinearLayout lineartop1;
    BaseAdapter mBaseAdapters = new C00582();
    private final BroadcastReceiver mGattUpdateReceiver = new C00604();
    private final ServiceConnection mServiceConnection = new C00593();
    private ListView main_list;
    private ArrayList<View> mainlistItems = new ArrayList();
    private ScanDeviceInfo pairSDI;
    private PopupWindow popupwindow;
    public boolean ppbo = true;
    private byte[] rePairData;
    public List<ScanDeviceInfo> scanlist = new ArrayList();
    private ScanninglockOnclick scanninglockOnclick;
    private Button scanninglockbt;
    private boolean scanningzt = false;
    private Timer timer;
    private Button top_left_button;
    private ProgressBar top_right_ProgressBar;
    private Button top_right_bt;
    private LinearLayout top_right_button;
    private TextView top_text;
    private Button top_tx_button;
    private TimerTask tt;
    private byte[] userKey;
    private TextView ypdtv;

    class C00571 extends Handler {
        C00571() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (AddSocketActivity.this.scanningzt) {
                        ScanDeviceInfo sdi = msg.obj;
                        if (sdi.getManufacturerData() != null && sdi.getManufacturerData()[0] == (byte) 1) {
                            if (AddSocketActivity.scanningnum.intValue() == 0) {
                                AddSocketActivity.this.mainlistItems = new ArrayList();
                                AddSocketActivity.this.main_list.setAdapter(AddSocketActivity.this.mBaseAdapters);
                            }
                            LinearLayout deivcelist = (LinearLayout) AddSocketActivity.this.inflater.inflate(C0048R.layout.searchsingledevice, null);
                            TextView dname = (TextView) deivcelist.findViewById(C0048R.id.deivcename_text);
                            TextView ypd = (TextView) deivcelist.findViewById(C0048R.id.deivcepd_text);
                            boolean b = false;
                            List<BLEDeviceInfo> list = AddSocketActivity.bLEDeviceInfoDAO.queryAll();
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    BLEDeviceInfo bdi = (BLEDeviceInfo) list.get(i);
                                    if (bdi != null && bdi.getAddress().equals(sdi.getDevice().getAddress())) {
                                        sdi.setPaired(true);
                                        b = true;
                                    }
                                }
                            }
                            int dnamewidth = (AddSocketActivity.width / 5) * 3;
                            LayoutParams ypdip = ypd.getLayoutParams();
                            ypdip.width = (AddSocketActivity.width / 5) * 2;
                            ypdip.height = AddSocketActivity.height / 15;
                            ypd.setLayoutParams(ypdip);
                            if (!b) {
                                ypd.setText("");
                            }
                            LayoutParams dnameip = dname.getLayoutParams();
                            dnameip.width = dnamewidth;
                            dnameip.height = AddSocketActivity.height / 15;
                            dname.setLayoutParams(dnameip);
                            dname.setText(sdi.getDevice().getName());
                            deivcelist.setContentDescription(sdi.getDevice().getAddress());
                            deivcelist.setOnClickListener(AddSocketActivity.this.beginMatchingOnclick);
                            AddSocketActivity.scanningnum = Integer.valueOf(AddSocketActivity.scanningnum.intValue() + 1);
                            AddSocketActivity.this.scanlist.add(sdi);
                            AddSocketActivity.this.mainlistItems.add(deivcelist);
                            AddSocketActivity.this.main_list.setAdapter(AddSocketActivity.this.mBaseAdapters);
                            return;
                        }
                        return;
                    }
                    return;
                case 1:
                    if (AddSocketActivity.scanningnum.intValue() == 0) {
                        AddSocketActivity.this.scanningymxs(AddSocketActivity.this.getResources().getString(C0048R.string.findDeviceLabel2));
                    }
                    AddSocketActivity.this.scanningzt = false;
                    AddSocketActivity.this.ProgressBarzt(false);
                    AddSocketActivity.this.scanninglockbt.setText(AddSocketActivity.this.getResources().getString(C0048R.string.scanninglock));
                    return;
                default:
                    return;
            }
        }
    }

    class C00582 extends BaseAdapter {
        C00582() {
        }

        public int getCount() {
            return AddSocketActivity.this.mainlistItems.size();
        }

        public Object getItem(int i) {
            return AddSocketActivity.this.mainlistItems.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return (View) AddSocketActivity.this.mainlistItems.get(i);
        }
    }

    class C00593 implements ServiceConnection {
        C00593() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder service) {
            AddSocketActivity.mBluetoothLeService = ((LocalBinder) service).getService();
            if (AddSocketActivity.mBluetoothLeService.initialize()) {
                Log.m2i("initialize Bluetooth");
            } else {
                Log.m2i("Unable to initialize Bluetooth");
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            AddSocketActivity.mBluetoothLeService = null;
        }
    }

    class C00604 extends BroadcastReceiver {
        C00604() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.m2i("action = " + action);
            if (!BluetoothLeService.ACTION_GATT_CONNECTED.equals(action) && !BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action) && !BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action) && !BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                if (BluetoothLeService.ACTION_GATT_HANDLERDATAERROR.equals(action)) {
                    AddSocketActivity.this.isConnected = false;
                    AddSocketActivity.this.pairError(1);
                } else if (BluetoothLeService.ACTION_GATT_READCHARACTERISTICSUCCESS.equals(action)) {
                    AddSocketActivity.this.isConnected = true;
                    AddSocketActivity.this.sendId();
                } else if (BluetoothLeService.ACTION_GATT_READCHARACTERISTICERROR.equals(action)) {
                    AddSocketActivity.this.isConnected = false;
                    AddSocketActivity.this.pairError(0);
                } else if (BluetoothLeService.ACTION_GATT_DATARECEIVED.equals(action)) {
                    byte[] by = intent.getByteArrayExtra(BluetoothLeService.DATA_NAME);
                    if (AddSocketActivity.this.pairSDI != null) {
                        AddSocketActivity.this.didDataReceived(by);
                    } else {
                        AddSocketActivity.this.pairError(1);
                    }
                }
            }
        }
    }

    class C00615 implements Runnable {
        C00615() {
        }

        public void run() {
            if (AddSocketActivity.this.pairSDI != null) {
                Log.m2i("超时断开连接=");
                AddSocketActivity.this.pairError2(0);
            }
        }
    }

    class C00626 extends Handler {
        C00626() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AddSocketActivity.ma.getDate();
                    return;
                default:
                    return;
            }
        }
    }

    class C00648 implements OnClickListener {
        C00648() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C00659 implements OnClickListener {
        C00659() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class AddLockMenLeftOnclick implements View.OnClickListener {
        private AddLockMenLeftOnclick() {
        }

        public void onClick(View v) {
            if (!AddSocketActivity.this.ppbo) {
                new BluetoothLeService().disconnects();
            }
            AddSocketActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(AddSocketActivity.this.getApplication(), MainActivity.class);
            intent.putExtras(intent);
            AddSocketActivity.this.finish();
            AddSocketActivity.this.startActivity(intent);
        }
    }

    private class AddLockMenrigthOnclick implements View.OnClickListener {
        private AddLockMenrigthOnclick() {
        }

        public void onClick(View v) {
            if (AddSocketActivity.this.popupwindow != null) {
                AddSocketActivity.this.dismiss();
                return;
            }
            View view = AddSocketActivity.this.inflater.inflate(C0048R.layout.popuplinear, null);
            AddSocketActivity.this.getpnpmenu(view);
            AddSocketActivity.this.popupwindow = new PopupWindow(view, (AddSocketActivity.width / 10) * 2, AddSocketActivity.height / 5);
            AddSocketActivity.this.popupwindow.showAsDropDown(v);
            AddSocketActivity.this.popupwindow.showAtLocation(AddSocketActivity.this.top_right_bt, 17, 20, 20);
        }
    }

    private class AddLockSoftwareEditionOnclick implements View.OnClickListener {
        private AddLockSoftwareEditionOnclick() {
        }

        public void onClick(View v) {
            if (!AddSocketActivity.this.ppbo) {
                new BluetoothLeService().disconnects();
            }
            AddSocketActivity.this.ppbo = true;
            AddSocketActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(AddSocketActivity.this.getApplication(), EditionformationActivity.class);
            intent.putExtras(intent);
            AddSocketActivity.this.startActivity(intent);
        }
    }

    private class AddLockUseExplainOnclick implements View.OnClickListener {
        private AddLockUseExplainOnclick() {
        }

        public void onClick(View v) {
            if (!AddSocketActivity.this.ppbo) {
                new BluetoothLeService().disconnects();
            }
            AddSocketActivity.this.ppbo = true;
            AddSocketActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(AddSocketActivity.this.getApplication(), UseexplainActivity.class);
            intent.putExtras(intent);
            AddSocketActivity.this.startActivity(intent);
        }
    }

    private class BeginMatchingOnclick implements View.OnClickListener {
        private BeginMatchingOnclick() {
        }

        public void onClick(View v) {
            if (AddSocketActivity.this.ppbo) {
                AddSocketActivity.this.ppbo = false;
                if (AddSocketActivity.this.isPairing != 1) {
                    List<BLEDeviceInfo> list = AddSocketActivity.bLEDeviceInfoDAO.queryAll();
                    if (list == null || list.size() < 5) {
                        AddSocketActivity.bluetoothLeScanService.stopLeScanDevice();
                        AddSocketActivity.this.scanningzt = false;
                        AddSocketActivity.this.scanninglockbt.setText(AddSocketActivity.this.getResources().getString(C0048R.string.scanninglock));
                        LinearLayout lin = (LinearLayout) v;
                        String adrss = lin.getContentDescription().toString();
                        TextView ypd = (TextView) lin.findViewById(C0048R.id.deivcepd_text);
                        if (AddSocketActivity.this.scanlist.size() > 0) {
                            for (int i = 0; i < AddSocketActivity.this.scanlist.size(); i++) {
                                ScanDeviceInfo dqscan = (ScanDeviceInfo) AddSocketActivity.this.scanlist.get(i);
                                if (dqscan.getDevice().getAddress().equals(adrss)) {
                                    AddSocketActivity.this.isPairing = 1;
                                    AddSocketActivity.this.pairSDI = dqscan;
                                    break;
                                }
                            }
                        }
                        if (AddSocketActivity.this.isPairing == 1 && AddSocketActivity.this.pairSDI != null) {
                            AddSocketActivity.this.ypdtv = ypd;
                            ypd.setText(AddSocketActivity.this.getResources().getString(C0048R.string.inmatching));
                            AddSocketActivity.this.setConnectingPB(true);
                            AddSocketActivity.this.deviceHandler.removeCallbacks(AddSocketActivity.this.connectTimeoutRunnable);
                            AddSocketActivity.this.deviceHandler.postDelayed(AddSocketActivity.this.connectTimeoutRunnable, 10000);
                            AddSocketActivity.mBluetoothLeService.connect(adrss, 5);
                            return;
                        }
                        return;
                    }
                    AddSocketActivity.this.ppbo = true;
                    AddSocketActivity.this.showAlertDialog(AddSocketActivity.this.getResources().getString(C0048R.string.editAlertMaxDeviceMsg), AddSocketActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null, 0);
                }
            }
        }
    }

    private class HomepageOnclick implements View.OnClickListener {
        private HomepageOnclick() {
        }

        public void onClick(View v) {
            if (!AddSocketActivity.this.ppbo) {
                new BluetoothLeService().disconnects();
            }
            AddSocketActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(AddSocketActivity.this.getApplication(), MainActivity.class);
            intent.putExtras(intent);
            AddSocketActivity.this.finish();
            AddSocketActivity.this.startActivity(intent);
        }
    }

    private class ScanninglockOnclick implements View.OnClickListener {
        private ScanninglockOnclick() {
        }

        public void onClick(View v) {
            AddSocketActivity.this.dismiss();
            if (AddSocketActivity.this.scanningzt) {
                if (AddSocketActivity.scanningnum.intValue() == 0) {
                    AddSocketActivity.this.scanningymxs(AddSocketActivity.this.getResources().getString(C0048R.string.findDeviceLabel2));
                }
                AddSocketActivity.this.scanningzt = false;
                AddSocketActivity.this.ProgressBarzt(false);
                AddSocketActivity.this.scanninglockbt.setText(AddSocketActivity.this.getResources().getString(C0048R.string.scanninglock));
                AddSocketActivity.bluetoothLeScanService.stopLeScanDevice();
                return;
            }
            AddSocketActivity.scanningnum = Integer.valueOf(0);
            AddSocketActivity.this.scanningzt = true;
            AddSocketActivity.this.ProgressBarzt(true);
            AddSocketActivity.this.scanninglockbt.setText(AddSocketActivity.this.getResources().getString(C0048R.string.ceasescanning));
            AddSocketActivity.this.scanlist = new ArrayList();
            AddSocketActivity.this.mainlistItems = new ArrayList();
            AddSocketActivity.this.main_list.setAdapter(AddSocketActivity.this.mBaseAdapters);
            AddSocketActivity.this.scanningymxs(AddSocketActivity.this.getResources().getString(C0048R.string.findDeviceLabel));
            AddSocketActivity.bluetoothLeScanService.startScanLeDevice(true);
        }
    }

    class myTimerTask extends TimerTask {
        myTimerTask() {
        }

        public void run() {
            AddSocketActivity.connectHnadler.obtainMessage(0).sendToTarget();
            AddSocketActivity.this.tt = new myTimerTask();
            if (AddSocketActivity.nm <= 1) {
                AddSocketActivity.this.timer.schedule(AddSocketActivity.this.tt, 1000);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.activity_addsocket);
        this.inflater = LayoutInflater.from(this);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        this.addLockmenLeftOnclick = new AddLockMenLeftOnclick();
        this.addLockmenrigthOnclick = new AddLockMenrigthOnclick();
        this.homepageOnclick = new HomepageOnclick();
        this.addLockuseExplainOnclick = new AddLockUseExplainOnclick();
        this.addLocksoftwareEditionOnclick = new AddLockSoftwareEditionOnclick();
        this.scanninglockOnclick = new ScanninglockOnclick();
        this.beginMatchingOnclick = new BeginMatchingOnclick();
        bLEDeviceInfoDAO = new BLEDeviceInfoDAO(this);
        ma = this;
        this.ppbo = true;
        scanningnum = Integer.valueOf(0);
        this.addlocklins1 = (LinearLayout) findViewById(C0048R.id.addlocklins1);
        this.lineartop1 = (LinearLayout) findViewById(C0048R.id.addlocklineartop1);
        this.linearbottom1 = (LinearLayout) findViewById(C0048R.id.addlocklinearbottom1);
        this.top_left_button = (Button) findViewById(C0048R.id.addlocktop_left_button);
        this.top_right_button = (LinearLayout) findViewById(C0048R.id.addlocktop_right_button);
        this.top_right_ProgressBar = (ProgressBar) findViewById(C0048R.id.addlocktop_right_ProgressBar);
        this.addlocktop_pb_connecting = (ProgressBar) findViewById(C0048R.id.addlocktop_pb_connecting);
        this.top_right_bt = (Button) findViewById(C0048R.id.addlocktop_right_bt);
        this.top_tx_button = (Button) findViewById(C0048R.id.top_tx_button);
        this.top_text = (TextView) findViewById(C0048R.id.addlocktop_text);
        this.scanninglockbt = (Button) findViewById(C0048R.id.scanninglockbt);
        this.main_list = (ListView) findViewById(C0048R.id.addlockmain_list);
        this.mainlistItems = new ArrayList();
        szlayoutParams();
        init();
        bluetoothLeScanService = new BluetoothLeScanService(this);
        bluetoothLeScanService.init(this.deviceHandler, SCAN_PERIOD, 1);
        parametervalue();
    }

    public void szlayoutParams() {
        LayoutParams relatlins1ip = this.addlocklins1.getLayoutParams();
        relatlins1ip.width = width;
        relatlins1ip.height = height;
        this.addlocklins1.setLayoutParams(relatlins1ip);
        LayoutParams lineartop1ip = this.lineartop1.getLayoutParams();
        lineartop1ip.width = width;
        lineartop1ip.height = height / 10;
        this.lineartop1.setLayoutParams(lineartop1ip);
        LayoutParams linearbottom1ip = this.linearbottom1.getLayoutParams();
        linearbottom1ip.width = width;
        linearbottom1ip.height = (height / 100) * 90;
        this.linearbottom1.setLayoutParams(linearbottom1ip);
        LayoutParams top_left_buttonip = this.top_left_button.getLayoutParams();
        top_left_buttonip.width = (width / 10) * 2;
        top_left_buttonip.height = height / 10;
        this.top_left_button.setLayoutParams(top_left_buttonip);
        this.top_left_button.setOnClickListener(this.addLockmenLeftOnclick);
        LayoutParams top_tx_buttonip = this.top_tx_button.getLayoutParams();
        top_tx_buttonip.width = width / 10;
        top_tx_buttonip.height = height / 10;
        this.top_tx_button.setLayoutParams(top_tx_buttonip);
        LayoutParams top_right_buttonip = this.top_right_button.getLayoutParams();
        top_right_buttonip.width = (width / 10) * 3;
        top_right_buttonip.height = height / 10;
        this.top_right_button.setLayoutParams(top_right_buttonip);
        LayoutParams top_right_ProgressBarip = this.top_right_ProgressBar.getLayoutParams();
        top_right_ProgressBarip.width = width / 15;
        top_right_ProgressBarip.height = height / 25;
        this.top_right_ProgressBar.setLayoutParams(top_right_ProgressBarip);
        this.addlocktop_pb_connecting.setLayoutParams(top_right_ProgressBarip);
        LayoutParams top_right_btip = this.top_right_bt.getLayoutParams();
        top_right_btip.width = (width / 10) * 2;
        top_right_btip.height = height / 10;
        this.top_right_bt.setLayoutParams(top_right_btip);
        this.top_right_bt.setOnClickListener(this.addLockmenrigthOnclick);
        LayoutParams top_textip = this.top_text.getLayoutParams();
        top_textip.width = (width / 10) * 4;
        top_textip.height = height / 15;
        this.top_text.setLayoutParams(top_textip);
        LayoutParams main_listip = this.main_list.getLayoutParams();
        main_listip.width = width;
        main_listip.height = (height / 100) * 84;
        this.main_list.setLayoutParams(main_listip);
        LayoutParams scanninglockbtip = this.scanninglockbt.getLayoutParams();
        scanninglockbtip.width = width / 2;
        scanninglockbtip.height = (height / 100) * 5;
        this.scanninglockbt.setLayoutParams(scanninglockbtip);
        this.scanninglockbt.setOnClickListener(this.scanninglockOnclick);
        this.main_list.setAdapter(this.mBaseAdapters);
        scanningymxs(getResources().getString(C0048R.string.findDeviceLabel2));
    }

    public void parametervalue() {
        bluetoothLeScanService.stopLeScanDevice();
        scanningnum = Integer.valueOf(0);
        this.scanningzt = true;
        ProgressBarzt(true);
        this.scanninglockbt.setText(getResources().getString(C0048R.string.ceasescanning));
        this.scanlist = new ArrayList();
        this.mainlistItems = new ArrayList();
        this.main_list.setAdapter(this.mBaseAdapters);
        scanningymxs(getResources().getString(C0048R.string.findDeviceLabel));
        bluetoothLeScanService.startScanLeDevice(true);
    }

    public void scanningymxs(String str) {
        this.mainlistItems = new ArrayList();
        this.main_list.setAdapter(this.mBaseAdapters);
        LinearLayout deivcelist = (LinearLayout) this.inflater.inflate(C0048R.layout.searchdeviceshow, null);
        TextView dname = (TextView) deivcelist.findViewById(C0048R.id.searchstate_text);
        LayoutParams dnameip = dname.getLayoutParams();
        dnameip.width = width;
        dnameip.height = height / 4;
        dname.setLayoutParams(dnameip);
        dname.setText(str);
        this.mainlistItems.add(deivcelist);
        this.main_list.setAdapter(this.mBaseAdapters);
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
        bt1.setText(getResources().getString(C0048R.string.returns));
        bt1.setOnClickListener(this.homepageOnclick);
        bt2.setOnClickListener(this.addLockuseExplainOnclick);
        bt3.setOnClickListener(this.addLocksoftwareEditionOnclick);
    }

    public void dismiss() {
        if (this.popupwindow != null) {
            this.popupwindow.dismiss();
            this.popupwindow = null;
        }
    }

    @SuppressLint({"InlinedApi"})
    private void init() {
        if (bindService(new Intent(this, BluetoothLeService.class), this.mServiceConnection, 1)) {
            Log.m2i("绑定服务gattServiceIntent成功");
        } else {
            Log.m2i("绑定服务gattServiceIntent失败");
        }
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

    public void setpasswordbuilder() {
        Builder builder = new Builder(this);
        builder.setTitle(getResources().getString(C0048R.string.setpassword1));
        final LinearLayout lin = (LinearLayout) this.inflater.inflate(C0048R.layout.setpassword_addsocket, null);
        builder.setView(lin);
        builder.setPositiveButton(getResources().getString(C0048R.string.alertOneButtonTitle), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (AddSocketActivity.this.connectBDI != null) {
                    String pwd = ((EditText) lin.findViewById(C0048R.id.setpassword1_et)).getText().toString();
                    if (!pwd.equals("")) {
                        AddSocketActivity.this.connectBDI.setPassword(pwd);
                        AddSocketActivity.bLEDeviceInfoDAO.updateByAddress(AddSocketActivity.this.connectBDI);
                        EditnewSocketActivity.connectBDI = AddSocketActivity.this.connectBDI;
                        Intent intent = new Intent();
                        intent.setClass(AddSocketActivity.this.getApplication(), EditnewSocketActivity.class);
                        intent.putExtras(intent);
                        AddSocketActivity.this.finish();
                        AddSocketActivity.this.startActivity(intent);
                    }
                }
            }
        });
        builder.create().show();
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
            analysis(AesCipherAndInvCipher.Decryptbyte(this.rePairData, null));
            this.rePairData = null;
        } else if (this.rePairData.length == 32) {
            analysis(AesCipherAndInvCipher.Decryptbyte(this.rePairData, null));
            this.rePairData = null;
        }
    }

    private void analysis(byte[] data) {
        if (data[0] != (byte) 74 || data[1] != (byte) 73 || data[2] != (byte) 78 || data[3] != (byte) 79 || data[4] != (byte) 85) {
            return;
        }
        if (data[7] == (byte) 3 && data[8] == (byte) 16) {
            byte[] user_key = new byte[16];
            for (int i = 0; i < 16; i++) {
                user_key[i] = data[i + 9];
            }
            byte[] dataByte = AesCipherAndInvCipher.EecryptToBytes("verirfy passwod!".getBytes(), user_key);
            byte[] d = new byte[32];
            d[0] = (byte) 74;
            d[1] = (byte) 73;
            d[2] = (byte) 78;
            d[3] = (byte) 79;
            d[4] = (byte) 85;
            d[7] = (byte) 4;
            d[8] = (byte) 16;
            d[9] = dataByte[0];
            d[10] = dataByte[1];
            d[11] = dataByte[2];
            d[12] = dataByte[3];
            d[13] = dataByte[4];
            d[14] = dataByte[5];
            d[15] = dataByte[6];
            d[16] = dataByte[7];
            d[17] = dataByte[8];
            d[18] = dataByte[9];
            d[19] = dataByte[10];
            d[20] = dataByte[11];
            d[21] = dataByte[12];
            d[22] = dataByte[13];
            d[23] = dataByte[14];
            d[24] = dataByte[15];
            this.userKey = user_key;
            sendData(AesCipherAndInvCipher.EecryptToBytes(d, null));
        } else if (data[7] == (byte) 5 && data[8] == (byte) 1) {
            this.ppbo = true;
            if (data[9] == (byte) 0) {
                byte[] checkKey = new byte[]{data[5], data[6]};
                if (this.pairSDI != null) {
                    bLEDeviceInfoDAO.deleteByAddress(this.pairSDI.getDevice().getAddress());
                }
                BLEDeviceInfo bdi = new BLEDeviceInfo(0, this.pairSDI.getDevice().getName(), this.pairSDI.getDevice().getName(), "", this.pairSDI.getDevice().getAddress(), this.userKey, checkKey, "", "0", "100", "0");
                this.connectBDI = bdi;
                bLEDeviceInfoDAO.insert(bdi);
                resetConnectState();
                EditnewSocketActivity.connectBDI = this.connectBDI;
                Intent intent = new Intent();
                intent.setClass(getApplication(), EditnewSocketActivity.class);
                intent.putExtras(intent);
                finish();
                startActivity(intent);
            } else if (data[9] == (byte) 1) {
                pairError(1);
            }
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    public void getDate() {
        if (nm == 1) {
            new BluetoothLeService().disconnects();
            if (this.tt != null) {
                this.tt.cancel();
            }
            this.tt = null;
            nm++;
            return;
        }
        nm++;
    }

    private void resetConnectState() {
        this.pairSDI = null;
        this.isPairing = 0;
        setConnectingPB(false);
        nm = 0;
        this.timer = new Timer();
        this.tt = new myTimerTask();
        this.timer.schedule(this.tt, 1000);
    }

    private void pairError2(int type) {
        if (this.ypdtv != null) {
            if (this.pairSDI == null) {
                this.ypdtv.setText("");
            } else if (this.pairSDI.isPaired()) {
                this.ypdtv.setText(getResources().getString(C0048R.string.pairedText));
            } else {
                this.ypdtv.setText("");
            }
        }
        this.pairSDI = null;
        this.isPairing = 0;
        this.ppbo = true;
        setConnectingPB(false);
        nm = 0;
        this.timer = new Timer();
        this.tt = new myTimerTask();
        this.timer.schedule(this.tt, 1000);
    }

    private void pairError(int type) {
        if (this.ypdtv != null) {
            if (this.pairSDI == null) {
                this.ypdtv.setText("");
            } else if (this.pairSDI.isPaired()) {
                this.ypdtv.setText(getResources().getString(C0048R.string.pairedText));
            } else {
                this.ypdtv.setText("");
            }
        }
        this.pairSDI = null;
        this.isPairing = 0;
        this.ppbo = true;
        setConnectingPB(false);
        String msg = "";
        switch (type) {
            case 0:
                msg = getResources().getString(C0048R.string.mainAlertConnectErrorMsg);
                break;
            case 1:
                msg = getResources().getString(C0048R.string.findDeviceAlertConnectErrorMsg);
                break;
        }
        showAlertDialog(msg, getResources().getString(C0048R.string.alertOneButtonTitle), null, 0);
        nm = 0;
        this.timer = new Timer();
        this.tt = new myTimerTask();
        this.timer.schedule(this.tt, 1000);
    }

    private void sendId() {
        byte[] id = BluetoothLeService.myId;
        if (id == null || id.length != 6) {
            Log.m2i("Not found bluetooth address!");
            id = new byte[]{(byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1};
        }
        byte[] idByte = new byte[16];
        idByte[0] = (byte) 74;
        idByte[1] = (byte) 73;
        idByte[2] = (byte) 78;
        idByte[3] = (byte) 79;
        idByte[4] = (byte) 85;
        idByte[7] = (byte) 6;
        idByte[8] = (byte) 6;
        idByte[9] = id[0];
        idByte[10] = id[1];
        idByte[11] = id[2];
        idByte[12] = id[3];
        idByte[13] = id[4];
        idByte[14] = id[5];
        sendData(AesCipherAndInvCipher.EecryptToBytes(idByte, null));
    }

    private void sendData(byte[] data) {
        if (this.isConnected && mBluetoothLeService != null) {
            mBluetoothLeService.wirte(data);
        }
    }

    public void ProgressBarzt(boolean bo) {
        if (bo) {
            this.addlocktop_pb_connecting.setVisibility(8);
            this.top_right_ProgressBar.setVisibility(0);
            return;
        }
        this.top_right_ProgressBar.setVisibility(8);
    }

    public void setConnectingPB(boolean bo) {
        if (bo) {
            this.top_right_ProgressBar.setVisibility(8);
            this.addlocktop_pb_connecting.setVisibility(0);
            return;
        }
        this.addlocktop_pb_connecting.setVisibility(8);
    }

    public void showAlertDialog(String content, String oneButton, String twoButton, int theme) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new C00648());
        builder.setNegativeButton(twoButton, new C00659());
        builder.create().show();
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mGattUpdateReceiver);
        unbindService(this.mServiceConnection);
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
