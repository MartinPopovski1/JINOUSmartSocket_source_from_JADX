package com.android.jinoux.powersupplysocketlibrary;

import java.util.HashMap;

public class SampleGattAttributes {
    public static final String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static final String DEVICE_CAN_RECEIVE_PACKET = "0000b35A-0000-1000-8000-00805f9b34fb";
    public static final String DEVICE_RECEIVED_ERROR_PACKET_SEQUENCE = "0000b358-0000-1000-8000-00805f9b34fb";
    public static final String DEVICE_RECEIVED_PACKET_SEQUENCE = "0000b355-0000-1000-8000-00805f9b34fb";
    public static final String FOR_SERIAL_PORT_READ = "0000b351-0000-1000-8000-00805f9b34fb";
    public static final String HEART_RATE_MEASUREMENT = "0000C004-0000-1000-8000-00805f9b34fb";
    public static final String HOST_CAN_RECEIVE_PACKET = "0000b35B-0000-1000-8000-00805f9b34fb";
    public static final String HOST_RECEIVED_ERROR_PACKET_SEQUENCE = "0000b359-0000-1000-8000-00805f9b34fb";
    public static final String HOST_RECEIVED_PACKET_SEQUENCE = "0000b356-0000-1000-8000-00805f9b34fb";
    public static final String MAX_PACKET_SIZE = "0000b353-0000-1000-8000-00805f9b34fb";
    public static final String NO_RESPONSE_MAX_PACKET_COUNT = "0000b354-0000-1000-8000-00805f9b34fb";
    public static final String PACKET_TIMEOUT = "0000b357-0000-1000-8000-00805f9b34fb";
    public static final String RESET_SEQUENCE = "0000b35C-0000-1000-8000-00805f9b34fb";
    public static final String SERIAL_PORT = "0000b350-0000-1000-8000-00805f9b34fb";
    public static final String SERIAL_PORT_WRITE = "0000b352-0000-1000-8000-00805f9b34fb";
    private static HashMap<String, String> attributes = new HashMap();

    static {
        attributes.put("0000fff00000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = (String) attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
