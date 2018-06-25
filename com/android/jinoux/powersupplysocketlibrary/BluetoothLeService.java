package com.android.jinoux.powersupplysocketlibrary;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.android.jinoux.util.Log;
import com.android.jinoux.util.Tools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressLint({"NewApi"})
public class BluetoothLeService extends Service {
    public static final String ACTION_DATA_AVAILABLE = "com.jinoux.android.btlock.ACTION_DATA_AVAILABLE";
    public static final String ACTION_GATT_CONNECTED = "com.jinoux.android.btlock.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DATARECEIVED = "com.jinoux.android.btlock.ACTION_GATT_DATARECEIVED";
    public static final String ACTION_GATT_DISCONNECTED = "com.jinoux.android.btlock.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_HANDLERDATAERROR = "com.jinoux.android.btlock.ACTION_GATT_HANDLERDATAERROR";
    public static final String ACTION_GATT_READCHARACTERISTICERROR = "com.jinoux.android.btlock.ACTION_GATT_READCHARACTERISTICERROR";
    public static final String ACTION_GATT_READCHARACTERISTICSUCCESS = "com.jinoux.android.btlock.ACTION_GATT_READCHARACTERISTICSUCCESS";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.jinoux.android.btlock.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String DATA_NAME = "receivedData";
    public static final int DEFAULT_CONNECT_TIMEOUT = 5;
    private static final int DEFAULT_DISCOVER_SERVICES_TIMEOUT = 3;
    public static final int DEFAULT_READ_CHARACTERISTIC_TIME = 100;
    private static final int DEFAULT_READ_CHARACTERISTIC__TIMEOUT = 3;
    public static final String EXTRA_DATA = "com.jinoux.android.btlock.EXTRA_DATA";
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTERROR = 3;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_READ_SUCCESS = 5;
    public static final int STATE_SERVICE_DISCOVERED = 4;
    private static final String TAG = BluetoothLeService.class.getSimpleName();
    public static final UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    private static boolean all = true;
    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothGatt mBluetoothGatt;
    public static int mConnectionState = 0;
    public static byte[] myId;
    private static Handler slaaa;
    private BluetoothGattCharacteristic DEVICE_CAN_RECEIVE_PACKET_Characteristic;
    private BluetoothGattCharacteristic DEVICE_RECEIVED_ERROR_PACKET_SEQUENCE_Characteristic;
    private BluetoothGattCharacteristic DEVICE_RECEIVED_PACKET_SEQUENCE_Characteristic;
    private BluetoothGattCharacteristic FOR_SERIAL_PORT_READ_Characteristic;
    private BluetoothGattCharacteristic HOST_CAN_RECEIVE_PACKET_Characteristic;
    private BluetoothGattCharacteristic HOST_RECEIVED_ERROR_PACKET_SEQUENCE_Characteristic;
    private BluetoothGattCharacteristic HOST_RECEIVED_PACKET_SEQUENCE_Characteristic;
    private BluetoothGattCharacteristic MAX_PACKET_SIZE_Characteristic;
    private BluetoothGattCharacteristic NO_RESPONSE_MAX_PACKET_COUNT_Characteristic;
    private BluetoothGattCharacteristic PACKET_TIMEOUT_Characteristic;
    private BluetoothGattCharacteristic RESET_SEQUENCE_Characteristic;
    private BluetoothGattCharacteristic SERIAL_PORT_WRITE_Characteristic;
    public boolean ba = false;
    Runnable connectTimeoutRunnable = new C00502();
    private int continuation = 0;
    Runnable discoverServicesTimeoutRunnable = new C00513();
    JOBluetoothDataPacket dp;
    private final IBinder mBinder = new LocalBinder();
    private String mBluetoothDeviceAddress;
    private BluetoothManager mBluetoothManager;
    private final BluetoothGattCallback mGattCallback = new C00491();
    private Thread mReadCharacteristicThread;
    private int readCharacteristicCount = 0;
    Handler readCharacteristicHandler = new C00535();
    Runnable readCharacteristicTimeoutRunnable = new C00524();
    private int readMaxPacketSize = 0;
    private int readNoResponseMaxPacketCount = 0;
    private int readPacketTimeout = 0;
    private Handler serviceHnadler = new C00546();
    private int stateReadMaxPacketSize = 0;
    private int stateReadNoResponseMaxPacketCount = 0;
    private int stateReadPacketTimeout = 0;
    private int stateReadSuccess = 0;

