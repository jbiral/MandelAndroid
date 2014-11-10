package com.julienbiral.mandelandroid.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DrawingParameters {

    /** Class constants */
    private final static int COLOR_COMPONENT = 3;
    private final static int RED = 0;
    private final static int GREEN = 1;
    private final static int BLUE = 2;

    /** Model */
    public int zoom;
    public double[] color = new double[COLOR_COMPONENT];

    /** Constructor */
    public DrawingParameters() { }

    /** Setters and getters */
    @JsonIgnore
    public void setRGBColor(double red, double green, double blue) {
        color[RED] = red;
        color[GREEN] = green;
        color[BLUE] = blue;
    }

    @JsonIgnore
    public double getRedColor() {
        return color[RED];
    }

    @JsonIgnore
    public double getGreenColor() {
        return color[GREEN];
    }

    @JsonIgnore
    public double getBlueColor() {
        return color[BLUE];
    }

    /** Methods */
    @Override
    public String toString() {
        return "DrawingParameters {Zoom = " + zoom
                + ", Color = ["
                + "R: " + color[RED]
                + "G: " + color[GREEN]
                + "B: " + color[BLUE]
                + "] }";
    }
}
