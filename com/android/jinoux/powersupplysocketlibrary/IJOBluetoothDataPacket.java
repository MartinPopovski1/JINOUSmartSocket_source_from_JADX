package com.android.jinoux.powersupplysocketlibrary;

public interface IJOBluetoothDataPacket {
    void didDataReceived(byte[] bArr, int i);

    void disconnect();

    JOBluetoothDataPacket init();

    boolean recvDataFromPeer(byte[] bArr);

    void recvDataPackeSequence(byte b, boolean z);

    void recvResetSequence();

    void reset();

    boolean sendDataToPeer(byte[] bArr);

    boolean writeData(byte[] bArr);

    boolean writeHostCanReceivePacket(byte[] bArr);

    boolean writeResetSequence(byte[] bArr);

    void writeResponsePacket(byte[] bArr, boolean z, boolean z2);
}
