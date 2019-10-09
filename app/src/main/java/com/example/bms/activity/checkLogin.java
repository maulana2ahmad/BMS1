package com.example.bms.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class checkLogin {
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private String url = "http://portal-bams.mncgroup.com:8008/api";
    private Activity activity;
    private ProgressDialog pDialog;


    public checkLogin(Activity activity){
        this.activity = activity;
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void Check(final String user, final String pass) {
        displayLoader();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOGIN", "Login Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);

                    // Check for error node in json
                    Log.e("STATUS", "onResponse: "+jObj.getString(KEY_MESSAGE));
                    if (jObj.getString(KEY_MESSAGE).equals("berhasil")) {
                        pDialog.dismiss();

                        Log.e("Successfully Login!", jObj.getString("token"));

                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.putExtra("token", jObj.getString(KEY_TOKEN));

                        activity.startActivity(intent);
                        activity.finish();
                    }
                    else {
                        pDialog.dismiss();
                        Toast.makeText(activity.getApplicationContext(),
                                "Login "+jObj.getString(KEY_MESSAGE)+", please check Username & Password!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    pDialog.dismiss();
                    // JSON error
                    Log.e("ERROR", "onResponse: "+ e.toString() );
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN", "Login Error: " + error.getMessage());
//                Toast.makeText(activity.getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();

                Toast.makeText(activity.getApplicationContext(), "Address and hostname not available", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, user);
                params.put(KEY_PASSWORD, pass);
                return params;
            }
        };

        // Adding request to request queue
        MySingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(strReq);
    }
}
