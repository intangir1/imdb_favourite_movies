package com.likhtman.imdbfavouritemovies.globalStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class TemporaryStorageSharedPreference {
    protected final static int DEFAULTINT = 0;
    protected final static boolean DEFAULTBOOL = false;
    protected final static String DEFAULTSTRING = "";
    int tempInt = 0;
    boolean tempBool = false;
    String tempString = "";

    public int readSharedPreferenceInt(Context context, String spName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return tempInt = sharedPreferences.getInt(key,DEFAULTINT);
    }

    public boolean readSharedPreferenceBool(Context context, String spName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return tempBool = sharedPreferences.getBoolean(key,DEFAULTBOOL);
    }


    public String  readSharedPreferenceString(Context context, String spName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String tempString = sharedPreferences.getString(key,DEFAULTSTRING);
        writeSharedPreferenceString(context,"","MyPref", "fragment");
        return tempString;
    }


    public void writeSharedPreferenceInt(Context context,int data,String spName,String key ){

        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, data);
        editor.commit();
    }


    public void writeSharedPreferenceBool(Context context,boolean data,String spName,String key ){

        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public void writeSharedPreferenceString(Context context,String data,String spName,String key ){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

}