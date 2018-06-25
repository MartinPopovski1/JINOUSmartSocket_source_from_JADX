package com.jinoux.pwersupplysocket.modle;

import android.bluetooth.BluetoothDevice;

public class ScanDeviceInfo {
    private BluetoothDevice device;
    private boolean isPaired;
    private byte[] manufacturerData;
    private int rssi;
    private byte[] scanRecord;

    public ScanDeviceInfo(BluetoothDevice device, byte[] scanRecord, int rssi, boolean isPaired, byte[] manufacturerData) {
        this.device = device;
        this.scanRecord = scanRecord;
        this.rssi = rssi;
        this.isPaired = isPaired;
        this.manufacturerData = manufacturerData;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public byte[] getScanRecord() {
        return this.scanRecord;
    }

    public void setScanRecord(byte[] scanRecord) {
        this.scanRecord = scanRecord;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public boolean isPaired() {
        return this.isPaired;
    }

    public void setPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }

    public byte[] getManufacturerData() {
        return this.manufacturerData;
    }

    public void setManufacturerData(byte[] manufacturerData) {
        this.manufacturerData = manufacturerData;
    }
}
