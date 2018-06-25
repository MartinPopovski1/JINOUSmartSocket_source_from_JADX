package com.android.jinoux.util;

import android.support.v4.view.MotionEventCompat;
import java.security.InvalidKeyException;

public class AesCipherAndInvCipher {
    public static final byte[] KEY = new byte[]{(byte) -123, (byte) 56, (byte) -34, (byte) -84, (byte) 62, (byte) -12, (byte) 72, (byte) -95, (byte) 89, (byte) 17, (byte) -37, (byte) -86, (byte) 122, (byte) 105, (byte) 28, (byte) -82};
    private static int Nb = 0;
    private static int blockSize = 4;
    private static byte[] ct;
    private static int dataLength;
    private static int mode;
    private static byte[] pt;

    private static byte[] Eecrypt(byte[] plainText, byte[] kb) throws InvalidKeyException {
        pt = plainText;
        dataLength = pt.length;
        mode = dataLength % blockSize;
        ct = new byte[(mode == 0 ? pt.length : ((dataLength / blockSize) * 4) + 32)];
        Nb = dataLength / blockSize;
        if (kb == null) {
            kb = KEY;
        }
        AEST a1 = new AEST(128, kb, Nb);
        if (mode == 0) {
            a1.Cipher(pt, ct);
        }
        return ct;
    }

    public static byte[] EecryptToBytes(byte[] plainText, byte[] kb) {
        byte[] data = new byte[plainText.length];
        int i = 0;
        while (i < plainText.length) {
            try {
                int j;
                byte[] e = new byte[16];
                for (j = 0; j < e.length; j++) {
                    e[j] = plainText[i + j];
                }
                byte[] ct = Eecrypt(e, kb);
                for (j = 0; j < ct.length; j++) {
                    data[i + j] = ct[j];
                }
                i += 16;
            } catch (InvalidKeyException e2) {
                e2.printStackTrace();
            }
        }
        return data;
    }

    public static byte[] Decryptbyte(byte[] ct, byte[] kb) {
        byte[] data = new byte[ct.length];
        if (mode == 0) {
            byte[] output = new byte[dataLength];
            if (kb == null) {
                kb = KEY;
            }
            AEST a = new AEST(128, kb, Nb);
            for (int i = 0; i < ct.length; i += 16) {
                int j;
                byte[] e = new byte[16];
                for (j = 0; j < e.length; j++) {
                    e[j] = ct[i + j];
                }
                a.Invcipher(e, output);
                for (j = 0; j < output.length; j++) {
                    data[i + j] = output[j];
                }
            }
        }
        return data;
    }

    public static String Decrypt(byte[] ct, byte[] kb) {
        String s = "";
        if (mode == 0) {
            byte[] output = new byte[dataLength];
            new AEST(128, kb, Nb).Invcipher(ct, output);
            for (byte byteToHexString : output) {
                s = new StringBuilder(String.valueOf(s)).append(byteToHexString(byteToHexString)).append(" ").toString();
            }
        }
        return s;
    }

    public static String byteToHexString(byte buf) {
        byte[] src = new byte[]{buf};
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & MotionEventCompat.ACTION_MASK);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString() + " ";
    }
}
