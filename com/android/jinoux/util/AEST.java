package com.android.jinoux.util;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import java.lang.reflect.Array;

public class AEST {
    private int Nb;
    private int Nk;
    private int[] Sbox;
    private byte[][] State;
    private int[] iSbox;
    private byte[] key;
    private byte[][][] f1w = ((byte[][][]) Array.newInstance(Byte.TYPE, new int[]{11, 4, 4}));

    public AEST(int keySize, byte[] keyBytes, int Nb) {
        SetNbNkNr(keySize, Nb);
        this.key = new byte[(this.Nk * 4)];
        this.key = keyBytes;
        BuildSbox();
        BuildInvSbox();
        KeyExpansion();
    }

    private void SetNbNkNr(int keysize, int Nb) {
        this.Nb = Nb;
        switch (keysize) {
            case 128:
                this.Nk = 4;
                return;
            case KEYSIZE.Bit192 /*192*/:
                this.Nk = 6;
                return;
            case 256:
                this.Nk = 8;
                return;
            default:
                return;
        }
    }

    private void BuildSbox() {
        int[] iArr = new int[256];
        iArr[0] = 99;
        iArr[1] = 124;
        iArr[2] = 119;
        iArr[3] = 123;
        iArr[4] = -14;
        iArr[5] = 107;
        iArr[6] = 111;
        iArr[7] = -59;
        iArr[8] = 48;
        iArr[9] = 1;
        iArr[10] = 103;
        iArr[11] = 43;
        iArr[12] = -2;
        iArr[13] = -41;
        iArr[14] = -85;
        iArr[15] = 118;
        iArr[16] = -54;
        iArr[17] = -126;
        iArr[18] = -55;
        iArr[19] = 125;
        iArr[20] = -6;
        iArr[21] = 89;
        iArr[22] = 71;
        iArr[23] = -16;
        iArr[24] = -83;
        iArr[25] = -44;
        iArr[26] = -94;
        iArr[27] = -81;
        iArr[28] = -100;
        iArr[29] = -92;
        iArr[30] = 114;
        iArr[31] = -64;
        iArr[32] = -73;
        iArr[33] = -3;
        iArr[34] = -109;
        iArr[35] = 38;
        iArr[36] = 54;
        iArr[37] = 63;
        iArr[38] = -9;
        iArr[39] = -52;
        iArr[40] = 52;
        iArr[41] = -91;
        iArr[42] = -27;
        iArr[43] = -15;
        iArr[44] = 113;
        iArr[45] = -40;
        iArr[46] = 49;
        iArr[47] = 21;
        iArr[48] = 4;
        iArr[49] = -57;
        iArr[50] = 35;
        iArr[51] = -61;
        iArr[52] = 24;
        iArr[53] = -106;
        iArr[54] = 5;
        iArr[55] = -102;
        iArr[56] = 7;
        iArr[57] = 18;
        iArr[58] = -128;
        iArr[59] = -30;
        iArr[60] = -21;
        iArr[61] = 39;
        iArr[62] = -78;
        iArr[63] = 117;
        iArr[64] = 9;
        iArr[65] = -125;
        iArr[66] = 44;
        iArr[67] = 26;
        iArr[68] = 27;
        iArr[69] = 110;
        iArr[70] = 90;
        iArr[71] = -96;
        iArr[72] = 82;
        iArr[73] = 59;
        iArr[74] = -42;
        iArr[75] = -77;
        iArr[76] = 41;
        iArr[77] = -29;
        iArr[78] = 47;
        iArr[79] = -124;
        iArr[80] = 83;
        iArr[81] = -47;
        iArr[83] = -19;
        iArr[84] = 32;
        iArr[85] = -4;
        iArr[86] = -79;
        iArr[87] = 91;
        iArr[88] = 106;
        iArr[89] = -53;
        iArr[90] = -66;
        iArr[91] = 57;
        iArr[92] = 74;
        iArr[93] = 76;
        iArr[94] = 88;
        iArr[95] = -49;
        iArr[96] = -48;
        iArr[97] = -17;
        iArr[98] = -86;
        iArr[99] = -5;
        iArr[100] = 67;
        iArr[101] = 77;
        iArr[102] = 51;
        iArr[103] = -123;
        iArr[104] = 69;
        iArr[105] = -7;
        iArr[106] = 2;
        iArr[107] = TransportMediator.KEYCODE_MEDIA_PAUSE;
        iArr[108] = 80;
        iArr[109] = 60;
        iArr[110] = -97;
        iArr[111] = -88;
        iArr[112] = 81;
        iArr[113] = -93;
        iArr[114] = 64;
        iArr[115] = -113;
        iArr[116] = -110;
        iArr[117] = -99;
        iArr[118] = 56;
        iArr[119] = -11;
        iArr[120] = -68;
        iArr[121] = -74;
        iArr[122] = -38;
        iArr[123] = 33;
        iArr[124] = 16;
        iArr[125] = -1;
        iArr[TransportMediator.KEYCODE_MEDIA_PLAY] = -13;
        iArr[TransportMediator.KEYCODE_MEDIA_PAUSE] = -46;
        iArr[128] = -51;
        iArr[129] = 12;
        iArr[TransportMediator.KEYCODE_MEDIA_RECORD] = 19;
        iArr[131] = -20;
        iArr[132] = 95;
        iArr[133] = -105;
        iArr[134] = 68;
        iArr[135] = 23;
        iArr[136] = -60;
        iArr[137] = -89;
        iArr[138] = TransportMediator.KEYCODE_MEDIA_PLAY;
        iArr[139] = 61;
        iArr[140] = 100;
        iArr[141] = 93;
        iArr[142] = 25;
        iArr[143] = 115;
        iArr[144] = 96;
        iArr[145] = -127;
        iArr[146] = 79;
        iArr[147] = -36;
        iArr[148] = 34;
        iArr[149] = 42;
        iArr[150] = -112;
        iArr[151] = -120;
        iArr[152] = 70;
        iArr[153] = -18;
        iArr[154] = -72;
        iArr[155] = 20;
        iArr[156] = -34;
        iArr[157] = 94;
        iArr[158] = 11;
        iArr[159] = -37;
        iArr[160] = -32;
        iArr[161] = 50;
        iArr[162] = 58;
        iArr[163] = 10;
        iArr[164] = 73;
        iArr[165] = 6;
        iArr[166] = 36;
        iArr[167] = 92;
        iArr[168] = -62;
        iArr[169] = -45;
        iArr[170] = -84;
        iArr[171] = 98;
        iArr[172] = -111;
        iArr[173] = -107;
        iArr[174] = -28;
        iArr[175] = 121;
        iArr[176] = -25;
        iArr[177] = -56;
        iArr[178] = 55;
        iArr[179] = 109;
        iArr[180] = -115;
        iArr[181] = -43;
        iArr[182] = 78;
        iArr[183] = -87;
        iArr[184] = 108;
        iArr[185] = 86;
        iArr[186] = -12;
        iArr[187] = -22;
        iArr[188] = 101;
        iArr[189] = 122;
        iArr[190] = -82;
        iArr[191] = 8;
        iArr[KEYSIZE.Bit192] = -70;
        iArr[193] = 120;
        iArr[194] = 37;
        iArr[195] = 46;
        iArr[196] = 28;
        iArr[197] = -90;
        iArr[198] = -76;
        iArr[199] = -58;
        iArr[200] = -24;
        iArr[201] = -35;
        iArr[202] = 116;
        iArr[203] = 31;
        iArr[204] = 75;
        iArr[205] = -67;
        iArr[206] = -117;
        iArr[207] = -118;
        iArr[208] = 112;
        iArr[209] = 62;
        iArr[210] = -75;
        iArr[211] = 102;
        iArr[212] = 72;
        iArr[213] = 3;
        iArr[214] = -10;
        iArr[215] = 14;
        iArr[216] = 97;
        iArr[217] = 53;
        iArr[218] = 87;
        iArr[219] = -71;
        iArr[220] = -122;
        iArr[221] = -63;
        iArr[222] = 29;
        iArr[223] = -98;
        iArr[224] = -31;
        iArr[225] = -8;
        iArr[226] = -104;
        iArr[227] = 17;
        iArr[228] = 105;
        iArr[229] = -39;
        iArr[230] = -114;
        iArr[231] = -108;
        iArr[232] = -101;
        iArr[233] = 30;
        iArr[234] = -121;
        iArr[235] = -23;
        iArr[236] = -50;
        iArr[237] = 85;
        iArr[238] = 40;
        iArr[239] = -33;
        iArr[240] = -116;
        iArr[241] = -95;
        iArr[242] = -119;
        iArr[243] = 13;
        iArr[244] = -65;
        iArr[245] = -26;
        iArr[246] = 66;
        iArr[247] = 104;
        iArr[248] = 65;
        iArr[249] = -103;
        iArr[250] = 45;
        iArr[251] = 15;
        iArr[252] = -80;
        iArr[253] = 84;
        iArr[254] = -69;
        iArr[MotionEventCompat.ACTION_MASK] = 22;
        this.Sbox = iArr;
    }

