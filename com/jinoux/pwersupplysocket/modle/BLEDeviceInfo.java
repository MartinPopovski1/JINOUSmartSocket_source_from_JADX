package com.jinoux.pwersupplysocket.modle;

public class BLEDeviceInfo {
    private String address;
    private byte[] checkKey;
    private int id;
    private String lastState;
    private String name;
    private String password;
    private String power;
    private String record;
    private String reset;
    private String serialNumber;
    private byte[] userKey;

    public BLEDeviceInfo(int id, String serialNumber, String name, String password, String address, byte[] userKey, byte[] checkKey, String record, String reset, String power, String lastState) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.password = password;
        this.address = address;
        this.userKey = userKey;
        this.checkKey = checkKey;
        this.record = record;
        this.reset = reset;
        this.power = power;
        this.lastState = lastState;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getUserKey() {
        return this.userKey;
    }

    public void setUserKey(byte[] userKey) {
        this.userKey = userKey;
    }

    public byte[] getCheckKey() {
        return this.checkKey;
    }

    public void setCheckKey(byte[] checkKey) {
        this.checkKey = checkKey;
    }

    public String getRecord() {
        return this.record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getReset() {
        return this.reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getPower() {
        return this.power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getLastState() {
        return this.lastState;
    }

    public void setLastState(String lastState) {
        this.lastState = lastState;
    }
}
