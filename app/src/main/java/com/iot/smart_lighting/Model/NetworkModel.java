package com.iot.smart_lighting.Model;

public class NetworkModel {

    private int id;
    private String ssidName;
    private int ssidKey;

    public NetworkModel(int id, String ssidName, int ssidKey) {
        this.id = id;
        this.ssidName = ssidName;
        this.ssidKey = ssidKey;
    }

    public int getId() {
        return id;
    }

    public String getSsidName() {
        return ssidName;
    }

    public int getSsidKey() {
        return ssidKey;
    }
}