    private void BuildInvSbox() {
        int[] iArr = new int[256];
        iArr[0] = 82;
        iArr[1] = 9;
        iArr[2] = 106;
        iArr[3] = -43;
        iArr[4] = 48;
        iArr[5] = 54;
        iArr[6] = -91;
        iArr[7] = 56;
        iArr[8] = -65;
        iArr[9] = 64;
        iArr[10] = -93;
        iArr[11] = -98;
        iArr[12] = -127;
        iArr[13] = -13;
        iArr[14] = -41;
        iArr[15] = -5;
        iArr[16] = 124;
        iArr[17] = -29;
        iArr[18] = 57;
        iArr[19] = -126;
        iArr[20] = -101;
        iArr[21] = 47;
        iArr[22] = -1;
        iArr[23] = -121;
        iArr[24] = 52;
        iArr[25] = -114;
        iArr[26] = 67;
        iArr[27] = 68;
        iArr[28] = -60;
        iArr[29] = -34;
        iArr[30] = -23;
        iArr[31] = -53;
        iArr[32] = 84;
        iArr[33] = 123;
        iArr[34] = -108;
        iArr[35] = 50;
        iArr[36] = -90;
        iArr[37] = -62;
        iArr[38] = 35;
        iArr[39] = 61;
        iArr[40] = -18;
        iArr[41] = 76;
        iArr[42] = -107;
        iArr[43] = 11;
        iArr[44] = 66;
        iArr[45] = -6;
        iArr[46] = -61;
        iArr[47] = 78;
        iArr[48] = 8;
        iArr[49] = 46;
        iArr[50] = -95;
        iArr[51] = 102;
        iArr[52] = 40;
        iArr[53] = -39;
        iArr[54] = 36;
        iArr[55] = -78;
        iArr[56] = 118;
        iArr[57] = 91;
        iArr[58] = -94;
        iArr[59] = 73;
        iArr[60] = 109;
        iArr[61] = -117;
        iArr[62] = -47;
        iArr[63] = 37;
        iArr[64] = 114;
        iArr[65] = -8;
        iArr[66] = -10;
        iArr[67] = 100;
        iArr[68] = -122;
        iArr[69] = 104;
        iArr[70] = -104;
        iArr[71] = 22;
        iArr[72] = -44;
        iArr[73] = -92;
        iArr[74] = 92;
        iArr[75] = -52;
        iArr[76] = 93;
        iArr[77] = 101;
        iArr[78] = -74;
        iArr[79] = -110;
        iArr[80] = 108;
        iArr[81] = 112;
        iArr[82] = 72;
        iArr[83] = 80;
        iArr[84] = -3;
        iArr[85] = -19;
        iArr[86] = -71;
        iArr[87] = -38;
        iArr[88] = 94;
        iArr[89] = 21;
        iArr[90] = 70;
        iArr[91] = 87;
        iArr[92] = -89;
        iArr[93] = -115;
        iArr[94] = -99;
        iArr[95] = -124;
        iArr[96] = -112;
        iArr[97] = -40;
        iArr[98] = -85;
        iArr[100] = -116;
        iArr[101] = -68;
        iArr[102] = -45;
        iArr[103] = 10;
        iArr[104] = -9;
        iArr[105] = -28;
        iArr[106] = 88;
        iArr[107] = 5;
        iArr[108] = -72;
        iArr[109] = -77;
        iArr[110] = 69;
        iArr[111] = 6;
        iArr[112] = -48;
        iArr[113] = 44;
        iArr[114] = 30;
        iArr[115] = -113;
        iArr[116] = -54;
        iArr[117] = 63;
        iArr[118] = 15;
        iArr[119] = 2;
        iArr[120] = -63;
        iArr[121] = -81;
        iArr[122] = -67;
        iArr[123] = 3;
        iArr[124] = 1;
        iArr[125] = 19;
        iArr[TransportMediator.KEYCODE_MEDIA_PLAY] = -118;
        iArr[TransportMediator.KEYCODE_MEDIA_PAUSE] = 107;
        iArr[128] = 58;
        iArr[129] = -111;
        iArr[TransportMediator.KEYCODE_MEDIA_RECORD] = 17;
        iArr[131] = 65;
        iArr[132] = 79;
        iArr[133] = 103;
        iArr[134] = -36;
        iArr[135] = -22;
        iArr[136] = -105;
        iArr[137] = -14;
        iArr[138] = -49;
        iArr[139] = -50;
        iArr[140] = -16;
        iArr[141] = -76;
        iArr[142] = -26;
        iArr[143] = 115;
        iArr[144] = -106;
        iArr[145] = -84;
        iArr[146] = 116;
        iArr[147] = 34;
        iArr[148] = -25;
        iArr[149] = -83;
        iArr[150] = 53;
        iArr[151] = -123;
        iArr[152] = -30;
        iArr[153] = -7;
        iArr[154] = 55;
        iArr[155] = -24;
        iArr[156] = 28;
        iArr[157] = 117;
        iArr[158] = -33;
        iArr[159] = 110;
        iArr[160] = 71;
        iArr[161] = -15;
        iArr[162] = 26;
        iArr[163] = 113;
        iArr[164] = 29;
        iArr[165] = 41;
        iArr[166] = -59;
        iArr[167] = -119;
        iArr[168] = 111;
        iArr[169] = -73;
        iArr[170] = 98;
        iArr[171] = 14;
        iArr[172] = -86;
        iArr[173] = 24;
        iArr[174] = -66;
        iArr[175] = 27;
        iArr[176] = -4;
        iArr[177] = 86;
        iArr[178] = 62;
        iArr[179] = 75;
        iArr[180] = -58;
        iArr[181] = -46;
        iArr[182] = 121;
        iArr[183] = 32;
        iArr[184] = -102;
        iArr[185] = -37;
        iArr[186] = -64;
        iArr[187] = -2;
        iArr[188] = 120;
        iArr[189] = -51;
        iArr[190] = 90;
        iArr[191] = -12;
        iArr[KEYSIZE.Bit192] = 31;
        iArr[193] = -35;
        iArr[194] = -88;
        iArr[195] = 51;
        iArr[196] = -120;
        iArr[197] = 7;
        iArr[198] = -57;
        iArr[199] = 49;
        iArr[200] = -79;
        iArr[201] = 18;
        iArr[202] = 16;
        iArr[203] = 89;
        iArr[204] = 39;
        iArr[205] = -128;
        iArr[206] = -20;
        iArr[207] = 95;
        iArr[208] = 96;
        iArr[209] = 81;
        iArr[210] = TransportMediator.KEYCODE_MEDIA_PAUSE;
        iArr[211] = -87;
        iArr[212] = 25;
        iArr[213] = -75;
        iArr[214] = 74;
        iArr[215] = 13;
        iArr[216] = 45;
        iArr[217] = -27;
        iArr[218] = 122;
        iArr[219] = -97;
        iArr[220] = -109;
        iArr[221] = -55;
        iArr[222] = -100;
        iArr[223] = -17;
        iArr[224] = -96;
        iArr[225] = -32;
        iArr[226] = 59;
        iArr[227] = 77;
        iArr[228] = -82;
        iArr[229] = 42;
        iArr[230] = -11;
        iArr[231] = -80;
        iArr[232] = -56;
        iArr[233] = -21;
        iArr[234] = -69;
        iArr[235] = 60;
        iArr[236] = -125;
        iArr[237] = 83;
        iArr[238] = -103;
        iArr[239] = 97;
        iArr[240] = 23;
        iArr[241] = 43;
        iArr[242] = 4;
        iArr[243] = TransportMediator.KEYCODE_MEDIA_PLAY;
        iArr[244] = -70;
        iArr[245] = 119;
        iArr[246] = -42;
        iArr[247] = 38;
        iArr[248] = -31;
        iArr[249] = 105;
        iArr[250] = 20;
        iArr[251] = 99;
        iArr[252] = 85;
        iArr[253] = 33;
        iArr[254] = 12;
        iArr[MotionEventCompat.ACTION_MASK] = 125;
        this.iSbox = iArr;
    }

