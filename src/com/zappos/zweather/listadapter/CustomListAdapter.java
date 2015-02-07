package com.zappos.zweather.listadapter;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.zappos.zweather.R;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter{

	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<String> cities;
	private ArrayList<String> temperature;
	
	public CustomListAdapter(Activity mContext, ArrayList<String> city, ArrayList<String> temperature) {
		// TODO Auto-generated constructor stub
		activity = mContext;
		cities = new ArrayList<String>();
		this.temperature = new ArrayList<String>(); 
		for (int i = 0; i < city.size(); i++) {
			cities.add(city.get(i));
			this.temperature.add(temperature.get(i));
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cities.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		
		return cities.get(location);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.city_list_view, null);
		TextView cityName = (TextView) convertView.findViewById(R.id.city_name);
		TextView temperature = (TextView) convertView.findViewById(R.id.temperature);
		
		cityName.setText(cities.get(position));
		temperature.setText(this.temperature.get(position));
		
		
		return convertView;
	}

}