    class C00491 extends BluetoothGattCallback {
        C00491() {
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.m2i("=====status:" + status + "====newState:" + newState);
            String intentAction;
            if (newState == 2) {
                if (BluetoothLeService.mConnectionState == 1) {
                    boolean bo = BluetoothLeService.mBluetoothGatt.discoverServices();
                    intentAction = BluetoothLeService.ACTION_GATT_CONNECTED;
                    BluetoothLeService.mConnectionState = 2;
                    BluetoothLeService.this.broadcastUpdate(intentAction);
                    BluetoothLeService.this.serviceHnadler.removeCallbacks(BluetoothLeService.this.connectTimeoutRunnable);
                    BluetoothLeService.this.serviceHnadler.postDelayed(BluetoothLeService.this.discoverServicesTimeoutRunnable, 3000);
                    Log.m2i("Attempting to start service discovery:" + bo);
                }
            } else if (newState == 0) {
                BluetoothLeService.mConnectionState = 0;
                BluetoothLeService.this.mBluetoothDeviceAddress = null;
                intentAction = BluetoothLeService.ACTION_GATT_DISCONNECTED;
                Log.m2i("Disconnected from GATT server.intentAction: " + intentAction);
                BluetoothLeService.this.dp = null;
                BluetoothLeService.this.broadcastUpdate(intentAction);
                BluetoothLeService.this.resetReadCharacteristic();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                BluetoothLeService.mConnectionState = 4;
                BluetoothLeService.this.serviceHnadler.removeCallbacks(BluetoothLeService.this.discoverServicesTimeoutRunnable);
                BluetoothLeService.this.getGattServices(BluetoothLeService.this.getSupportedGattServices());
                return;
            }
            Log.m2i("onServicesDiscovered received: " + status);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0 && characteristic.getValue() != null) {
                if (characteristic.getUuid().toString().equals(SampleGattAttributes.MAX_PACKET_SIZE)) {
                    BluetoothLeService.this.readMaxPacketSize = characteristic.getValue()[0];
                    BluetoothLeService.this.stateReadMaxPacketSize = 1;
                } else if (characteristic.getUuid().toString().equals(SampleGattAttributes.NO_RESPONSE_MAX_PACKET_COUNT)) {
                    BluetoothLeService.this.readNoResponseMaxPacketCount = characteristic.getValue()[0];
                    BluetoothLeService.this.stateReadNoResponseMaxPacketCount = 1;
                } else if (characteristic.getUuid().toString().equals(SampleGattAttributes.PACKET_TIMEOUT)) {
                    BluetoothLeService.this.readPacketTimeout = characteristic.getValue()[0];
                    BluetoothLeService.this.stateReadPacketTimeout = 1;
                }
                BluetoothLeService.this.checkReadCharacteristic();
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.m2i("onDescriptorWrite = " + status + ", descriptor =" + descriptor.getUuid().toString());
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (characteristic != null) {
                BluetoothLeService.this.whichChanged(characteristic);
            }
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Log.m2i("rssi = " + rssi);
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }
    }

    class C00502 implements Runnable {
        C00502() {
        }

        public void run() {
            if (BluetoothLeService.mConnectionState != 2) {
                Log.m2i("conect timeout --> disconnect");
                BluetoothLeService.this.disconnect(0);
            }
            BluetoothLeService.this.serviceHnadler.removeCallbacks(BluetoothLeService.this.connectTimeoutRunnable);
        }
    }

    class C00513 implements Runnable {
        C00513() {
        }

