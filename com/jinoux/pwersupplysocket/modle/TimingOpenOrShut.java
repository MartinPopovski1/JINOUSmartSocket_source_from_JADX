package com.jinoux.pwersupplysocket.modle;

public class TimingOpenOrShut {
    private int timingid;
    private String timingopen;
    private String timingshut;

    public TimingOpenOrShut(int tid, String open, String shut) {
        this.timingid = tid;
        this.timingopen = open;
        this.timingshut = shut;
    }

    public int getTimingid() {
        return this.timingid;
    }

    public void setTimingid(int timingid) {
        this.timingid = timingid;
    }

    public String getTimingopen() {
        return this.timingopen;
    }

    public void setTimingopen(String timingopen) {
        this.timingopen = timingopen;
    }

    public String getTimingshut() {
        return this.timingshut;
    }

    public void setTimingshut(String timingshut) {
        this.timingshut = timingshut;
    }
}
