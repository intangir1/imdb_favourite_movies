package com.likhtman.imdbfavouritemovies.managers.apimanagers;

import android.os.AsyncTask;

import com.likhtman.imdbfavouritemovies.callbacks.CallbacksWebApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiPutManager extends AsyncTask<String, Void, String> {

    private CallbacksWebApi callbacks; // Notify to activity what happened.
    private int httpStatusCode; // Http status code.
    private String errorMessage; // Error message.
    private String link;

    // Take given link:
    private String requestBody;


    // Constructor:
    public ApiPutManager(CallbacksWebApi callbacks) {
        this.callbacks = callbacks;
    }

    // Executes before doInBackground in the UI's thread:
    protected void onPreExecute() {
        callbacks.onAboutToBegin();
    }

    // Executes in the background in a different thread than the UI's thread:
    protected String doInBackground(String... params) {
        // Take given link:
        link = params[0];

        // Take given link:
        requestBody = params[1];



        URL url = null;
        // Create a url:
        try {
            url = new URL(link);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("Put");
//            connection.setRequestProperty("platform", ConstantsManager.getInstance().getPlatform());
//            connection.setRequestProperty("version", ConstantsManager.getInstance().getVersion());
//            connection.setRequestProperty("APPID", ConstantsManager.getInstance().getAPPID());
//            connection.setRequestProperty("SECRETKEY", ConstantsManager.getInstance().getSECRETKEY());
//
//            connection.setRequestProperty("sdkver", "1");
//
//            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        InputStream inputStream = null;
        // get stream
        try {
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String temp, downloadedText = "";
        if(inputStream != null){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while ((temp = bufferedReader.readLine()) != null) {
                    downloadedText += temp;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }else{
            downloadedText = "{success:false}";
        }
        return downloadedText.toString();
    }

    // Executes after doInBackground in the UI's thread:
    protected void onPostExecute(String downloadedText) {
        if(downloadedText != null) // Don't check errorMessage cause it can be null even if there is an error.
        {
            JSONObject jObject; // JSONObject
            JSONArray jArray; // JSONArray
            Object textObject = new JSONTokener(downloadedText);
            if (textObject instanceof JSONObject){
                try {
                    jObject = new JSONObject(downloadedText); // set json string to json object
                    callbacks.onSuccess(jObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else if (textObject instanceof JSONArray){
                try{
                    jArray = new JSONArray(downloadedText);
                    callbacks.onSuccess(jArray);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        else
            callbacks.onError(httpStatusCode, errorMessage);
    }

}