        public void run() {
            if (BluetoothLeService.mConnectionState != 4) {
                BluetoothLeService.this.disconnect(0);
            }
            BluetoothLeService.this.serviceHnadler.removeCallbacks(BluetoothLeService.this.discoverServicesTimeoutRunnable);
        }
    }

    class C00524 implements Runnable {
        C00524() {
        }

        public void run() {
            if (BluetoothLeService.mConnectionState != 4) {
                BluetoothLeService.this.disconnect(0);
            } else if (BluetoothLeService.this.stateReadSuccess == 5) {
                BluetoothLeService.this.serviceHnadler.removeCallbacks(BluetoothLeService.this.readCharacteristicTimeoutRunnable);
            } else if (BluetoothLeService.this.readCharacteristicCount < 1) {
                BluetoothLeService.this.resetReadCharacteristic();
                BluetoothLeService.this.startReadCharacteristic();
                BluetoothLeService.this.serviceHnadler.postDelayed(BluetoothLeService.this.readCharacteristicTimeoutRunnable, 3000);
                BluetoothLeService bluetoothLeService = BluetoothLeService.this;
                bluetoothLeService.readCharacteristicCount = bluetoothLeService.readCharacteristicCount + 1;
            } else {
                BluetoothLeService.this.disconnect(0);
            }
        }
    }

