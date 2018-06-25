package com.jinoux.pwersupplysocket.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import com.android.jinoux.powersupplysocket.C0048R;
import com.android.jinoux.powersupplysocketlibrary.BluetoothLeService;
import com.android.jinoux.powersupplysocketlibrary.BluetoothLeService.LocalBinder;
import com.android.jinoux.util.AesCipherAndInvCipher;
import com.android.jinoux.util.Log;
import com.android.jinoux.util.Tools;
import com.jinoux.pwersupplysocket.customdialog.CustomDialog.Builder;
import com.jinoux.pwersupplysocket.dao.BLEDeviceInfoDAO;
import com.jinoux.pwersupplysocket.modle.BLEDeviceInfo;
import com.jinoux.pwersupplysocket.modle.ScanDeviceInfo;
import com.jinoux.pwersupplysocket.modle.TimingOpenOrShut;
import com.jinoux.pwersupplysocket.service.BluetoothLeScanService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"NewApi", "SimpleDateFormat"})
public class MainActivity extends Activity {
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int SCAN_PERIOD = 5000;
    public static BLEDeviceInfoDAO bLEDeviceInfoDAO;
    public static BluetoothLeScanService bluetoothLeScanService;
    public static boolean connectzt = false;
    public static int height;
    public static boolean isCanClick = true;
    public static boolean isCmdLockState = false;
    public static BluetoothLeService mBluetoothLeService;
    private static MainActivity ma;
    public static int nm = 0;
    public static boolean notWantBLE = false;
    public static boolean openorclose = false;
    private static int statenum = 0;
    public static List<TextView> textViewList = new ArrayList();
    public static int width;
    private int action;
    private AddLockOnclick addLockOnclick;
    private List<BLEDeviceInfo> allDevices;
    private BLEDeviceInfo connectBDI;
    public Handler connectHnadler = new C00846();
    private Runnable connectTimeoutRunnable = new C00802();
    private TextView deivcePower_text1;
    private ImageView deivcePower_text2;
    private LinearLayout deivceinforlin1;
    private LinearLayout deivceinforlin10;
    private LinearLayout deivceinforlin11;
    private LinearLayout deivceinforlin12;
    private LinearLayout deivceinforlin2;
    private LinearLayout deivceinforlin3;
    private LinearLayout deivceinforlin4;
    private LinearLayout deivceinforlin5;
    private LinearLayout deivceinforlin7;
    private LinearLayout deivceinforlin8;
    private LinearLayout deivceinforlin9;
    private LinearLayout deivceinforlinz;
    private TextView deivcename_text1;
    private ImageView deivcename_text2;
    private TextView deivcestate_text1;
    private ImageView deivcestate_text2;
    private TextView deivcetime_text1;
    @SuppressLint({"HandlerLeak"})
    public Handler deviceHandler = new C00791();
    private DeviceInformationOnclick deviceInformationOnclick;
    private ImageView images;
    private LayoutInflater inflater;
    private boolean isConnected = false;
    private boolean isStopScan = false;
    private LinearLayout linearbottom1;
    private LinearLayout lineartop1;
    BaseAdapter mBaseAdapters = new C00835();
    private final BroadcastReceiver mGattUpdateReceiver = new C00824();
    private OpenorCloseSocketOnclick mOpenorCloseSocketOnclick;
    private QueryTimingSocketOnclick mQueryTimingSocketOnclick;
    private final ServiceConnection mServiceConnection = new C00813();
    private TimingSocketOnclick mTimingSocketOnclick;
    private TimingTimeSetOnclick mTimingTimeSetOnclick;
    private ListView mainList;
    private TextView mainText;
    private ArrayList<View> mainlistItems = new ArrayList();
    private MenLeftOnclick menLeftOnclick;
    private MenrigthOnclick menrigthOnclick;
    private int pageshowstate = 0;
    private PopupWindow popupwindow;
    private byte[] rePairData;
    private LinearLayout relatlins1;
    private Runnable scanRunnable = new C00857();
    private List<ScanDeviceInfo> sdiList = new ArrayList();
    private Button settimesbt;
    private TextView setworktimes_textg1;
    private TextView setworktimes_textg1s;
    private TextView setworktimes_textg2;
    private TextView setworktimes_textg2s;
    private TextView setworktimes_textg3;
    private TextView setworktimes_textg3s;
    private TextView setworktimes_textk1;
    private TextView setworktimes_textk1s;
    private TextView setworktimes_textk2;
    private TextView setworktimes_textk2s;
    private TextView setworktimes_textk3;
    private TextView setworktimes_textk3s;
    private Button setworktimesbt1;
    private int socketid = 1;
    private SoftwareEditionOnclick softwareEditionOnclick;
    private List<ScanDeviceInfo> tempSdiList = new ArrayList();
    private TimePicker timePicker;
    private Timer timer;
    public List<TimingOpenOrShut> timinglist = new ArrayList();
    public List<byte[]> timinglistx = new ArrayList();
    public int timingnum = 0;
    private Button top_left_button;
    private ProgressBar top_pb_connecting;
    private ProgressBar top_right_ProgressBar;
    private Button top_right_bt;
    private LinearLayout top_right_button;
    private TextView top_text;
    private Button top_tx_button;
    private TimerTask tt;
    private UseExplainOnclick useExplainOnclick;

    class C00791 extends Handler {
        C00791() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0) {
                switch (msg.what) {
                    case 0:
                        MainActivity.this.sdiList.add(msg.obj);
                        MainActivity.this.refreshListView();
                        return;
                    case 1:
                        MainActivity.this.searchbar(false);
                        return;
                    case 2:
                        MainActivity.this.searchbar(false);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    class C00802 implements Runnable {
        C00802() {
        }

        public void run() {
            if (MainActivity.this.connectBDI != null) {
                if (MainActivity.mBluetoothLeService == null) {
                    MainActivity.mBluetoothLeService = new BluetoothLeService();
                }
                if (MainActivity.this.isConnected) {
                    MainActivity.this.isConnected = false;
                    MainActivity.this.showConnectOrHandlerError(2);
                }
                MainActivity.this.resetConnectState();
            }
        }
    }

    class C00813 implements ServiceConnection {
        C00813() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MainActivity.mBluetoothLeService = ((LocalBinder) service).getService();
            if (MainActivity.mBluetoothLeService.initialize()) {
                Log.m2i("initialize Bluetooth");
            } else {
                Log.m2i("Unable to initialize Bluetooth");
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainActivity.mBluetoothLeService = null;
        }
    }

    class C00824 extends BroadcastReceiver {
        C00824() {
        }

        public void onReceive(Context context, Intent intent) {
            String straction = intent.getAction();
            if (!BluetoothLeService.ACTION_GATT_CONNECTED.equals(straction) && !BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(straction) && !BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(straction) && !BluetoothLeService.ACTION_DATA_AVAILABLE.equals(straction)) {
                if (BluetoothLeService.ACTION_GATT_HANDLERDATAERROR.equals(straction)) {
                    MainActivity.this.isConnected = false;
                    if (MainActivity.this.pageshowstate == 0) {
                        MainActivity.this.showConnectOrHandlerError(2);
                    } else {
                        MainActivity.this.showConnectOrHandlerError(MainActivity.this.action);
                    }
                    MainActivity.this.resetConnectState();
                } else if (BluetoothLeService.ACTION_GATT_READCHARACTERISTICSUCCESS.equals(straction)) {
                    MainActivity.this.isConnected = true;
                    MainActivity.this.sendId();
                } else if (BluetoothLeService.ACTION_GATT_READCHARACTERISTICERROR.equals(straction)) {
                    MainActivity.this.isConnected = false;
                    MainActivity.this.showConnectOrHandlerError(2);
                    MainActivity.this.resetConnectState();
                } else if (BluetoothLeService.ACTION_GATT_DATARECEIVED.equals(straction)) {
                    MainActivity.this.didDataReceived(intent.getByteArrayExtra(BluetoothLeService.DATA_NAME));
                }
            }
        }
    }

    class C00835 extends BaseAdapter {
        C00835() {
        }

        public int getCount() {
            return MainActivity.this.mainlistItems.size();
        }

        public Object getItem(int i) {
            return MainActivity.this.mainlistItems.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return (View) MainActivity.this.mainlistItems.get(i);
        }
    }

    class C00846 extends Handler {
        C00846() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    MainActivity.ma.getDate();
                    return;
                case 1:
                    new BluetoothLeService().disconnects();
                    MainActivity.this.setConnectingPB(false);
                    MainActivity.isCanClick = true;
                    MainActivity.this.connectBDI = null;
                    MainActivity.isCmdLockState = false;
                    MainActivity.connectzt = false;
                    MainActivity.this.pageshowstate = 0;
                    return;
                default:
                    return;
            }
        }
    }

