package com.iot.smart_lighting;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Esp32 {

    private Context context;
    private  RequestQueue queue;

    public Esp32 (Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);  // Instantiate the RequestQueue
    }

    // Function to Get Network SSID
    public String getESP32Ssid() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            String connectedSsid = wifiInfo.getSSID();
            Log.d("SSID", "Connected SSID: " + connectedSsid);
            return connectedSsid;
        }
        return "";
    }

    private void sendRequest(String url, int method, Response.Listener<String> success, Response.ErrorListener error) {
        StringRequest stringRequest = new StringRequest(method, url, success, error);
        queue.add(stringRequest);
    }

    // Function to ping to ESP32
    public void pingESP32() {
        String url = "http://192.168.4.1";

        // Request a string response  from the URL
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response);
                Toast.makeText(context, "Response: Ping!", Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response: ", String.valueOf(error));
                Toast.makeText(context, "Response: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        // Add the request to the RequestQueue
        sendRequest(url, Request.Method.GET, success, error);
    }

    // Function to get initial state of lamp
    public void getLampStates(String url) {
        // Request a string response  from the URL
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response);
                //Toast.makeText(context, "Response: " + response, Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Log.e("No Connection Error: ", error.toString());
                }
                Log.d("Response: ", String.valueOf(error));
                Toast.makeText(context, "Response: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        // Add the request to the RequestQueue
        sendRequest(url, Request.Method.GET, success, error);
    }

    // Function to turn on/off lamp
    public void applyLamp(String url) {
        // Request a string response from the URL
        // Use POST method to send the request by the user
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response);
                //Toast.makeText(context, "Response: " + response, Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response: ", String.valueOf(error));
                Toast.makeText(context, "Response: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        // Add the request to the RequestQueue
        sendRequest(url, Request.Method.POST, success, error);
    }
}
