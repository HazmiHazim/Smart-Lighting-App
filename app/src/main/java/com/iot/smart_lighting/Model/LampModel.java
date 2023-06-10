package com.iot.smart_lighting.Model;

public class LampModel {

    private int id;
    private int connection;
    private int status;
    private int networkId;

    public LampModel(int id, int connection, int status, int networkId) {
        this.id = id;
        this.connection = connection;
        this.status = status;
        this.networkId = networkId;
    }

    public int getId() {
        return id;
    }

    public int getConnection() {
        return connection;
    }

    public int getStatus() {
        return status;
    }

    public int getNetworkId() {
        return networkId;
    }
}
