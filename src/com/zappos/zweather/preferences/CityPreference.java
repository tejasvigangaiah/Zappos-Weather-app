package com.zappos.zweather.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
 
public class CityPreference {
     
    SharedPreferences prefs;
     
    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
     
    
    public String getCity(){
             
        return prefs.getString("city", "Tempe");        
    }
     
    public void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
     
}