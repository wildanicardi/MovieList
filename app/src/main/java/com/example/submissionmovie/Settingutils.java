package com.example.submissionmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settingutils {
    private SharedPreferences sharedPreferences;

    public Settingutils(Context context) {
        if (context != null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public void setReleaseReminder(boolean status){
        sharedPreferences
                .edit()
                .putBoolean("release_reminder",status)
                .apply();
    }

    public boolean getReleaseReminder(){
        return sharedPreferences.getBoolean("release_reminder",true);
    }

    public void setDialyReminder(boolean status){
        sharedPreferences
                .edit()
                .putBoolean("dialy_reminder",status)
                .apply();
    }

    public boolean getDialyReminder(){
        return sharedPreferences.getBoolean("dialy_reminder",true);
    }
}
