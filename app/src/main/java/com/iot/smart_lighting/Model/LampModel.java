package com.iot.smart_lighting.Model;

public class LampModel {

    private int id;
    private String ssidName;
    private int connection;
    private int status;

    public LampModel(int id, String ssidName, int connection, int status) {
        this.id = id;
        this.ssidName = ssidName;
        this.connection = connection;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getSsidName() {
        return ssidName;
    }

    public int getConnection() {
        return connection;
    }

    public int getStatus() {
        return status;
    }
}
