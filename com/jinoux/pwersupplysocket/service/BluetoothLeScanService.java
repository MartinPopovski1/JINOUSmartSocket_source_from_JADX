package com.jinoux.pwersupplysocket.service;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import com.jinoux.pwersupplysocket.modle.ScanDeviceInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"NewApi"})
public class BluetoothLeScanService {
    private List<BluetoothDevice> bdList = new ArrayList();
    public BluetoothAdapter bluetoothAdapter;
    private Handler deviceHandler;
    private LeScanCallback mLeScanCallback = new C00962();
    private int openTime = 5;
    public boolean scanning = false;
    private int scanperiod;
    Runnable stop = new C00951();
    private int whichOne;
    private int whichOneNow;

    class C00951 implements Runnable {
        C00951() {
        }

        public void run() {
            BluetoothLeScanService.this.scanning = false;
            BluetoothLeScanService.this.bluetoothAdapter.stopLeScan(BluetoothLeScanService.this.mLeScanCallback);
            BluetoothLeScanService.this.bdList = new ArrayList();
            BluetoothLeScanService.this.deviceHandler.obtainMessage(1, BluetoothLeScanService.this.whichOneNow, 0, null).sendToTarget();
        }
    }

    class C00962 implements LeScanCallback {
        C00962() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            boolean flag = true;
            for (int i = 0; i < BluetoothLeScanService.this.bdList.size(); i++) {
                if (((BluetoothDevice) BluetoothLeScanService.this.bdList.get(i)).getAddress().equals(device.getAddress())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                BluetoothLeScanService.this.bdList.add(device);
                BluetoothLeScanService.this.doIt(device, rssi, scanRecord);
            }
        }
    }

    class C00973 extends TimerTask {
        C00973() {
        }

        public void run() {
            BluetoothLeScanService.this.scanning = false;
            BluetoothAdapter.getDefaultAdapter().enable();
        }
    }

    public BluetoothLeScanService(Context ct) {
        this.bluetoothAdapter = ((BluetoothManager) ct.getSystemService("bluetooth")).getAdapter();
    }

    public void init(Handler deviceHandler, int scanperiod, int whichOne) {
        this.deviceHandler = deviceHandler;
        this.scanperiod = scanperiod;
        this.whichOne = whichOne;
    }

    public void stopLeScanDevice() {
        if (this.scanning) {
            this.scanning = false;
            this.bluetoothAdapter.stopLeScan(this.mLeScanCallback);
            this.bdList = new ArrayList();
            this.deviceHandler.removeCallbacks(this.stop, null);
        }
    }

    public void startScanLeDevice(boolean enable) {
        this.whichOneNow = this.whichOne;
        this.deviceHandler.removeCallbacks(this.stop, null);
        if (!enable) {
            this.scanning = false;
            this.bluetoothAdapter.stopLeScan(this.mLeScanCallback);
            this.deviceHandler.obtainMessage(1, this.whichOneNow, 0, null).sendToTarget();
        } else if (this.scanning) {
            this.scanning = false;
            this.bluetoothAdapter.stopLeScan(this.mLeScanCallback);
            this.deviceHandler.obtainMessage(1, this.whichOneNow, 0, null).sendToTarget();
        } else {
            this.scanning = true;
            this.deviceHandler.postDelayed(this.stop, (long) this.scanperiod);
            this.bdList = new ArrayList();
            if (!this.bluetoothAdapter.startLeScan(this.mLeScanCallback)) {
                if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    BluetoothAdapter.getDefaultAdapter().disable();
                }
                new Timer().schedule(new C00973(), (long) (this.openTime * 1000));
            }
        }
    }

    private void doIt(BluetoothDevice device, int rssi, byte[] scanRecord) {
        byte[] manufacturerData = socketScanRecord(device, scanRecord);
        if (manufacturerData != null) {
            this.deviceHandler.obtainMessage(0, this.whichOneNow, 0, new ScanDeviceInfo(device, scanRecord, rssi, false, manufacturerData)).sendToTarget();
        }
    }

    private byte[] socketScanRecord(BluetoothDevice device, byte[] scanRecord) {
        byte[] manufacturerData = new byte[3];
        if (scanRecord.length <= 27) {
            return null;
        }
        int num = 0;
        int nums = 0;
        List<Integer> list = new ArrayList();
        List<Integer> cdlist = new ArrayList();
        int zzz = 0;
        int i = 0;
        while (i < scanRecord.length) {
            if (i <= 1) {
                if (i == 0) {
                    num = scanRecord[i] & MotionEventCompat.ACTION_MASK;
                    zzz++;
                } else {
                    list.add(Integer.valueOf(scanRecord[i] & MotionEventCompat.ACTION_MASK));
                    cdlist.add(Integer.valueOf(i));
                    zzz += num;
                    nums = zzz;
                }
            } else if (nums == zzz) {
                num = scanRecord[i] & MotionEventCompat.ACTION_MASK;
                zzz++;
            } else {
                list.add(Integer.valueOf(scanRecord[i] & MotionEventCompat.ACTION_MASK));
                cdlist.add(Integer.valueOf(i));
                zzz += num;
                nums = zzz;
            }
            i = zzz;
        }
        int yzm = 0;
        boolean bl = false;
        for (i = 0; i < list.size(); i++) {
            if (((Integer) list.get(i)).intValue() == MotionEventCompat.ACTION_MASK && !bl) {
                yzm++;
                num = ((Integer) cdlist.get(i)).intValue();
                if (Integer.valueOf(scanRecord[num - 1] & MotionEventCompat.ACTION_MASK).intValue() == 4 && scanRecord[num + 4] == (byte) 13) {
                    bl = true;
                }
            }
        }
        if (!bl) {
            return null;
        }
        manufacturerData[0] = scanRecord[num + 1];
        manufacturerData[1] = scanRecord[num + 2];
        manufacturerData[2] = scanRecord[num + 3];
        return manufacturerData;
    }
}