    private void KeyExpansion() {
        int r;
        byte[] rc = new byte[]{(byte) 1, (byte) 2, (byte) 4, (byte) 8, (byte) 16, (byte) 32, (byte) 64, Byte.MIN_VALUE, (byte) 27, (byte) 54};
        for (r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                this.f1w[0][r][c] = this.key[(c * 4) + r];
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < 4; j++) {
                byte[] t = new byte[4];
                for (r = 0; r < 4; r++) {
                    if (j != 0) {
                        t[r] = this.f1w[i][r][j - 1];
                    } else {
                        t[r] = this.f1w[i - 1][r][3];
                    }
                }
                if (j == 0) {
                    int temp = t[0] & MotionEventCompat.ACTION_MASK;
                    for (r = 0; r < 3; r++) {
                        if (t[(r + 1) % 4] < (byte) 0) {
                            t[r] = (byte) this.Sbox[t[(r + 1) % 4] & MotionEventCompat.ACTION_MASK];
                        } else {
                            t[r] = (byte) this.Sbox[t[(r + 1) % 4]];
                        }
                    }
                    t[3] = (byte) this.Sbox[temp];
                    t[0] = (byte) (t[0] ^ rc[i - 1]);
                }
                for (r = 0; r < 4; r++) {
                    this.f1w[i][r][j] = (byte) (this.f1w[i - 1][r][j] ^ t[r]);
                }
            }
        }
    }

    private void AddRoundKey(byte[][] k) {
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                byte[] bArr = this.State[r];
                bArr[c] = (byte) (bArr[c] ^ k[r][c]);
            }
        }
    }

    private void SubBytes() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < this.Nb; c++) {
                this.State[r][c] = (byte) this.Sbox[this.State[r][c] & MotionEventCompat.ACTION_MASK];
            }
        }
    }

    private void InvSubBytes() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < this.Nb; c++) {
                this.State[r][c] = (byte) this.iSbox[this.State[r][c] & MotionEventCompat.ACTION_MASK];
            }
        }
    }

    private void ShiftRows() {
        byte[] t = new byte[this.Nb];
        for (int r = 1; r < 4; r++) {
            int c;
            for (c = 0; c < this.Nb; c++) {
                t[c] = this.State[r][(c + r) % this.Nb];
            }
            for (c = 0; c < this.Nb; c++) {
                this.State[r][c] = t[c];
            }
        }
    }

    private void InvShiftRows() {
        byte[] t = new byte[this.Nb];
        for (int r = 1; r < 4; r++) {
            int c;
            for (c = 0; c < this.Nb; c++) {
                t[c] = this.State[r][((c - r) + 4) % this.Nb];
            }
            for (c = 0; c < this.Nb; c++) {
                this.State[r][c] = t[c];
            }
        }
    }

    private void MixColumns() {
        byte[] t = new byte[this.Nb];
        for (int c = 0; c < 4; c++) {
            int r;
            for (r = 0; r < this.Nb; r++) {
                t[r] = this.State[r][c];
            }
            for (r = 0; r < this.Nb; r++) {
                this.State[r][c] = (byte) (((ffmult((byte) 2, t[r]) ^ ffmult((byte) 3, t[(r + 1) % 4])) ^ ffmult((byte) 1, t[(r + 2) % 4])) ^ ffmult((byte) 1, t[(r + 3) % 4]));
            }
        }
    }

    private void InvMixColumns() {
        byte[] t = new byte[this.Nb];
        for (int c = 0; c < 4; c++) {
            int r;
            for (r = 0; r < this.Nb; r++) {
                t[r] = this.State[r][c];
            }
            for (r = 0; r < this.Nb; r++) {
                this.State[r][c] = (byte) (((ffmult((byte) 14, t[r]) ^ ffmult((byte) 11, t[(r + 1) % 4])) ^ ffmult((byte) 13, t[(r + 2) % 4])) ^ ffmult((byte) 9, t[(r + 3) % 4]));
            }
        }
    }

    private byte ffmult(byte a, byte b) {
        int i;
        int[] bw = new int[4];
        byte res = (byte) 0;
        bw[0] = b;
        for (i = 1; i < 4; i++) {
            bw[i] = bw[i - 1] << 1;
            if ((bw[i - 1] & 128) != 0) {
                bw[i] = bw[i] ^ 27;
            }
        }
        for (i = 0; i < 4; i++) {
            if (((a >> i) & 1) != 0) {
                res = (byte) (bw[i] ^ res);
            }
        }
        return res;
    }

    public void Cipher(byte[] input, byte[] output) {
        int r;
        this.State = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{4, this.Nb});
        for (r = 0; r < 4; r++) {
            int c;
            for (c = 0; c < 4; c++) {
                this.State[r][c] = input[(c * 4) + r];
            }
        }
        AddRoundKey(this.f1w[0]);
        for (int i = 1; i <= 10; i++) {
            SubBytes();
            ShiftRows();
            if (i != 10) {
                MixColumns();
            }
            AddRoundKey(this.f1w[i]);
        }
        for (r = 0; r < 4; r++) {
            for (c = 0; c < 4; c++) {
                output[(c * 4) + r] = this.State[r][c];
            }
        }
    }

    public void getState(byte[][] State) {
        String states = "state == ";
        for (byte[] bs : State) {
            for (byte b : bs) {
                states = new StringBuilder(String.valueOf(states)).append(AesCipherAndInvCipher.byteToHexString(b)).append(" ").toString();
            }
        }
        System.out.println(states);
    }

    public void Invcipher(byte[] input, byte[] output) {
        int r;
        int c;
        this.State = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{4, this.Nb});
        for (r = 0; r < 4; r++) {
            for (c = 0; c < 4; c++) {
                this.State[r][c] = input[(c * 4) + r];
            }
        }
        AddRoundKey(this.f1w[10]);
        for (int i = 9; i >= 0; i--) {
            InvShiftRows();
            InvSubBytes();
            AddRoundKey(this.f1w[i]);
            if (i != 0) {
                InvMixColumns();
            }
        }
        for (r = 0; r < 4; r++) {
            for (c = 0; c < 4; c++) {
                output[(c * 4) + r] = this.State[r][c];
            }
        }
    }
}