    class C00857 implements Runnable {
        C00857() {
        }

        public void run() {
            if (MainActivity.this.pageshowstate == 0) {
                if (MainActivity.this.connectBDI != null) {
                    MainActivity.this.searchbar(false);
                } else if (MainActivity.this.allDevices == null || MainActivity.this.allDevices.size() <= 0) {
                    MainActivity.this.searchbar(false);
                } else if (!MainActivity.this.isStopScan && BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    MainActivity.this.searchbar(true);
                    MainActivity.this.tempSdiList = MainActivity.this.sdiList;
                    MainActivity.this.sdiList = new ArrayList();
                    MainActivity.bluetoothLeScanService.startScanLeDevice(true);
                }
                MainActivity.this.deviceHandler.postDelayed(MainActivity.this.scanRunnable, 12000);
            }
        }
    }

    private class AddLockOnclick implements OnClickListener {
        private AddLockOnclick() {
        }

        public void onClick(View v) {
            MainActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this.getApplication(), AddSocketActivity.class);
            intent.putExtras(intent);
            MainActivity.this.finish();
            MainActivity.this.startActivity(intent);
        }
    }

    private class DeviceInformationOnclick implements OnClickListener {
        private DeviceInformationOnclick() {
        }

        public void onClick(View v) {
            if (!MainActivity.connectzt) {
                Integer nm = Integer.valueOf(Integer.parseInt(((TextView) v).getContentDescription().toString()));
                if (MainActivity.this.allDevices != null) {
                    EditSocketActivity.connectBDI = (BLEDeviceInfo) MainActivity.this.allDevices.get(nm.intValue());
                }
                MainActivity.bluetoothLeScanService.stopLeScanDevice();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this.getApplication(), EditSocketActivity.class);
                intent.putExtras(intent);
                MainActivity.this.finish();
                MainActivity.this.startActivity(intent);
            }
        }
    }

    private class MenLeftOnclick implements OnClickListener {
        private MenLeftOnclick() {
        }

        public void onClick(View v) {
            MainActivity.this.showAlertDialog(MainActivity.this.getResources().getString(C0048R.string.mainAlertExitMsg), MainActivity.this.getResources().getString(C0048R.string.alertTwoButtonTitle1), MainActivity.this.getResources().getString(C0048R.string.alertTwoButtonTitle2), 1, 0, Integer.valueOf(0));
        }
    }

    private class MenrigthOnclick implements OnClickListener {
        private MenrigthOnclick() {
        }

        public void onClick(View v) {
            if (MainActivity.this.popupwindow != null) {
                MainActivity.this.dismiss();
                return;
            }
            View view = MainActivity.this.inflater.inflate(C0048R.layout.popuplinear, null);
            MainActivity.this.getpnpmenu(view);
            MainActivity.this.popupwindow = new PopupWindow(view, (MainActivity.width / 10) * 2, MainActivity.height / 5);
            MainActivity.this.popupwindow.showAsDropDown(v);
            MainActivity.this.popupwindow.showAtLocation(MainActivity.this.top_right_bt, 17, 20, 20);
        }
    }

    public class MyTimePickerDialog implements OnTimeSetListener {
        public void onTimeSet(TimePicker mTimePicker, int hour, int minute) {
            if (MainActivity.this.timePicker != null) {
                MainActivity.this.timePicker.setCurrentHour(Integer.valueOf(hour));
                MainActivity.this.timePicker.setCurrentMinute(Integer.valueOf(minute));
            }
        }
    }

    private class OpenorCloseSocketOnclick implements OnClickListener {
        private OpenorCloseSocketOnclick() {
        }

        public void onClick(View v) {
            if (!MainActivity.openorclose) {
                MainActivity.openorclose = true;
                ImageView imgv = (ImageView) v;
                String numstr = imgv.getContentDescription().toString();
                MainActivity.this.images = imgv;
                if (numstr.equals("1")) {
                    MainActivity.this.socketid = 1;
                } else if (numstr.equals("2")) {
                    MainActivity.this.socketid = 2;
                } else if (numstr.equals("3")) {
                    MainActivity.this.socketid = 4;
                }
                MainActivity.this.sendDataHandle();
            }
        }
    }

    private class QueryTimingSocketOnclick implements OnClickListener {
        private QueryTimingSocketOnclick() {
        }

        public void onClick(View v) {
            if (!MainActivity.openorclose) {
                MainActivity.openorclose = true;
                MainActivity.this.timinglist = new ArrayList();
                MainActivity.this.sendWorkTimeSetData();
            }
        }
    }

    private class SoftwareEditionOnclick implements OnClickListener {
        private SoftwareEditionOnclick() {
        }

        public void onClick(View v) {
            MainActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this.getApplication(), EditionformationActivity.class);
            intent.putExtras(intent);
            MainActivity.this.startActivity(intent);
        }
    }

    private class TimingSocketOnclick implements OnClickListener {
        private TimingSocketOnclick() {
        }

        public void onClick(View v) {
            final TextView text = (TextView) v;
            MainActivity.this.timePicker = new TimePicker(MainActivity.this);
            new TimePickerDialog(MainActivity.this, new MyTimePickerDialog(), MainActivity.this.timePicker.getCurrentHour().intValue(), MainActivity.this.timePicker.getCurrentMinute().intValue(), true).show();
            MainActivity.this.timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
                public void onTimeChanged(TimePicker arg0, int hour, int minute) {
                    String str = "";
                    if (hour < 10) {
                        str = "0" + hour;
                    } else {
                        str = new StringBuilder(String.valueOf(hour)).toString();
                    }
                    if (minute < 10) {
                        str = new StringBuilder(String.valueOf(str)).append(":0").append(minute).toString();
                    } else {
                        str = new StringBuilder(String.valueOf(str)).append(":").append(minute).toString();
                    }
                    text.setText(str);
                }
            });
        }
    }

    private class TimingTimeSetOnclick implements OnClickListener {
        private TimingTimeSetOnclick() {
        }

        public void onClick(View v) {
            if (!MainActivity.openorclose) {
                int more;
                MainActivity.openorclose = true;
                String timingk1 = (String) MainActivity.this.setworktimes_textk1s.getText();
                String timingg1 = (String) MainActivity.this.setworktimes_textg1s.getText();
                String timingk2 = (String) MainActivity.this.setworktimes_textk2s.getText();
                String timingg2 = (String) MainActivity.this.setworktimes_textg2s.getText();
                String timingk3 = (String) MainActivity.this.setworktimes_textk3s.getText();
                String timingg3 = (String) MainActivity.this.setworktimes_textg3s.getText();
                MainActivity.this.timinglistx = new ArrayList();
                boolean bo1 = MainActivity.this.timingtimebo(0, timingk1, timingg1);
                boolean bo2 = MainActivity.this.timingtimebo(1, timingk2, timingg2);
                boolean bo3 = MainActivity.this.timingtimebo(2, timingk3, timingg3);
                if (bo1) {
                    more = 0;
                    if (bo2 || bo3) {
                        more = 1;
                    }
                    MainActivity.this.timinglistx.add(MainActivity.this.timingtimebytes(0, timingk1, timingg1, more));
                }
                if (bo2) {
                    more = 0;
                    if (bo3) {
                        more = 1;
                    }
                    MainActivity.this.timinglistx.add(MainActivity.this.timingtimebytes(1, timingk2, timingg2, more));
                }
                if (bo3) {
                    MainActivity.this.timinglistx.add(MainActivity.this.timingtimebytes(2, timingk3, timingg3, 0));
                }
                if (MainActivity.this.timinglistx.size() > 0) {
                    MainActivity.this.timingnum = 1;
                    MainActivity.this.sendWorkTimeData((byte[]) MainActivity.this.timinglistx.get(0));
                    if (MainActivity.this.timinglistx.size() > 1) {
                        MainActivity.nm = 0;
                        MainActivity.this.timer = new Timer();
                        MainActivity.this.tt = new myTimerTask();
                        MainActivity.this.timer.schedule(MainActivity.this.tt, 500);
                        return;
                    }
                    return;
                }
                MainActivity.openorclose = false;
                MainActivity.this.foundBuilder(MainActivity.this.getResources().getString(C0048R.string.app_name), MainActivity.this.getResources().getString(C0048R.string.settimingwin), MainActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null);
            }
        }
    }

    private class UseExplainOnclick implements OnClickListener {
        private UseExplainOnclick() {
        }

        public void onClick(View v) {
            MainActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this.getApplication(), UseexplainActivity.class);
            intent.putExtras(intent);
            MainActivity.this.startActivity(intent);
        }
    }

    public class myTimerTask extends TimerTask {
        public void run() {
            MainActivity.this.connectHnadler.obtainMessage(0).sendToTarget();
            MainActivity.this.tt = new myTimerTask();
            if (MainActivity.nm == 0) {
                MainActivity.this.timer.schedule(MainActivity.this.tt, 500);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.activity_main);
        this.inflater = LayoutInflater.from(this);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Log.m2i("宽：" + width + "  高：" + height);
        ma = this;
        this.menLeftOnclick = new MenLeftOnclick();
        this.menrigthOnclick = new MenrigthOnclick();
        this.addLockOnclick = new AddLockOnclick();
        this.useExplainOnclick = new UseExplainOnclick();
        this.softwareEditionOnclick = new SoftwareEditionOnclick();
        this.deviceInformationOnclick = new DeviceInformationOnclick();
        this.mOpenorCloseSocketOnclick = new OpenorCloseSocketOnclick();
        this.mQueryTimingSocketOnclick = new QueryTimingSocketOnclick();
        this.mTimingTimeSetOnclick = new TimingTimeSetOnclick();
        this.mTimingSocketOnclick = new TimingSocketOnclick();
        bLEDeviceInfoDAO = new BLEDeviceInfoDAO(this);
        this.relatlins1 = (LinearLayout) findViewById(C0048R.id.relatlins1);
        this.lineartop1 = (LinearLayout) findViewById(C0048R.id.lineartop1);
        this.linearbottom1 = (LinearLayout) findViewById(C0048R.id.linearbottom1);
        this.top_left_button = (Button) findViewById(C0048R.id.top_left_button);
        this.top_right_button = (LinearLayout) findViewById(C0048R.id.top_right_button);
        this.top_right_ProgressBar = (ProgressBar) findViewById(C0048R.id.top_right_ProgressBar);
        this.top_pb_connecting = (ProgressBar) findViewById(C0048R.id.top_pb_connecting);
        this.top_right_bt = (Button) findViewById(C0048R.id.top_right_bt);
        this.top_tx_button = (Button) findViewById(C0048R.id.top_tx_button);
        this.top_text = (TextView) findViewById(C0048R.id.top_text);
        this.mainText = (TextView) findViewById(C0048R.id.main_text);
        this.mainList = (ListView) findViewById(C0048R.id.main_list);
        this.mainlistItems = new ArrayList();
        szlayoutParams();
        init();
        bluetoothLeScanService = new BluetoothLeScanService(this);
        bluetoothLeScanService.init(this.deviceHandler, SCAN_PERIOD, 1);
        this.pageshowstate = 0;
        getBLEDeviceInfo();
    }

    public void szlayoutParams() {
        LayoutParams relatlins1ip = this.relatlins1.getLayoutParams();
        relatlins1ip.width = width;
        relatlins1ip.height = height;
        this.relatlins1.setLayoutParams(relatlins1ip);
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
        this.top_left_button.setOnClickListener(this.menLeftOnclick);
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
        this.top_pb_connecting.setLayoutParams(top_right_ProgressBarip);
        LayoutParams top_right_btip = this.top_right_bt.getLayoutParams();
        top_right_btip.width = (width / 10) * 2;
        top_right_btip.height = height / 10;
        this.top_right_bt.setLayoutParams(top_right_btip);
        this.top_right_bt.setOnClickListener(this.menrigthOnclick);
        LayoutParams top_textip = this.top_text.getLayoutParams();
        top_textip.width = (width / 10) * 4;
        top_textip.height = height / 15;
        this.top_text.setLayoutParams(top_textip);
        LayoutParams mainTextip = this.mainText.getLayoutParams();
        mainTextip.width = width;
        mainTextip.height = (height / 100) * 89;
        this.mainText.setLayoutParams(mainTextip);
        LayoutParams main_listip = this.mainList.getLayoutParams();
        main_listip.width = width;
        main_listip.height = (height / 100) * 89;
        this.mainList.setLayoutParams(main_listip);
        this.mainList.setAdapter(this.mBaseAdapters);
    }

    public void getBLEDeviceInfo() {
        List<BLEDeviceInfo> list = bLEDeviceInfoDAO.queryAll();
        if (list == null || list.size() <= 0) {
            this.mainList.setVisibility(8);
            this.mainText.setVisibility(0);
            return;
        }
        this.allDevices = list;
        this.mainlistItems = new ArrayList();
        this.mainList.setAdapter(this.mBaseAdapters);
        this.mainText.setVisibility(8);
        this.mainList.setVisibility(0);
        for (int i = 0; i < list.size(); i++) {
            setView((BLEDeviceInfo) list.get(i), i);
        }
    }

    public void setView(BLEDeviceInfo bdi, int i) {
        LinearLayout mlin = (LinearLayout) this.inflater.inflate(C0048R.layout.main_listitem_device, null);
        LinearLayout lin = (LinearLayout) mlin.findViewById(C0048R.id.main_device_lin);
        TextView tv = (TextView) mlin.findViewById(C0048R.id.main_device_name);
        ImageView sw = (ImageView) mlin.findViewById(C0048R.id.main_switch);
        LayoutParams linip = lin.getLayoutParams();
        linip.width = width;
        linip.height = height / 15;
        lin.setLayoutParams(linip);
        LayoutParams tvip = tv.getLayoutParams();
        tvip.width = (width / 10) * 7;
        tvip.height = height / 15;
        tv.setLayoutParams(tvip);
        tv.setText(bdi.getName());
        if (bdi.getReset().equals("1")) {
            tv.setTextColor(SupportMenu.CATEGORY_MASK);
        }
        tv.setOnClickListener(this.deviceInformationOnclick);
        tv.setContentDescription(i);
        LayoutParams swip = sw.getLayoutParams();
        swip.width = (width / 10) * 3;
        swip.height = height / 15;
        sw.setLayoutParams(swip);
        if (bdi.getLastState().equals("1")) {
            sw.setImageResource(C0048R.drawable.green);
        } else {
            sw.setImageResource(C0048R.drawable.white);
        }
        sw.setContentDescription(i);
        sw.setClickable(false);
        sw.setAlpha(0.5f);
        this.mainlistItems.add(mlin);
        this.mainList.setAdapter(this.mBaseAdapters);
    }

    public void refreshListView() {
        if (this.sdiList != null && this.allDevices != null && this.allDevices.size() > 0) {
            this.tempSdiList = this.sdiList;
            this.mainlistItems = new ArrayList();
            this.mainList.setAdapter(this.mBaseAdapters);
            for (int i = 0; i < this.allDevices.size(); i++) {
                BLEDeviceInfo ble = (BLEDeviceInfo) this.allDevices.get(i);
                setView(ble, i);
                String alladrss = ble.getAddress();
                LinearLayout mlin = (LinearLayout) this.mainlistItems.get(i);
                TextView tv = (TextView) mlin.findViewById(C0048R.id.main_device_name);
                final ImageView sw = (ImageView) mlin.findViewById(C0048R.id.main_switch);
                boolean bl = false;
                ScanDeviceInfo scan = null;
                if (this.sdiList.size() > 0) {
                    for (int j = 0; j < this.sdiList.size(); j++) {
                        scan = (ScanDeviceInfo) this.sdiList.get(j);
                        String sdiadrss = scan.getDevice().getAddress();
                        if (sdiadrss != null && alladrss != null && sdiadrss.equals(alladrss)) {
                            bl = true;
                            break;
                        }
                    }
                }
                if (bl) {
                    final ScanDeviceInfo sdi = scan;
                    if (ble.getReset().equals("1")) {
                        tv.setTextColor(SupportMenu.CATEGORY_MASK);
                    } else {
                        tv.setTextColor(-16711936);
                    }
                    sw.setClickable(true);
                    sw.setAlpha(1.0f);
                    byte[] data = sdi.getManufacturerData();
                    String lastState = "";
                    if ((data[2] & 15) == 1) {
                        sw.setImageResource(C0048R.drawable.green);
                        lastState = "1";
                    } else if ((data[2] & 15) == 0) {
                        sw.setImageResource(C0048R.drawable.white);
                        lastState = "0";
                    } else if ((data[2] & 15) == 2) {
                        if ((data[1] >> 4) == 1) {
                            sw.setImageResource(C0048R.drawable.green);
                            lastState = "1";
                        } else if ((data[2] >> 4) == 0) {
                            sw.setImageResource(C0048R.drawable.white);
                            lastState = "0";
                        } else if ((data[2] >> 4) == 2) {
                            sw.setImageResource(C0048R.drawable.white);
                            lastState = "0";
                        }
                    }
                    ble.setLastState(lastState);
                    bLEDeviceInfoDAO.updateByAddress(ble);
                    this.allDevices.remove(i);
                    this.allDevices.add(i, ble);
                    sw.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (!MainActivity.connectzt) {
                                MainActivity.connectzt = true;
                                MainActivity.this.connectBDI = (BLEDeviceInfo) MainActivity.this.allDevices.get(Integer.valueOf(Integer.parseInt((String) sw.getContentDescription())).intValue());
                                if (MainActivity.this.connectBDI == null) {
                                    MainActivity.connectzt = false;
                                } else if (MainActivity.this.connectBDI.getReset().equals("1")) {
                                    MainActivity.connectzt = false;
                                    MainActivity.this.showAlertDialog(MainActivity.this.getResources().getString(C0048R.string.mainAlertResetMsg), MainActivity.this.getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                                } else {
                                    MainActivity.this.isConnected = false;
                                    MainActivity.this.setConnectingPB(true);
                                    MainActivity.bluetoothLeScanService.stopLeScanDevice();
                                    MainActivity.this.handle(MainActivity.this.connectBDI, sdi);
                                }
                            }
                        }
                    });
                } else {
                    if (ble.getReset().equals("1")) {
                        tv.setTextColor(SupportMenu.CATEGORY_MASK);
                    } else {
                        tv.setTextColor(-7829368);
                    }
                    sw.setClickable(false);
                    sw.setAlpha(0.5f);
                }
            }
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
        bt1.setOnClickListener(this.addLockOnclick);
        bt2.setOnClickListener(this.useExplainOnclick);
        bt3.setOnClickListener(this.softwareEditionOnclick);
    }

    public void dismiss() {
        if (this.popupwindow != null) {
            this.popupwindow.dismiss();
            this.popupwindow = null;
        }
    }

    public void searchbar(boolean bo) {
        if (bo) {
            this.top_pb_connecting.setVisibility(8);
            this.top_right_ProgressBar.setVisibility(0);
            return;
        }
        this.top_right_ProgressBar.setVisibility(8);
    }

    public void setConnectingPB(boolean bo) {
        if (bo) {
            this.top_right_ProgressBar.setVisibility(8);
            this.top_pb_connecting.setVisibility(0);
            return;
        }
        this.top_pb_connecting.setVisibility(8);
    }

    private void handle(BLEDeviceInfo bdi, ScanDeviceInfo sdi) {
        statenum = sdi.getManufacturerData()[0];
        if (statenum != 0 && statenum != 1) {
            resetConnectState();
            showAlertDialog(getResources().getString(C0048R.string.mainAlertErrorMsg), getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
        } else if (true) {
            ConnectDevice();
        }
    }

    public void ConnectDevice() {
        isCmdLockState = true;
        this.deviceHandler.removeCallbacks(this.connectTimeoutRunnable);
        this.deviceHandler.postDelayed(this.connectTimeoutRunnable, 10000);
        if (mBluetoothLeService != null) {
            mBluetoothLeService.connect(this.connectBDI.getAddress(), 5);
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

    private void showReset() {
        showAlertDialog(getResources().getString(C0048R.string.mainAlertResetMsg), getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
        if (this.connectBDI != null) {
            this.connectBDI.setReset("1");
            bLEDeviceInfoDAO.updateByAddress(this.connectBDI);
        }
        List<BLEDeviceInfo> list = bLEDeviceInfoDAO.queryAll();
        if (list != null && list.size() > 0) {
            this.allDevices = list;
        }
        refreshListView();
    }

    private void showConnectOrHandlerError(int type) {
        String msg = "";
        switch (type) {
            case 0:
                msg = getResources().getString(C0048R.string.mainAlertOpenErrorMsg);
                break;
            case 1:
                msg = getResources().getString(C0048R.string.mainAlertCloseErrorMsg);
                break;
            case 2:
                msg = getResources().getString(C0048R.string.mainAlertConnectErrorMsg);
                break;
            default:
                msg = getResources().getString(C0048R.string.mainAlertConnectErrorMsg);
                break;
        }
        connectzt = false;
        showAlertDialog(msg, getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
    }

    private void sendId() {
        byte[] id = BluetoothLeService.myId;
        if (id == null || id.length != 6) {
            Log.m2i("Not found bluetooth address!");
            id = new byte[]{(byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1};
        }
        if (this.connectBDI != null) {
            byte[] checkKey = this.connectBDI.getCheckKey();
            if (checkKey != null && checkKey.length == 2) {
                byte[] by;
                byte[] idByte = new byte[16];
                idByte[0] = (byte) 74;
                idByte[1] = (byte) 73;
                idByte[2] = (byte) 78;
                idByte[3] = (byte) 79;
                idByte[4] = (byte) 85;
                idByte[5] = checkKey[0];
                idByte[6] = checkKey[1];
                idByte[7] = (byte) 8;
                idByte[8] = (byte) 6;
                idByte[9] = id[0];
                idByte[10] = id[1];
                idByte[11] = id[2];
                idByte[12] = id[3];
                idByte[13] = id[4];
                idByte[14] = id[5];
                if (statenum == 0) {
                    by = AesCipherAndInvCipher.EecryptToBytes(idByte, this.connectBDI.getUserKey());
                } else {
                    idByte = new byte[16];
                    idByte[0] = (byte) 74;
                    idByte[1] = (byte) 73;
                    idByte[2] = (byte) 78;
                    idByte[3] = (byte) 79;
                    idByte[4] = (byte) 85;
                    idByte[7] = (byte) 8;
                    idByte[8] = (byte) 6;
                    idByte[9] = id[0];
                    idByte[10] = id[1];
                    idByte[11] = id[2];
                    idByte[12] = id[3];
                    idByte[13] = id[4];
                    idByte[14] = id[5];
                    by = AesCipherAndInvCipher.EecryptToBytes(idByte, null);
                }
                sendData(by);
            }
        }
    }

    private void sendDataHandle() {
            if (this.connectBDI != null) {
            byte[] checkKey = this.connectBDI.getCheckKey();
            if (checkKey != null && checkKey.length == 2) {
                this.action = 1;
                if ("1".equals(this.connectBDI.getLastState())) {
                    this.action = 0;
                }
                byte[] cmdByte = new byte[16];
                cmdByte[0] = (byte) 74;
                cmdByte[1] = (byte) 73;
                cmdByte[2] = (byte) 78;
                cmdByte[3] = (byte) 79;
                cmdByte[4] = (byte) 85;
                cmdByte[5] = checkKey[0];
                cmdByte[6] = checkKey[1];
                cmdByte[8] = (byte) 2;
                cmdByte[9] = (byte) this.socketid;
                cmdByte[10] = (byte) this.action;
                sendData(AesCipherAndInvCipher.EecryptToBytes(cmdByte, this.connectBDI.getUserKey()));
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
            if (this.connectBDI == null) {
                analysis(this.rePairData);
            } else {
                analysis(this.rePairData);
            }
        } else if (this.rePairData.length == 32) {
            if (this.connectBDI == null) {
                analysis(this.rePairData);
            } else {
                analysis(this.rePairData);
            }
        }
        this.rePairData = null;
    }

    private void analysis(byte[] data) {
        if (data != null) {
            byte[] datays = data;
            if (this.connectBDI != null) {
                data = AesCipherAndInvCipher.Decryptbyte(data, this.connectBDI.getUserKey());
            } else {
                data = AesCipherAndInvCipher.Decryptbyte(data, null);
            }
            if (data[0] != (byte) 74 || data[1] != (byte) 73 || data[2] != (byte) 78 || data[3] != (byte) 79 || data[4] != (byte) 85) {
                data = AesCipherAndInvCipher.Decryptbyte(datays, null);
                resetConnectState();
                return;
            } else if (data[7] == (byte) 2 && data[8] == (byte) 2) {
                byte[] checkByte = new byte[2];
                int nums = 10;
                if (data.length == 16) {
                    checkByte[0] = data[5];
                    checkByte[1] = data[6];
                    this.socketid = data[9];
                } else if (data.length == 32) {
                    checkByte[0] = data[21];
                    checkByte[1] = data[22];
                    this.socketid = data[25];
                    nums = 26;
                }
                this.connectBDI.setLastState(new StringBuilder(String.valueOf(data[nums])).toString());
                this.connectBDI.setCheckKey(checkByte);
                bLEDeviceInfoDAO.updateByAddress(this.connectBDI);
                if (isCmdLockState) {
                    isCmdLockState = false;
                    this.deviceHandler.removeCallbacks(this.connectTimeoutRunnable);
                    this.pageshowstate = 1;
                    setConnectingPB(false);
                    this.deviceHandler.removeCallbacks(this.scanRunnable);
                    sendTimeSetData();
                    openorclose = true;
                    connectsocketshow();
                    return;
                } else if (this.pageshowstate == 1) {
                    Log.m2i("返回开关结果");
                    byte b = data[10];
                    String msg;
                    if (this.action == 1) {
                        if (b == (byte) 1) {
                            if (this.images != null) {
                                this.images.setImageResource(C0048R.drawable.green);
                            }
                        } else if (b == (byte) 0) {
                            showAlertDialog(getResources().getString(C0048R.string.mainAlertOpenErrorMsg), getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                        } else if (b == (byte) 2) {
                            showAlertDialog(getResources().getString(C0048R.string.mainAlertOpenErrorMsg), getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                        } else if (b == (byte) 3 || b == (byte) 4) {
                            msg = "";
                            if (b == (byte) 3) {
                                msg = new StringBuilder(String.valueOf(getResources().getString(C0048R.string.operatefailreason2))).append("，").append(getResources().getString(C0048R.string.mainAlertOpenErrorMsg)).toString();
                            } else {
                                msg = new StringBuilder(String.valueOf(getResources().getString(C0048R.string.operatefailreason1))).append("，").append(getResources().getString(C0048R.string.mainAlertOpenErrorMsg)).toString();
                            }
                            showAlertDialog(msg, getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                        }
                        openorclose = false;
                        return;
                    }
                    if (b == (byte) 1) {
                        showAlertDialog(getResources().getString(C0048R.string.mainAlertCloseErrorMsg), getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                    } else if (b == (byte) 0) {
                        if (this.images != null) {
                            this.images.setImageResource(C0048R.drawable.white);
                        }
                    } else if (b == (byte) 2) {
                        showAlertDialog(getResources().getString(C0048R.string.mainAlertCloseErrorMsg), getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                    } else if (b == (byte) 3 || b == (byte) 4) {
                        msg = "";
                        if (b == (byte) 3) {
                            msg = new StringBuilder(String.valueOf(getResources().getString(C0048R.string.operatefailreason2))).append("，").append(getResources().getString(C0048R.string.mainAlertCloseErrorMsg)).toString();
                        } else {
                            msg = new StringBuilder(String.valueOf(getResources().getString(C0048R.string.operatefailreason1))).append("，").append(getResources().getString(C0048R.string.mainAlertCloseErrorMsg)).toString();
                        }
                        showAlertDialog(msg, getResources().getString(C0048R.string.alertOneButtonTitle), null, 0, 1, null);
                    }
                    openorclose = false;
                    return;
                } else {
                    return;
                }
            } else if (data[7] == (byte) 9) {
                this.timinglist = new ArrayList();
                sendWorkTimeSetData();
                return;
            } else if (data[7] == (byte) 10) {
                this.timingnum = 0;
                openorclose = false;
                foundBuilder(getResources().getString(C0048R.string.app_name), getResources().getString(C0048R.string.settimingwin), getResources().getString(C0048R.string.alertOneButtonTitle), null);
                return;
            } else if (data[7] != (byte) 12) {
                openorclose = false;
                if (this.pageshowstate == 0) {
                    showConnectOrHandlerError(2);
                } else {
                    showConnectOrHandlerError(this.action);
                }
                resetConnectState();
                return;
            } else if (data[8] == (byte) 6) {
                String strk = "";
                if (data[10] < (byte) 10) {
                    strk = "0" + data[10] + ":";
                } else {
                    strk = data[10] + ":";
                }
                if (data[11] < (byte) 10) {
                    strk = new StringBuilder(String.valueOf(strk)).append("0").append(data[11]).toString();
                } else {
                    strk = new StringBuilder(String.valueOf(strk)).append(data[11]).toString();
                }
                String strg = "";
                if (data[12] < (byte) 10) {
                    strg = "0" + data[12] + ":";
                } else {
                    strg = data[12] + ":";
                }
                if (data[13] < (byte) 10) {
                    strg = new StringBuilder(String.valueOf(strg)).append("0").append(data[13]).toString();
                } else {
                    strg = new StringBuilder(String.valueOf(strg)).append(data[13]).toString();
                }
                this.timinglist.add(new TimingOpenOrShut(data[9], strk, strg));
                if (data[14] == (byte) 0) {
                    openorclose = false;
                    timingtimeshow();
                    return;
                }
                return;
            } else {
                openorclose = false;
                return;
            }
        }
        if (this.pageshowstate == 0) {
            showConnectOrHandlerError(2);
        } else {
            showConnectOrHandlerError(this.action);
        }
        resetConnectState();
    }

    private void sendData(byte[] data) {
        if (this.isConnected && mBluetoothLeService != null) {
            mBluetoothLeService.wirte(data);
        }
    }

    private void resetConnectState() {
        new BluetoothLeService().disconnects();
        setConnectingPB(false);
        isCanClick = true;
        this.connectBDI = null;
        isCmdLockState = false;
        connectzt = false;
        this.pageshowstate = 0;
    }

    @SuppressLint({"SimpleDateFormat"})
    public void getDate() {
        if (nm == 1) {
            if (this.tt != null) {
                this.tt.cancel();
            }
            this.tt = null;
        } else if (this.timingnum == 1) {
            if (this.timinglistx.size() >= 2) {
                this.timingnum = 2;
                sendWorkTimeData((byte[]) this.timinglistx.get(1));
                return;
            }
            nm = 1;
        } else if (this.timingnum != 2) {
            nm = 1;
        } else if (this.timinglistx.size() >= 3) {
            this.timingnum = 3;
            sendWorkTimeData((byte[]) this.timinglistx.get(2));
        } else {
            nm = 1;
        }
    }

    public void showAlertDialog(String content, String oneButton, String twoButton, int theme, final int type, Object obj) {
        Builder builder = new Builder(this, theme);
        builder.setMessage(content);
        builder.setPositiveButton(oneButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (type) {
                    case 0:
                        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                            BluetoothAdapter.getDefaultAdapter().disable();
                        }
                        MainActivity.this.dismiss();
                        MainActivity.this.finish();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(twoButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (type) {
                    case 0:
                        MainActivity.this.dismiss();
                        MainActivity.this.finish();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void checkScan() {
        new Thread(new Runnable() {
            public void run() {
                while (MainActivity.bluetoothLeScanService.scanning && 0 <= 10) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (MainActivity.bluetoothLeScanService.scanning) {
                    MainActivity.this.deviceHandler.obtainMessage(2, 0, 0, null).sendToTarget();
                    return;
                }
                MainActivity.this.isStopScan = false;
                MainActivity.this.deviceHandler.postDelayed(MainActivity.this.scanRunnable, 2000);
            }
        }).start();
    }

    public void connectsocketshow() {
        LinearLayout linears = (LinearLayout) this.inflater.inflate(C0048R.layout.deivceinformationlinear, null);
        this.deivceinforlinz = (LinearLayout) linears.findViewById(C0048R.id.deivcelinearbottomx1);
        this.deivceinforlin1 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx1);
        this.deivceinforlin2 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx2);
        this.deivceinforlin3 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx3);
        this.deivcename_text1 = (TextView) linears.findViewById(C0048R.id.deivcename_textx1);
        this.deivcename_text2 = (ImageView) linears.findViewById(C0048R.id.deivcename_textx2);
        this.deivcePower_text1 = (TextView) linears.findViewById(C0048R.id.deivcePower_textx1);
        this.deivcePower_text2 = (ImageView) linears.findViewById(C0048R.id.deivcePower_textx2);
        this.deivcestate_text1 = (TextView) linears.findViewById(C0048R.id.deivcestate_textx1);
        this.deivcestate_text2 = (ImageView) linears.findViewById(C0048R.id.deivcestate_textx2);
        this.deivceinforlin4 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx4);
        this.deivceinforlin5 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx5);
        this.deivceinforlin7 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx7);
        this.deivceinforlin8 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx8);
        this.deivceinforlin9 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx9);
        this.deivceinforlin10 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx10);
        this.deivceinforlin11 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx11);
        this.deivceinforlin12 = (LinearLayout) linears.findViewById(C0048R.id.deivceinforlinx12);
        this.deivcetime_text1 = (TextView) linears.findViewById(C0048R.id.deivcetime_textx1);
        this.settimesbt = (Button) linears.findViewById(C0048R.id.settimesbtx);
        this.setworktimesbt1 = (Button) linears.findViewById(C0048R.id.setworktimesbtx1);
        this.setworktimes_textk1 = (TextView) linears.findViewById(C0048R.id.setworktimes_textkx1);
        this.setworktimes_textk1s = (TextView) linears.findViewById(C0048R.id.setworktimes_textk1xs);
        this.setworktimes_textg1 = (TextView) linears.findViewById(C0048R.id.setworktimes_textgx1);
        this.setworktimes_textg1s = (TextView) linears.findViewById(C0048R.id.setworktimes_textg1xs);
        this.setworktimes_textk2 = (TextView) linears.findViewById(C0048R.id.setworktimes_textkx2);
        this.setworktimes_textk2s = (TextView) linears.findViewById(C0048R.id.setworktimes_textk2xs);
        this.setworktimes_textg2 = (TextView) linears.findViewById(C0048R.id.setworktimes_textgx2);
        this.setworktimes_textg2s = (TextView) linears.findViewById(C0048R.id.setworktimes_textg2xs);
        this.setworktimes_textk3 = (TextView) linears.findViewById(C0048R.id.setworktimes_textkx3);
        this.setworktimes_textk3s = (TextView) linears.findViewById(C0048R.id.setworktimes_textk3xs);
        this.setworktimes_textg3 = (TextView) linears.findViewById(C0048R.id.setworktimes_textgx3);
        this.setworktimes_textg3s = (TextView) linears.findViewById(C0048R.id.setworktimes_textg3xs);
        szlayoutParamsr();
        timingtimeshow();
        this.mainText.setVisibility(8);
        this.mainList.setVisibility(0);
        this.mainlistItems = new ArrayList();
        this.mainlistItems.add(linears);
        this.mainList.setAdapter(this.mBaseAdapters);
    }

    public void szlayoutParamsr() {
        LayoutParams deivceinforlinzip = this.deivceinforlinz.getLayoutParams();
        deivceinforlinzip.width = width;
        deivceinforlinzip.height = (height / 100) * 88;
        this.deivceinforlinz.setLayoutParams(deivceinforlinzip);
        int wi = (width / 100) * 95;
        LayoutParams deivceinforlinip = this.deivceinforlin1.getLayoutParams();
        deivceinforlinip.width = wi;
        deivceinforlinip.height = height / 15;
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
        imagesip.height = height / 16;
        this.deivcename_text2.setLayoutParams(imagesip);
        this.deivcePower_text2.setLayoutParams(imagesip);
        this.deivcestate_text2.setLayoutParams(imagesip);
        if (this.connectBDI != null) {
            this.top_text.setText(this.connectBDI.getName());
            this.top_left_button.setText(getResources().getString(C0048R.string.mainButton));
            this.top_right_bt.setVisibility(8);
            this.top_left_button.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.this.resetConnectState();
                    MainActivity.this.top_text.setText(MainActivity.this.getResources().getString(C0048R.string.mainTitle));
                    MainActivity.openorclose = false;
                    MainActivity.this.top_left_button.setText(MainActivity.this.getResources().getString(C0048R.string.leaveButtonText));
                    MainActivity.this.top_left_button.setOnClickListener(MainActivity.this.menLeftOnclick);
                    MainActivity.this.top_right_bt.setVisibility(0);
                    MainActivity.this.pageshowstate = 0;
                    MainActivity.this.getBLEDeviceInfo();
                    MainActivity.this.deviceHandler.postDelayed(MainActivity.this.scanRunnable, 2000);
                }
            });
            if (this.connectBDI.getLastState().equals("1")) {
                this.deivcename_text2.setImageResource(C0048R.drawable.green);
            } else {
                this.deivcename_text2.setImageResource(C0048R.drawable.white);
                this.deivcePower_text2.setImageResource(C0048R.drawable.white);
                this.deivcestate_text2.setImageResource(C0048R.drawable.white);
            }
            this.deivcename_text2.setOnClickListener(this.mOpenorCloseSocketOnclick);
            this.deivcePower_text2.setOnClickListener(this.mOpenorCloseSocketOnclick);
            this.deivcestate_text2.setOnClickListener(this.mOpenorCloseSocketOnclick);
            if (this.socketid == 1) {
                this.deivcePower_text1.setVisibility(8);
                this.deivcePower_text2.setVisibility(8);
                this.deivcestate_text1.setVisibility(8);
                this.deivcestate_text2.setVisibility(8);
            } else if (this.socketid == 2) {
                this.deivcestate_text1.setVisibility(8);
                this.deivcestate_text2.setVisibility(8);
            }
        }
        LayoutParams deivceinforlin4ip = this.deivceinforlin4.getLayoutParams();
        deivceinforlin4ip.width = wi;
        deivceinforlin4ip.height = height / 15;
        this.deivceinforlin4.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin5.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin7.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin8.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin9.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin10.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin11.setLayoutParams(deivceinforlin4ip);
        this.deivceinforlin12.setLayoutParams(deivceinforlin4ip);
        LayoutParams deivcetime_text1ip = this.deivcetime_text1.getLayoutParams();
        deivcetime_text1ip.width = wi / 2;
        deivcetime_text1ip.height = height / 15;
        this.deivcetime_text1.setLayoutParams(deivcetime_text1ip);
        LayoutParams settimesbtip = this.settimesbt.getLayoutParams();
        settimesbtip.width = wi - (wi / 2);
        settimesbtip.height = height / 15;
        this.settimesbt.setLayoutParams(settimesbtip);
        this.settimesbt.setOnClickListener(this.mQueryTimingSocketOnclick);
        LayoutParams times_textsip = this.setworktimes_textk1.getLayoutParams();
        times_textsip.width = wi / 2;
        times_textsip.height = height / 15;
        this.deivcetime_text1.setLayoutParams(times_textsip);
        this.setworktimes_textk1.setLayoutParams(times_textsip);
        this.setworktimes_textk1s.setLayoutParams(times_textsip);
        this.setworktimes_textg1.setLayoutParams(times_textsip);
        this.setworktimes_textg1s.setLayoutParams(times_textsip);
        this.setworktimes_textk2.setLayoutParams(times_textsip);
        this.setworktimes_textk2s.setLayoutParams(times_textsip);
        this.setworktimes_textg2.setLayoutParams(times_textsip);
        this.setworktimes_textg2s.setLayoutParams(times_textsip);
        this.setworktimes_textk3.setLayoutParams(times_textsip);
        this.setworktimes_textk3s.setLayoutParams(times_textsip);
        this.setworktimes_textg3.setLayoutParams(times_textsip);
        this.setworktimes_textg3s.setLayoutParams(times_textsip);
        LayoutParams setworktimesbt1ip = this.setworktimesbt1.getLayoutParams();
        setworktimesbt1ip.width = wi / 2;
        setworktimesbt1ip.height = height / 15;
        this.setworktimesbt1.setLayoutParams(setworktimesbt1ip);
        this.setworktimesbt1.setOnClickListener(this.mTimingTimeSetOnclick);
        this.setworktimes_textk1s.setOnClickListener(this.mTimingSocketOnclick);
        this.setworktimes_textg1s.setOnClickListener(this.mTimingSocketOnclick);
        this.setworktimes_textk2s.setOnClickListener(this.mTimingSocketOnclick);
        this.setworktimes_textg2s.setOnClickListener(this.mTimingSocketOnclick);
        this.setworktimes_textk3s.setOnClickListener(this.mTimingSocketOnclick);
        this.setworktimes_textg3s.setOnClickListener(this.mTimingSocketOnclick);
    }

    public void timingtimeshow() {
        if (this.timinglist != null && this.timinglist.size() > 0) {
            for (int i = 0; i < this.timinglist.size(); i++) {
                TimingOpenOrShut tos = (TimingOpenOrShut) this.timinglist.get(i);
                if (tos.getTimingid() == 0) {
                    if (!(this.setworktimes_textk1s == null || this.setworktimes_textg1s == null)) {
                        this.setworktimes_textk1s.setText(tos.getTimingopen());
                        this.setworktimes_textg1s.setText(tos.getTimingshut());
                    }
                } else if (tos.getTimingid() == 1) {
                    if (!(this.setworktimes_textk2s == null || this.setworktimes_textg2s == null)) {
                        this.setworktimes_textk2s.setText(tos.getTimingopen());
                        this.setworktimes_textg2s.setText(tos.getTimingshut());
                    }
                } else if (!(tos.getTimingid() != 2 || this.setworktimes_textk3s == null || this.setworktimes_textg3s == null)) {
                    this.setworktimes_textk3s.setText(tos.getTimingopen());
                    this.setworktimes_textg3s.setText(tos.getTimingshut());
                }
            }
        }
    }

    public boolean timingtimebo(int tid, String timingk, String timingg) {
        TimingOpenOrShut tos;
        boolean bo = true;
        if (timingk.equals(timingg)) {
            bo = false;
        }
        int num = 0;
        if (bo && this.timinglist != null && this.timinglist.size() > 0) {
            int i = 0;
            while (i < this.timinglist.size()) {
                tos = (TimingOpenOrShut) this.timinglist.get(i);
                if (tos.getTimingid() == tid) {
                    if (timingk.equals(tos.getTimingopen()) && timingg.equals(tos.getTimingshut())) {
                        bo = false;
                    }
                    num = i;
                } else {
                    i++;
                }
            }
        }
        if (bo) {
            tos = new TimingOpenOrShut(tid, timingk, timingg);
            if (this.timinglist.size() > 0) {
                this.timinglist.remove(num);
            }
            this.timinglist.add(num, tos);
        }
        return bo;
    }

    public byte[] timingtimebytes(int tid, String timingk, String timingg, int more) {
        by = new byte[6];
        String[] strk = timingk.split(":");
        String[] strg = timingg.split(":");
        by[1] = (byte) Integer.parseInt(strk[0]);
        by[2] = (byte) Integer.parseInt(strk[1]);
        by[3] = (byte) Integer.parseInt(strg[0]);
        by[4] = (byte) Integer.parseInt(strg[1]);
        by[5] = (byte) more;
        return by;
    }

    private void sendWorkTimeSetData() {
        if (this.connectBDI != null) {
            byte[] checkKey = this.connectBDI.getCheckKey();
            if (checkKey != null && checkKey.length == 2) {
                byte[] cmdByte = new byte[16];
                cmdByte[0] = (byte) 74;
                cmdByte[1] = (byte) 73;
                cmdByte[2] = (byte) 78;
                cmdByte[3] = (byte) 79;
                cmdByte[4] = (byte) 85;
                cmdByte[5] = checkKey[0];
                cmdByte[6] = checkKey[1];
                cmdByte[7] = (byte) 11;
                sendData(AesCipherAndInvCipher.EecryptToBytes(cmdByte, this.connectBDI.getUserKey()));
            }
        }
    }

    private void sendTimeSetData() {
        if (this.connectBDI != null) {
            byte[] timeby = timeDatebytes();
            byte[] checkKey = this.connectBDI.getCheckKey();
            if (checkKey != null && checkKey.length == 2) {
                byte[] cmdByte = new byte[16];
                cmdByte[0] = (byte) 74;
                cmdByte[1] = (byte) 73;
                cmdByte[2] = (byte) 78;
                cmdByte[3] = (byte) 79;
                cmdByte[4] = (byte) 85;
                cmdByte[5] = checkKey[0];
                cmdByte[6] = checkKey[1];
                cmdByte[7] = (byte) 9;
                cmdByte[8] = (byte) 6;
                cmdByte[9] = timeby[0];
                cmdByte[10] = timeby[1];
                cmdByte[11] = timeby[2];
                cmdByte[12] = timeby[3];
                cmdByte[13] = timeby[4];
                cmdByte[14] = timeby[5];
                sendData(AesCipherAndInvCipher.EecryptToBytes(cmdByte, this.connectBDI.getUserKey()));
            }
        }
    }

    private void sendWorkTimeData(byte[] timeby) {
        if (this.connectBDI != null) {
            byte[] checkKey = this.connectBDI.getCheckKey();
            if (checkKey != null && checkKey.length == 2) {
                byte[] cmdByte = new byte[16];
                cmdByte[0] = (byte) 74;
                cmdByte[1] = (byte) 73;
                cmdByte[2] = (byte) 78;
                cmdByte[3] = (byte) 79;
                cmdByte[4] = (byte) 85;
                cmdByte[5] = checkKey[0];
                cmdByte[6] = checkKey[1];
                cmdByte[7] = (byte) 10;
                cmdByte[8] = (byte) 6;
                cmdByte[9] = timeby[0];
                cmdByte[10] = timeby[1];
                cmdByte[11] = timeby[2];
                cmdByte[12] = timeby[3];
                cmdByte[13] = timeby[4];
                cmdByte[14] = timeby[5];
                String sml = Tools.bytesToHexString(cmdByte);
                sendData(AesCipherAndInvCipher.EecryptToBytes(cmdByte, this.connectBDI.getUserKey()));
            }
        }
    }

    public byte[] timeDatebytes() {
        byte[] by = new byte[6];
        String times = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        for (int i = 0; i < times.length(); i += 2) {
            by[i / 2] = (byte) Integer.parseInt(times.substring(i, i + 2));
        }
        return by;
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mGattUpdateReceiver);
        unbindService(this.mServiceConnection);
        mBluetoothLeService = null;
    }

    protected void onResume() {
        super.onResume();
        if (connectzt && this.pageshowstate == 0) {
            new BluetoothLeService().disconnects();
            setConnectingPB(false);
            isCanClick = true;
            this.connectBDI = null;
            isCmdLockState = false;
            connectzt = false;
        }
        bluetoothLeScanService.init(this.deviceHandler, SCAN_PERIOD, 0);
        if (this.connectBDI != null && this.pageshowstate == 0) {
            setConnectingPB(true);
        }
        if (this.pageshowstate == 0) {
            this.mainList.setClickable(false);
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                return;
            }
            if (this.allDevices == null || this.allDevices.size() <= 0) {
                searchbar(false);
            } else {
                checkScan();
            }
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.pageshowstate == 0) {
            isCanClick = true;
            this.isStopScan = true;
            connectzt = false;
            isCmdLockState = false;
            this.deviceHandler.removeCallbacks(this.scanRunnable);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.pageshowstate == 1) {
                resetConnectState();
                this.top_text.setText(getResources().getString(C0048R.string.mainTitle));
                this.top_left_button.setText(getResources().getString(C0048R.string.leaveButtonText));
                this.top_left_button.setOnClickListener(this.menLeftOnclick);
                this.top_right_bt.setVisibility(0);
                this.pageshowstate = 0;
                getBLEDeviceInfo();
                this.deviceHandler.postDelayed(this.scanRunnable, 2000);
            } else {
                showAlertDialog(getResources().getString(C0048R.string.mainAlertExitMsg), getResources().getString(C0048R.string.alertTwoButtonTitle1), getResources().getString(C0048R.string.alertTwoButtonTitle2), 1, 0, Integer.valueOf(0));
            }
        }
        return false;
    }

    public void foundBuilder(String title, String message, String button1, String button2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        if (!(button1 == null || button1.equals(""))) {
            builder.setPositiveButton(button1, null);
        }
        if (!(button2 == null || button2.equals(""))) {
            builder.setNegativeButton(button2, null);
        }
        builder.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0048R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
