package com.android.jinoux.powersupplysocketlibrary;

import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import com.android.jinoux.util.Log;
import com.android.jinoux.util.Tools;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JOBluetoothDataPacket implements IJOBluetoothDataPacket {
    private static final boolean DEBUG_LOG = true;
    private static final boolean DEBUG_LOG_DATA = true;
    static final boolean DISCONNECT_WHEN_MULTI_PACKET_TIMEOUT = true;
    static final boolean MULTI_PACKET_ONE_RESPONSE = false;
    private int DEFAULT_MAX_PACKET_SIZE = 20;
    private int DEFAULT_MAX_PACKET_TIMEOUT = 3;
    private int DEFAULT_NO_RESPONSE_MAX_PACKET_COUNT = 10;
    private long DEFAULT_SEND_PACKET_INTERVAL = 200;
    private int MAX_BUFFER_PACKET_COUNT = 100;
    List<byte[]> arrayWaitResponseData = null;
    List<byte[]> arrayWaitSendData = null;
    private boolean bPeerCanReceive = false;
    Timer dataTimer = null;
    Date lastRecvResetSequenceDate = null;
    Date lastSendDate = null;
    Timer multiPacketResponseTimeoutTimer = null;
    int nContinuePacketTimeoutCount = 0;
    int nCurrNotResponsePacketCount = 0;
    byte nLastPacketTimeoutSeq = (byte) 0;
    public int nMaxPacketSize = 0;
    int nNextSendDataIndex = 0;
    byte nNextSendPacketSequence = (byte) 0;
    byte nNextWantRecvPacketSequence = (byte) 0;
    public int nNoResponseAllowMaxPacketCount = 0;
    int nNoResponseCurrAllowMaxPacketCount = 0;
    public int nPacketTimeout = 0;
    int nWaitResendPacketCount = 0;
    int nWaitSendDataCount = 0;
    SendDataTimer sdt;
    private Handler serviceHnadler;
    TimerTask tt = new C00551();

    class C00551 extends TimerTask {
        C00551() {
        }

        public void run() {
            JOBluetoothDataPacket.this.writeBufferResonsePacket(null);
        }
    }

    class SendDataTimer extends TimerTask {
        SendDataTimer() {
        }

        public void run() {
            if (JOBluetoothDataPacket.this.arrayWaitSendData != null && JOBluetoothDataPacket.this.arrayWaitResponseData != null) {
                if (JOBluetoothDataPacket.this.arrayWaitSendData.size() == 0 && JOBluetoothDataPacket.this.arrayWaitResponseData.size() == 0) {
                    if (JOBluetoothDataPacket.this.dataTimer != null) {
                        JOBluetoothDataPacket.this.dataTimer.cancel();
                        JOBluetoothDataPacket.this.dataTimer = null;
                    }
                    if (JOBluetoothDataPacket.this.sdt != null) {
                        JOBluetoothDataPacket.this.sdt.cancel();
                        JOBluetoothDataPacket.this.sdt = null;
                        return;
                    }
                    return;
                }
                JOBluetoothDataPacket.this.packetTimeout();
                JOBluetoothDataPacket.this.sendDataPacket();
                if (JOBluetoothDataPacket.this.dataTimer != null) {
                    if (JOBluetoothDataPacket.this.sdt != null) {
                        JOBluetoothDataPacket.this.sdt.cancel();
                    }
                    JOBluetoothDataPacket.this.sdt = new SendDataTimer();
                    JOBluetoothDataPacket.this.dataTimer.schedule(JOBluetoothDataPacket.this.sdt, JOBluetoothDataPacket.this.DEFAULT_SEND_PACKET_INTERVAL);
                }
            }
        }
    }

    public JOBluetoothDataPacket(Handler serviceHnadler) {
        init();
        this.serviceHnadler = serviceHnadler;
    }

    public void initN(int readMaxPacketSize, int readNoResponseMaxPacketCount, int readPacketTimeout) {
        this.DEFAULT_MAX_PACKET_SIZE = readMaxPacketSize;
        this.DEFAULT_NO_RESPONSE_MAX_PACKET_COUNT = readNoResponseMaxPacketCount;
        this.DEFAULT_MAX_PACKET_TIMEOUT = readPacketTimeout;
        this.nMaxPacketSize = this.DEFAULT_MAX_PACKET_SIZE;
        this.nNoResponseAllowMaxPacketCount = this.DEFAULT_NO_RESPONSE_MAX_PACKET_COUNT;
        this.nNoResponseCurrAllowMaxPacketCount = this.DEFAULT_NO_RESPONSE_MAX_PACKET_COUNT;
        this.nPacketTimeout = this.DEFAULT_MAX_PACKET_TIMEOUT;
    }

    public JOBluetoothDataPacket init() {
        this.arrayWaitSendData = new ArrayList();
        this.arrayWaitResponseData = new ArrayList();
        this.dataTimer = null;
        this.nNextSendDataIndex = 0;
        this.nNextSendPacketSequence = (byte) 0;
        this.nNextWantRecvPacketSequence = (byte) 0;
        this.nMaxPacketSize = this.DEFAULT_MAX_PACKET_SIZE;
        this.nNoResponseAllowMaxPacketCount = this.DEFAULT_NO_RESPONSE_MAX_PACKET_COUNT;
        this.nNoResponseCurrAllowMaxPacketCount = this.DEFAULT_NO_RESPONSE_MAX_PACKET_COUNT;
        this.nPacketTimeout = this.DEFAULT_MAX_PACKET_TIMEOUT;
        this.bPeerCanReceive = true;
        this.lastRecvResetSequenceDate = null;
        this.nWaitResendPacketCount = 0;
        this.nContinuePacketTimeoutCount = 0;
        this.nLastPacketTimeoutSeq = (byte) -1;
        return this;
    }

    public void reset() {
        if (this.dataTimer != null) {
            this.dataTimer.cancel();
            this.dataTimer = null;
        }
        if (this.sdt != null) {
            this.sdt.cancel();
            this.sdt = null;
        }
        this.arrayWaitSendData = new ArrayList();
        this.arrayWaitResponseData = new ArrayList();
        this.nNextSendDataIndex = 0;
        this.nNextSendPacketSequence = (byte) 0;
        this.nNoResponseAllowMaxPacketCount = 0;
        this.bPeerCanReceive = true;
        this.nNoResponseCurrAllowMaxPacketCount = this.nNoResponseAllowMaxPacketCount;
        this.nWaitResendPacketCount = 0;
        this.nContinuePacketTimeoutCount = 0;
        this.nLastPacketTimeoutSeq = (byte) -1;
    }

    public void sendResetSequence() {
        writeResetSequence(new byte[]{(byte) 1});
        this.nNextSendPacketSequence = (byte) 0;
        this.arrayWaitResponseData = new ArrayList();
        this.lastSendDate = null;
    }

    public boolean sendDataToPeer(byte[] data) {
        Log.m2i("sendDataToPeer. length=" + data.length);
        if (this.arrayWaitSendData.size() >= this.MAX_BUFFER_PACKET_COUNT) {
            Log.m2i("No more buffer. we don't send it.");
            return false;
        }
        byte[] newData;
        int nLength;
        byte[] src = data;
        int nIndex = 0;
        if (this.arrayWaitSendData.size() > 0) {
            newData = (byte[]) this.arrayWaitSendData.get(this.arrayWaitSendData.size() - 1);
            if (newData != null && newData.length < this.nMaxPacketSize) {
                nIndex = newData.length - 1;
                nLength = this.nMaxPacketSize - newData.length;
                if (nLength > data.length) {
                    nLength = data.length;
                }
                byte[] temp = new byte[(newData.length + data.length)];
                System.arraycopy(newData, 0, temp, 0, newData.length);
                System.arraycopy(data, 0, temp, newData.length, data.length);
                System.arraycopy(data, 0, temp, nIndex, nLength);
                nIndex = nLength;
            }
        }
        while (nIndex < data.length) {
            nLength = this.nMaxPacketSize - 2;
            if (data.length - nIndex < nLength) {
                nLength = data.length - nIndex;
            }
            newData = new byte[(nLength + 2)];
            System.arraycopy(src, nIndex, newData, 1, nLength);
            this.arrayWaitSendData.add(newData);
            nIndex += nLength;
        }
        sendDataPacket();
        if (this.dataTimer != null) {
            this.dataTimer.cancel();
            this.dataTimer = null;
        }
        if (this.sdt != null) {
            this.sdt.cancel();
        }
        if (this.dataTimer == null) {
            this.dataTimer = new Timer();
        }
        this.sdt = new SendDataTimer();
        this.dataTimer.schedule(this.sdt, this.DEFAULT_SEND_PACKET_INTERVAL);
        return true;
    }

    void sendDataPacket() {
        if (this.bPeerCanReceive && this.arrayWaitSendData.size() > 0 && this.arrayWaitResponseData.size() < this.nNoResponseCurrAllowMaxPacketCount) {
            byte[] buffer = (byte[]) this.arrayWaitSendData.get(0);
            if (buffer == null) {
                for (int i = 1; i < this.arrayWaitSendData.size(); i++) {
                    buffer = (byte[]) this.arrayWaitSendData.get(i);
                    if (buffer != null) {
                        break;
                    }
                }
            }
            if (buffer == null) {
                buffer = new byte[18];
            }
            buffer[0] = this.nNextSendPacketSequence;
            buffer[buffer.length - 1] = checksum(buffer, buffer.length - 1);
            if (writeData(buffer)) {
                Log.m2i("sendDataPacket. 序号:0x" + Tools.byteToHexString(this.nNextSendPacketSequence) + " 长度:" + buffer.length);
                this.lastSendDate = new Date();
                if (this.arrayWaitSendData != null && this.arrayWaitSendData.size() > 0) {
                    this.arrayWaitSendData.remove(0);
                }
                this.nNextSendPacketSequence = (byte) (this.nNextSendPacketSequence + 1);
                if (this.nNextSendPacketSequence >= (byte) -1) {
                    this.nNextSendPacketSequence = (byte) 0;
                }
                if (this.nWaitResendPacketCount > 0) {
                    this.nWaitResendPacketCount--;
                }
                this.arrayWaitResponseData.add(buffer);
            }
        }
    }

    void moveWaitResponseDataToSendData(int nIndex) {
        byte[] data = null;
        try {
            this.nWaitResendPacketCount += this.arrayWaitResponseData.size();
            while (this.arrayWaitResponseData.size() > nIndex) {
                if (this.arrayWaitResponseData.size() >= 1) {
                    data = (byte[]) this.arrayWaitResponseData.get(this.arrayWaitResponseData.size() - 1);
                    this.arrayWaitResponseData.remove(this.arrayWaitResponseData.size() - 1);
                    this.arrayWaitSendData.add(0, data);
                }
            }
            if (data != null && data.length > 0) {
                this.nNextSendPacketSequence = data[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void packetTimeout() {
        if (this.lastSendDate == null || this.lastRecvResetSequenceDate == null) {
            packetTimeoutReset();
            return;
        }
        int timeInterval = ((int) (this.lastRecvResetSequenceDate.getTime() - this.lastSendDate.getTime())) * 1000;
        if (timeInterval < 0 || timeInterval / 1000 > this.nPacketTimeout) {
            packetTimeoutReset();
        }
    }

    private void packetTimeoutReset() {
        Log.m2i("接收数据超时 次数:" + this.nContinuePacketTimeoutCount);
        moveWaitResponseDataToSendData(0);
        this.nNoResponseCurrAllowMaxPacketCount = 1;
        if (this.bPeerCanReceive) {
            if (this.nNextSendDataIndex != this.nLastPacketTimeoutSeq) {
                this.nLastPacketTimeoutSeq = (byte) this.nNextSendDataIndex;
            } else {
                this.nContinuePacketTimeoutCount++;
            }
            if (this.nContinuePacketTimeoutCount > this.nNoResponseAllowMaxPacketCount * 2) {
                disconnect();
            }
        }
    }

    private byte checksum(byte[] buffer, int length) {
        byte nSum = (byte) 0;
        for (int i = 0; i < length; i++) {
            nSum = (byte) (buffer[i] + nSum);
        }
        return nSum;
    }

    int getNWaitSendDataCount() {
        int i;
        int nCount = 0;
        for (i = 0; i < this.arrayWaitResponseData.size(); i++) {
            nCount += ((byte[]) this.arrayWaitResponseData.get(i)).length - 2;
        }
        for (i = 0; i < this.arrayWaitSendData.size(); i++) {
            nCount += ((byte[]) this.arrayWaitResponseData.get(i)).length - 2;
        }
        return nCount;
    }

    public boolean recvDataFromPeer(byte[] data) {
        Log.m2i("recvDataFromPeer. length=" + data.length);
        if (data.length >= 2) {
            byte[] buffer = data;
            byte nCurrChecksum = checksum(buffer, data.length - 1);
            Log.m2i("接收到的数据 序号:[" + Tools.byteToHexString(buffer[0]) + "]/checksum[" + Tools.byteToHexString(buffer[data.length - 1]) + "]. 等待的数据 序号:[" + Tools.byteToHexString(this.nNextWantRecvPacketSequence) + "]/checksum[" + Tools.byteToHexString(nCurrChecksum) + "]");
            if (buffer[data.length - 1] != nCurrChecksum) {
                writeResponsePacket(new byte[]{this.nNextWantRecvPacketSequence}, true, true);
                Log.m2i("序号不正确,直接应答错误");
                return true;
            } else if (buffer[0] == this.nNextWantRecvPacketSequence) {
                didDataReceived(buffer, data.length - 2);
                this.nNextWantRecvPacketSequence = (byte) (this.nNextWantRecvPacketSequence + 1);
                if (this.nNextWantRecvPacketSequence == (byte) -1) {
                    this.nNextWantRecvPacketSequence = (byte) 0;
                }
                writeResponsePacket(new byte[]{buffer[0]}, false, false);
                return true;
            } else {
                Log.m2i("序号不是期望的,丢弃这个数据");
                if (IsHistoryPacketSeq(buffer[0])) {
                    writeResponsePacket(new byte[]{buffer[0]}, false, false);
                    Log.m2i("重复序号");
                    return true;
                }
                byte[] respPacket = new byte[]{this.nNextWantRecvPacketSequence};
                Log.m2i("错误序号");
                writeResponsePacket(respPacket, true, true);
                return true;
            }
        }
        Log.m2i("数据长度小于2,直接抛弃数据");
        return false;
    }

    boolean IsHistoryPacketSeq(byte nRecvedSeq) {
        if (((this.nNextWantRecvPacketSequence + MotionEventCompat.ACTION_MASK) - nRecvedSeq) % MotionEventCompat.ACTION_MASK < this.nNoResponseAllowMaxPacketCount) {
            return true;
        }
        return false;
    }

    public void recvDataPackeSequence(byte nSeq, boolean error) {
        this.lastRecvResetSequenceDate = new Date();
        Log.m2i("recvDataPackeSequence. nSeq=0x" + Tools.byteToHexString(nSeq) + ". error=" + error);
        int nDataIndex = 0;
        while (nDataIndex < this.arrayWaitResponseData.size()) {
            byte[] data = (byte[]) this.arrayWaitResponseData.get(nDataIndex);
            if (data.length != 0) {
                if (data[0] == nSeq) {
                    break;
                }
                nDataIndex++;
            } else {
                return;
            }
        }
        if (nDataIndex >= this.arrayWaitResponseData.size()) {
            Log.m2i("应答序号错误. arrayWaitResponseData.size --> " + this.arrayWaitResponseData.size() + " 中没有找到该序号");
            Boolean bIsResendPacketSeq = Boolean.valueOf(false);
            if (this.nWaitResendPacketCount > 0) {
                byte nFirstResendSeq = this.nNextSendPacketSequence;
                byte nLastResendSeq = nFirstResendSeq + this.nWaitResendPacketCount;
                byte nRecvdSeq = nSeq;
                if (nRecvdSeq < nFirstResendSeq) {
                    nRecvdSeq = nSeq + MotionEventCompat.ACTION_MASK;
                }
                if (nFirstResendSeq <= nRecvdSeq && nRecvdSeq <= nLastResendSeq) {
                    bIsResendPacketSeq = Boolean.valueOf(true);
                    Log.m2i("\t\t the Sequence in resend array.");
                }
                if (error && !bIsResendPacketSeq.booleanValue()) {
                    Log.m2i("\t (" + this.nWaitResendPacketCount + "-" + Tools.byteToHexString((byte) nFirstResendSeq) + "-" + Tools.byteToHexString((byte) nRecvdSeq) + "-" + Tools.byteToHexString((byte) nLastResendSeq) + ")");
                }
            }
            if (error && !bIsResendPacketSeq.booleanValue()) {
                Log.m2i("\t we send reset sequence message.");
                sendResetSequence();
                return;
            }
            return;
        }
        Log.m2i("找到了序号对应的数据包 wait response array length[" + this.arrayWaitResponseData.size() + "]. recv seq at[" + nDataIndex + "].");
        if (error) {
            moveWaitResponseDataToSendData(0);
            this.nNoResponseCurrAllowMaxPacketCount = 1;
            return;
        }
        for (int i = 0; i <= nDataIndex; i++) {
            this.arrayWaitResponseData.remove(0);
        }
        this.nNoResponseCurrAllowMaxPacketCount += 2;
        if (this.nNoResponseCurrAllowMaxPacketCount >= this.nNoResponseAllowMaxPacketCount) {
            this.nNoResponseCurrAllowMaxPacketCount = this.nNoResponseAllowMaxPacketCount;
        }
        this.nContinuePacketTimeoutCount = 0;
    }

    void setNNoResponseAllowMaxPacketCount(int inNoResponseAllowMaxPacketCount) {
        this.nNoResponseAllowMaxPacketCount = inNoResponseAllowMaxPacketCount;
        if (inNoResponseAllowMaxPacketCount > this.nNoResponseAllowMaxPacketCount) {
            inNoResponseAllowMaxPacketCount = this.nNoResponseAllowMaxPacketCount;
        }
    }

    public void setBPeerCanReceive(boolean bbPeerCanReceive) {
        this.bPeerCanReceive = bbPeerCanReceive;
        Log.m2i("setBPeerCanReceive (" + bbPeerCanReceive + ")");
    }

    public void recvResetSequence() {
        Log.m2i("recv ResetSequence event.");
        boolean bNeedReset = true;
        if (!(this.lastRecvResetSequenceDate == null || this.lastSendDate == null)) {
            int timeInterval = ((int) (this.lastRecvResetSequenceDate.getTime() - this.lastSendDate.getTime())) * 1000;
            if (timeInterval >= 0 && timeInterval < this.nPacketTimeout) {
                bNeedReset = false;
            }
        }
        if (bNeedReset) {
            this.lastRecvResetSequenceDate = new Date();
            this.nNextWantRecvPacketSequence = (byte) 0;
            return;
        }
        Log.m2i("discard reset sequence event.");
    }

    void writeBufferResonsePacket(Timer timer) {
        if (!(this.multiPacketResponseTimeoutTimer == null || timer.equals(this.multiPacketResponseTimeoutTimer))) {
            this.multiPacketResponseTimeoutTimer.purge();
            this.multiPacketResponseTimeoutTimer.cancel();
            this.multiPacketResponseTimeoutTimer = null;
        }
        byte nSeq = (byte) (this.nNextWantRecvPacketSequence - 1);
        if (nSeq == (byte) -1) {
            nSeq = (byte) -2;
        }
        byte[] respPacket = new byte[]{nSeq};
        this.serviceHnadler.obtainMessage(5, 1, 0, respPacket).sendToTarget();
        this.nCurrNotResponsePacketCount = 0;
        Log.m2i("writeBufferResonsePacket data(" + respPacket + ").timer(" + timer + ")");
    }

    public void writeResponsePacket(byte[] respPacket, boolean immedialy, boolean error) {
        int i;
        Log.m2i("writeResponsePacket data(" + Tools.bytesToHexString(respPacket) + "). error:" + error);
        Handler handler = this.serviceHnadler;
        if (error) {
            i = 0;
        } else {
            i = 1;
        }
        handler.obtainMessage(5, i, 0, respPacket).sendToTarget();
    }

    public boolean writeHostCanReceivePacket(byte[] data) {
        this.serviceHnadler.obtainMessage(0, data).sendToTarget();
        return true;
    }

    public boolean writeResetSequence(byte[] data) {
        this.serviceHnadler.obtainMessage(1, data).sendToTarget();
        return true;
    }

    public void disconnect() {
        reset();
        this.serviceHnadler.sendEmptyMessage(2);
    }

    public void didDataReceived(byte[] buffer, int length) {
        byte[] data = new byte[length];
        System.arraycopy(buffer, 1, data, 0, length);
        this.serviceHnadler.obtainMessage(3, data).sendToTarget();
    }

    public boolean writeData(byte[] data) {
        this.lastSendDate = new Date();
        this.serviceHnadler.obtainMessage(4, data).sendToTarget();
        return true;
    }
}
