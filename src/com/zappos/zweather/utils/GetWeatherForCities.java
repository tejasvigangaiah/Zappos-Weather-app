package com.zappos.zweather.utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.zappos.zweather.R;

public class GetWeatherForCities {
	
	Handler handler;
	Context mContext;
	HashMap<String, Double> temperatures;
	ArrayList<String> mCities;
	public GetWeatherForCities(Context mContext) {
		// TODO Auto-generated constructor stub
        handler = new Handler();
        this.mContext = mContext;
        temperatures = new HashMap<String, Double>();
        mCities = new ArrayList<String>();
        
        
	}
	//HashMap<String, Double> 
	
	public HashMap<String, Double> getTemparature(ArrayList<String> cities)
	{
		for (int i = 0; i < cities.size(); i++) {
			updateWeatherData(cities.get(i));
		}
		System.out.println("I m here ======================>");
		for (int i = 0; i < temperatures.size(); i++) {
			System.out.println("I m here 2======================>");
			System.out.println(temperatures.get(cities.get(i)));
		}
		
		return temperatures;
	}
	
	
	private void updateWeatherData(final String city){
	    new Thread(){
	        public void run(){
	            final JSONObject json = RemoteFetch.getJSON(mContext, city);
	            if(json == null){
	                handler.post(new Runnable(){
	                    public void run(){
	                        Toast.makeText(mContext, 
	                        		mContext.getString(R.string.place_not_found), 
	                                Toast.LENGTH_LONG).show(); 
	                    }
	                });
	            } else {
	                handler.post(new Runnable(){
	                    public void run(){
	                        renderWeather(json);
	                    }
	                });
	            }               
	        }
	    }.start();
	}
	
	private void renderWeather(JSONObject json){
		
	    try {
	    	
	    	
	        
	        String cityField = json.getString("name").toUpperCase(Locale.US) + 
	                ", " + 
	                json.getJSONObject("sys").getString("country");
	        
	        JSONObject details = json.getJSONArray("weather").getJSONObject(0);
	        JSONObject main = json.getJSONObject("main");
	        temperatures.put(cityField,  main.getDouble("temp"));
	       
	        
	         
	    }catch(Exception e){
	        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
	    }
		
	}
}
