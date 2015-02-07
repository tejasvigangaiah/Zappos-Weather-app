package com.zappos.zweather.activity;


import com.zappos.zweather.R;
import com.zappos.zweather.preferences.CityPreference;
import com.zappos.zweather.utils.WeatherFragment;

import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class WeatherActivity extends ActionBarActivity {

	private ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		 
	    if (savedInstanceState == null) {
	    	pDialog = new ProgressDialog(this);
			// Showing progress dialog before making http request
			pDialog.setMessage("Loading...");
			pDialog.show();
	        getSupportFragmentManager().beginTransaction()
	                .add(R.id.container, new WeatherFragment(pDialog))
	                .commit();
	    }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    if(item.getItemId() == R.id.change_city){
	        showInputDialog();
	    }
	    return false;
		
	}

	
	private void showInputDialog(){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Change city");
	    final EditText input = new EditText(this);
	    input.setInputType(InputType.TYPE_CLASS_TEXT);
	    builder.setView(input);
	    builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            changeCity(input.getText().toString());
	        }
	    });
	    builder.show();
	}
	 
	public void changeCity(String city){
		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

	    WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
	                            .findFragmentById(R.id.container);
	    wf.changeCity(city, pDialog);
	    new CityPreference(this).setCity(city);
	}
	
}
