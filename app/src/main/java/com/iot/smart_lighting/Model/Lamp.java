package com.iot.smart_lighting.Model;

public class Lamp {

    private int id;
    private String ssidName;
    private int intensity;
    private int connection;
    private int status;

    public Lamp() {}

    public Lamp(int id, String ssidName, int intensity, int connection, int status) {
        this.id = id;
        this.ssidName = ssidName;
        this.intensity = intensity;
        this.connection = connection;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getSsidName() {
        return ssidName;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getConnection() {
        return connection;
    }

    public int getStatus() {
        return status;
    }
}
