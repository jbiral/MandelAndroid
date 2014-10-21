package com.julienbiral.mandelandroid.util;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParser {

    JsonParser() { }

    public static String createJson(DrawingParameters params) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(params);
            Log.e("JSon Parser says: ", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static DrawingParameters getParams(String json) {
        ObjectMapper mapper = new ObjectMapper();
        DrawingParameters params = null;

        try {
            params = mapper.readValue(json, DrawingParameters.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }
}
