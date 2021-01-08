package com.likhtman.imdbfavouritemovies.managers.apimanagers;

import android.os.AsyncTask;

import com.likhtman.imdbfavouritemovies.callbacks.CallbacksWebApi;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDeleteManager extends AsyncTask<String, Void, String> {

    private CallbacksWebApi callbacks; // Notify to activity what happened.
    private int httpStatusCode; // Http status code.
    private String errorMessage; // Error message.
    private String link;

    // Constructor:
    public ApiDeleteManager(CallbacksWebApi callbacks) {
        this.callbacks = callbacks;
    }

    // Executes before doInBackground in the UI's thread:
    protected void onPreExecute() {
        callbacks.onAboutToBegin();
    }

    // Executes in the background in a different thread than the UI's thread:
    protected String doInBackground(String... params) {
        // Readers:
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {

            // Take given link:
            link = params[0];

            // Create a url:
            URL url = new URL(link);

            // Open connection:
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("DELETE");
//            connection.setRequestProperty("platform", ConstantsManager.getInstance().getPlatform());
//            connection.setRequestProperty("version", ConstantsManager.getInstance().getVersion());
//            connection.setRequestProperty("APPID", ConstantsManager.getInstance().getAPPID());
//            connection.setRequestProperty("SECRETKEY", ConstantsManager.getInstance().getSECRETKEY());
//            connection.setRequestProperty("sdkver", "1");


            // Check for failure:
            httpStatusCode = connection.getResponseCode();
            if(httpStatusCode != HttpURLConnection.HTTP_OK) {
                errorMessage = connection.getResponseMessage(); // Can be null.
                return null;
            }

            // Create readers:
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            // Read downloaded text:
            StringBuilder downloadedText = new StringBuilder();
            String oneLine = bufferedReader.readLine();
            while(oneLine != null) {
                downloadedText.append(oneLine + "\n");
                oneLine = bufferedReader.readLine();
            }

            // Return result:
            return downloadedText.toString();
        }
        catch(Exception ex) {
            errorMessage = ex.getMessage(); // Can be null.
            return null;
        }
        finally { // Close readers:
            if(bufferedReader != null)
                try { bufferedReader.close(); } catch (Exception e) { }
            if(inputStreamReader != null)
                try { inputStreamReader.close(); } catch (Exception e) { }
            if(inputStream != null)
                try { inputStream.close(); } catch (Exception e) { }
        }
    }



    // Executes after doInBackground in the UI's thread:
    protected void onPostExecute(String downloadedText) {
        if(downloadedText != null) // Don't check errorMessage cause it can be null even if there is an error.
        {
            try {
                callbacks.onSuccess(downloadedText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            callbacks.onError(httpStatusCode, errorMessage);
    }
}

