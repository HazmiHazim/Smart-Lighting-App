package com.iot.smart_lighting.Model;

public class LampColourModel {

    private int id;
    private String colour;
    private int saturation;
    private int opacity;
    private int lampId;

    public LampColourModel(int id, String colour, int saturation, int opacity, int lampId) {
        this.id = id;
        this.colour = colour;
        this.saturation = saturation;
        this.opacity = opacity;
        this.lampId = lampId;
    }

    public int getId() {
        return id;
    }

    public String getColour() {
        return colour;
    }

    public int getSaturation() {
        return saturation;
    }

    public int getOpacity() {
        return opacity;
    }

    public int getLampId() {
        return lampId;
    }
}
