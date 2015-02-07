package com.zappos.zweather.activity;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import org.json.JSONObject;

import com.zappos.zweather.R;
import com.zappos.zweather.listadapter.CustomListAdapter;
import com.zappos.zweather.utils.GetWeatherForCities;
import com.zappos.zweather.utils.RemoteFetch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class CityListActivity extends Activity {

	
	Handler handler;
//	Context mContext;
	HashMap<String, Double> temperatures;
	ArrayList<String> mCities;
	ArrayList<String> mTemperature;
	public CustomListAdapter adapter;
	public ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		listView = (ListView) findViewById(R.id.list);
		 	handler = new Handler();
	        //this.mContext = mContext;
	        temperatures = new HashMap<String, Double>();
	        mCities = new ArrayList<String>();
	        mTemperature = new ArrayList<String>();
		  ArrayList<String> result = new ArrayList<String>();
	         result.add("bangalore");
	         result.add("san francisco");
	         getTemparature(result);
	         System.out.println("mCities.size() "+mCities.size());
	         
		// (new AsyncListViewLoader()).execute("");
	         
	      
	}
	
	private class AsyncListViewLoader extends AsyncTask<String, Void, ArrayList<String>> {
	    private final ProgressDialog dialog = new ProgressDialog(CityListActivity.this);
	     
	    @Override
	    protected void onPostExecute(ArrayList<String> result) {           
	        super.onPostExecute(result);
	        dialog.dismiss();
//	        adpt.setItemList(result);
//	        adpt.notifyDataSetChanged();
	    }
	 
	    @Override
	    protected void onPreExecute() {       
	        super.onPreExecute();
	        dialog.setMessage("Downloading weather info...");
	        dialog.show();           
	    }
	 
	    @Override
	    protected ArrayList<String> doInBackground(String... params) {
	        ArrayList<String> result = new ArrayList<String>();
	         result.add("bangalore");
	         result.add("san francisco");
	        try {
//	        	for (int i = 0; i < result.size(); i++) {
//	        		 final JSONObject json = RemoteFetch.getJSON(getApplicationContext(), result);
//				}
	        	
	        	GetWeatherForCities getWeather = new GetWeatherForCities(getApplicationContext());
	        	getWeather.getTemparature(result);
	             
	            return result;
	        }
	        catch(Throwable t) {
	            t.printStackTrace();
	        }
	        return null;
	    }
	     
	    
	     
	}
	
	public HashMap<String, Double> getTemparature(ArrayList<String> cities)
	{
		for (int i = 0; i < cities.size(); i++) {
			System.out.println("city = "+ cities.get(i));
			updateWeatherData(cities.get(i));
		}
		System.out.println("I m here ======================>"+ temperatures.size());
		for (int i = 0; i < temperatures.size(); i++) {
			System.out.println("I m here 2======================>");
			System.out.println(temperatures.get(cities.get(i)));
		}
		
		return temperatures;
	}
	
	
	private void updateWeatherData(final String city){
	    new Thread(){
	        public void run(){
	            final JSONObject json = RemoteFetch.getJSON(getApplicationContext(), city);
	            if(json == null){
	                handler.post(new Runnable(){
	                    public void run(){
	                        Toast.makeText(getApplicationContext(), 
	                        		getApplicationContext().getString(R.string.place_not_found), 
	                                Toast.LENGTH_LONG).show(); 
	                    }
	                });
	            } else {
	                handler.post(new Runnable(){
	                    public void run(){
	                    	System.out.println("I m here 4");
	                        renderWeather(json);
	                    }
	                });
	            }               
	        }
	    }.start();
	}
	
	private void renderWeather(JSONObject json){
		
	    try {
	    	
	    	
	        System.out.println("I m here 3");
	        String cityField = json.getString("name").toUpperCase(Locale.US) + 
	                ", " + 
	                json.getJSONObject("sys").getString("country");
	        
	        System.out.println("cityField = "+ cityField);
	        JSONObject details = json.getJSONArray("weather").getJSONObject(0);
	        JSONObject main = json.getJSONObject("main");
	        System.out.println("temp = "+ main.getDouble("temp"));
	        
	        mCities.add(cityField);
	        mTemperature.add( String.format("%.2f", main.getDouble("temp"))+ " ℃");
	        //temperatures.put(cityField,  main.getDouble("temp"));
	     
	        if(mCities.size() == 2)
	        {
	        	System.out.println("i m in mcities.size() == 2");
	        	// ArrayList<String> city =  cityList.toArray();
	   	      adapter = new CustomListAdapter(this, mCities, mTemperature); 
	   	      listView.setAdapter(adapter);
	        }
	     // Set<String> cityList = temperatures.;
	      
	      
	         
	    }catch(Exception e){
	        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
	    }
		
	}
	

}