    class C00535 extends Handler {
        C00535() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BluetoothLeService.this.readCharacteristic(BluetoothLeService.this.MAX_PACKET_SIZE_Characteristic);
                    return;
                case 1:
                    BluetoothLeService.this.readCharacteristic(BluetoothLeService.this.NO_RESPONSE_MAX_PACKET_COUNT_Characteristic);
                    return;
                case 2:
                    BluetoothLeService.this.readCharacteristic(BluetoothLeService.this.PACKET_TIMEOUT_Characteristic);
                    return;
                default:
                    return;
            }
        }
    }

    class C00546 extends Handler {
        C00546() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BluetoothLeService.this.HOST_CAN_RECEIVE_PACKET_Characteristic.setValue((byte[]) msg.obj);
                    BluetoothLeService.this.wirteCharacteristic(BluetoothLeService.this.HOST_CAN_RECEIVE_PACKET_Characteristic);
                    return;
                case 1:
                    if (BluetoothLeService.this.RESET_SEQUENCE_Characteristic != null) {
                        BluetoothLeService.this.RESET_SEQUENCE_Characteristic.setValue((byte[]) msg.obj);
                        BluetoothLeService.this.wirteCharacteristic(BluetoothLeService.this.RESET_SEQUENCE_Characteristic);
                        return;
                    }
                    return;
                case 2:
                    BluetoothLeService.this.disconnect(1);
                    return;
                case 3:
                    BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_GATT_DATARECEIVED, (byte[]) msg.obj);
                    return;
                case 4:
                    if (BluetoothLeService.mConnectionState == 4 && BluetoothLeService.this.stateReadSuccess == 5) {
                        BluetoothLeService.this.SERIAL_PORT_WRITE_Characteristic.setValue(msg.obj);
                        BluetoothLeService.this.wirteCharacteristic(BluetoothLeService.this.SERIAL_PORT_WRITE_Characteristic);
                        return;
                    }
                    return;
                case 5:
                    int i;
                    if (msg.arg1 == 0) {
                        BluetoothLeService.this.HOST_RECEIVED_ERROR_PACKET_SEQUENCE_Characteristic.setValue((byte[]) msg.obj);
                        for (i = 0; i < 5; i++) {
                            BluetoothLeService.this.wirteCharacteristicr(BluetoothLeService.this.HOST_RECEIVED_ERROR_PACKET_SEQUENCE_Characteristic);
                        }
                        return;
                    } else if (msg.arg1 == 1) {
                        BluetoothLeService.this.HOST_RECEIVED_PACKET_SEQUENCE_Characteristic.setValue((byte[]) msg.obj);
                        for (i = 0; i < 5; i++) {
                            BluetoothLeService.this.wirteCharacteristicr(BluetoothLeService.this.HOST_RECEIVED_PACKET_SEQUENCE_Characteristic);
                        }
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            BluetoothLeService.mConnectionState = 0;
            return BluetoothLeService.this;
        }
    }

    private class readCharacteristicThread extends Thread {
        private readCharacteristicThread() {
        }

        public void run() {
            super.run();
            int i = 0;
            while (i < 3) {
                try {
                    Thread.sleep(100);
                    BluetoothLeService.this.readCharacteristicHandler.sendEmptyMessage(i);
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    public static void ss(Handler slaa) {
        all = true;
        slaaa = slaa;
    }

    private void checkReadCharacteristic() {
        if (this.stateReadMaxPacketSize == 1 && this.stateReadNoResponseMaxPacketCount == 1 && this.stateReadPacketTimeout == 1) {
            this.stateReadSuccess = 5;
            this.dp.initN(this.readMaxPacketSize, this.readNoResponseMaxPacketCount, this.readPacketTimeout);
            this.serviceHnadler.removeCallbacks(this.readCharacteristicTimeoutRunnable);
            broadcastUpdate(ACTION_GATT_READCHARACTERISTICSUCCESS);
        }
    }

    public void resetReadCharacteristic() {
        this.stateReadMaxPacketSize = 0;
        this.stateReadNoResponseMaxPacketCount = 0;
        this.stateReadPacketTimeout = 0;
        this.stateReadSuccess = 0;
        this.readCharacteristicCount = 0;
    }

    private void whichChanged(BluetoothGattCharacteristic characteristic) {
        String UUIDStr = characteristic.getUuid().toString();
        if (UUIDStr.equals(SampleGattAttributes.FOR_SERIAL_PORT_READ)) {
            if (characteristic.getValue() == null || this.dp == null) {
                Log.m2i("characteristic.getValue()== null");
            } else {
                this.dp.recvDataFromPeer(characteristic.getValue());
            }
        } else if (UUIDStr.equals(SampleGattAttributes.MAX_PACKET_SIZE)) {
            if (characteristic.getValue() == null || this.dp == null) {
                Log.m2i("characteristic.getValue()== null");
                return;
            }
            this.dp.nMaxPacketSize = characteristic.getValue()[0];
        } else if (UUIDStr.equals(SampleGattAttributes.NO_RESPONSE_MAX_PACKET_COUNT)) {
            if (characteristic.getValue() != null && this.dp != null) {
                this.dp.nNoResponseAllowMaxPacketCount = characteristic.getValue()[0];
            }
        } else if (UUIDStr.equals(SampleGattAttributes.PACKET_TIMEOUT)) {
            if (characteristic.getValue() == null || this.dp == null) {
                Log.m2i("characteristic.getValue()== null");
                return;
            }
            this.dp.nPacketTimeout = characteristic.getValue()[0];
        } else if (UUIDStr.equals(SampleGattAttributes.DEVICE_RECEIVED_PACKET_SEQUENCE)) {
            if (characteristic.getValue() != null) {
                this.dp.recvDataPackeSequence(characteristic.getValue()[0], false);
            } else {
                Log.m2i("characteristic.getValue()== null");
            }
        } else if (UUIDStr.equals(SampleGattAttributes.DEVICE_RECEIVED_ERROR_PACKET_SEQUENCE)) {
            if (characteristic.getValue() == null || this.dp == null) {
                Log.m2i("characteristic.getValue()== null");
            } else {
                this.dp.recvDataPackeSequence(characteristic.getValue()[0], true);
            }
        } else if (UUIDStr.equals(SampleGattAttributes.DEVICE_CAN_RECEIVE_PACKET)) {
            if (characteristic.getValue() == null) {
                this.dp.setBPeerCanReceive(true);
            } else if (characteristic.getValue()[0] == (byte) 0) {
                this.dp.setBPeerCanReceive(false);
            } else {
                this.dp.setBPeerCanReceive(true);
            }
        } else if (UUIDStr.equals(SampleGattAttributes.RESET_SEQUENCE)) {
            Log.m2i("RESET_SEQUENCE --> 0000b35C-0000-1000-8000-00805f9b34fb");
        }
    }

    public void broadcastUpdate(String action) {
        sendBroadcast(new Intent(action));
    }

    private void broadcastUpdate(String action, byte[] data) {
        Intent intent = new Intent(action);
        intent.putExtra(DATA_NAME, data);
        sendBroadcast(intent);
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager) getSystemService("bluetooth");
            if (this.mBluetoothManager == null) {
                Log.m2i("Unable to initialize BluetoothManager.");
                return false;
            }
        }
        mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        String[] address = mBluetoothAdapter.getAddress().split(":");
        myId = Tools.hexStrToStr(address[0] + address[1] + address[2] + address[3] + address[4] + address[5]);
        if (mBluetoothAdapter != null) {
            return true;
        }
        Log.m2i("Unable to obtain a BluetoothAdapter.");
        return false;
    }

    public boolean connect(String address, int timeout) {
        if (mBluetoothAdapter == null || address == null) {
            Log.m2i("BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        if (mConnectionState != 4 || this.mBluetoothDeviceAddress == null || !this.mBluetoothDeviceAddress.equals(address)) {
            this.continuation = 0;
        } else if (this.continuation < 1) {
            this.continuation = 1;
            return true;
        } else if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        if (mConnectionState != 1) {
            Log.m2i("Trying to use an existing mBluetoothGatt for connection.1");
            this.dp = new JOBluetoothDataPacket(this.serviceHnadler);
            if (mBluetoothGatt == null || this.mBluetoothDeviceAddress == null || !this.mBluetoothDeviceAddress.equals(address)) {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                if (device == null) {
                    Log.m2i("Device not found,Unable to connect.Return false!");
                    return false;
                }
                mBluetoothGatt = device.connectGatt(this, false, this.mGattCallback);
            } else {
                Log.m2i("Trying to use an existing mBluetoothGatt for connection.2");
            }
            resetReadCharacteristic();
            this.mBluetoothDeviceAddress = address;
            mConnectionState = 1;
            this.serviceHnadler.postDelayed(this.connectTimeoutRunnable, (long) (timeout * 1000));
            return true;
        }
        Log.m2i("mBluetoothGatt is connecting.Return false!");
        mConnectionState = 0;
        return false;
    }

    public void disconnect(int type) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.m2i("BluetoothAdapter not initialized");
        } else if (mConnectionState != 0) {
            mConnectionState = 0;
            mBluetoothGatt.disconnect();
            close();
            switch (type) {
                case 0:
                    broadcastUpdate(ACTION_GATT_READCHARACTERISTICERROR);
                    break;
                case 1:
                    broadcastUpdate(ACTION_GATT_HANDLERDATAERROR);
                    break;
            }
            resetReadCharacteristic();
        }
        this.dp = null;
        mBluetoothGatt = null;
    }

    public void disconnects() {
        if (mBluetoothGatt != null) {
            mConnectionState = 0;
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            resetReadCharacteristic();
            this.dp = null;
            mBluetoothGatt = null;
        }
    }

    public void close() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
    }

    public void wirteCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.m2i("BluetoothAdapter not initialized");
        } else {
            mBluetoothGatt.writeCharacteristic(characteristic);
        }
    }

    public void wirteCharacteristicr(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.m2i("BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public boolean readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.m2i("BluetoothAdapter not initialized");
            return false;
        } else if (characteristic != null) {
            return mBluetoothGatt.readCharacteristic(characteristic);
        } else {
            return false;
        }
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.m2i("BluetoothAdapter not initialized");
            return;
        }
        this.ba = mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(characteristic.getUuid());
        if (descriptor != null) {
            Log.m2i("write descriptor uuid -->" + characteristic.getUuid().toString());
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) {
            return null;
        }
        return mBluetoothGatt.getServices();
    }

    public boolean getRssiVal() {
        if (mBluetoothGatt == null) {
            return false;
        }
        return mBluetoothGatt.readRemoteRssi();
    }

    private void getGattServices(List<BluetoothGattService> gattServices) {
        ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
        if (gattServices != null) {
            ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList();
            mGattCharacteristics = new ArrayList();
            for (BluetoothGattService gattService : gattServices) {
                ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList();
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                ArrayList<BluetoothGattCharacteristic> charas = new ArrayList();
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    charas.add(gattCharacteristic);
                    gattCharacteristicGroupData.add(new HashMap());
                }
                mGattCharacteristics.add(charas);
                gattCharacteristicData.add(gattCharacteristicGroupData);
            }
            setCharacteristicNotificationAndReadValue(mGattCharacteristics);
        }
    }

    private void setCharacteristicNotificationAndReadValue(ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics2) {
        for (int i = 0; i < mGattCharacteristics2.size(); i++) {
            for (int j = 0; j < ((ArrayList) mGattCharacteristics2.get(i)).size(); j++) {
                BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) ((ArrayList) mGattCharacteristics2.get(i)).get(j);
                String uuid = characteristic.getUuid().toString();
                if (uuid.equals(SampleGattAttributes.FOR_SERIAL_PORT_READ)) {
                    this.FOR_SERIAL_PORT_READ_Characteristic = characteristic;
                    setCharacteristicNotification(characteristic, true);
                } else if (uuid.equals(SampleGattAttributes.SERIAL_PORT_WRITE)) {
                    this.SERIAL_PORT_WRITE_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.MAX_PACKET_SIZE)) {
                    this.MAX_PACKET_SIZE_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.NO_RESPONSE_MAX_PACKET_COUNT)) {
                    this.NO_RESPONSE_MAX_PACKET_COUNT_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.DEVICE_RECEIVED_PACKET_SEQUENCE)) {
                    this.DEVICE_RECEIVED_PACKET_SEQUENCE_Characteristic = characteristic;
                    setCharacteristicNotification(characteristic, true);
                } else if (uuid.equals(SampleGattAttributes.HOST_RECEIVED_PACKET_SEQUENCE)) {
                    this.HOST_RECEIVED_PACKET_SEQUENCE_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.PACKET_TIMEOUT)) {
                    this.PACKET_TIMEOUT_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.DEVICE_RECEIVED_ERROR_PACKET_SEQUENCE)) {
                    this.DEVICE_RECEIVED_ERROR_PACKET_SEQUENCE_Characteristic = characteristic;
                    setCharacteristicNotification(characteristic, true);
                } else if (uuid.equals(SampleGattAttributes.HOST_RECEIVED_ERROR_PACKET_SEQUENCE)) {
                    this.HOST_RECEIVED_ERROR_PACKET_SEQUENCE_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.DEVICE_CAN_RECEIVE_PACKET)) {
                    this.DEVICE_CAN_RECEIVE_PACKET_Characteristic = characteristic;
                    setCharacteristicNotification(characteristic, true);
                } else if (uuid.equals(SampleGattAttributes.HOST_CAN_RECEIVE_PACKET)) {
                    this.HOST_CAN_RECEIVE_PACKET_Characteristic = characteristic;
                } else if (uuid.equals(SampleGattAttributes.RESET_SEQUENCE)) {
                    this.RESET_SEQUENCE_Characteristic = characteristic;
                }
            }
        }
        startReadCharacteristic();
        this.serviceHnadler.postDelayed(this.readCharacteristicTimeoutRunnable, 3000);
    }

    public void wirte(byte[] data) {
        if (this.dp != null) {
            this.dp.sendDataToPeer(data);
        }
    }

    private boolean startReadCharacteristic() {
        try {
            if (this.mReadCharacteristicThread != null && this.mReadCharacteristicThread.isAlive()) {
                this.mReadCharacteristicThread.interrupt();
                this.mReadCharacteristicThread = null;
            }
            this.mReadCharacteristicThread = new readCharacteristicThread();
            this.mReadCharacteristicThread.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
