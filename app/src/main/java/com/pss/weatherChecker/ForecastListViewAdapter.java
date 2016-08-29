package com.pss.weatherChecker;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.*;
import android.widget.*;
import com.pss.weatherChecker.utils.*;
import android.text.*;

public class ForecastListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	public ForecastListViewAdapter(Context context,
						   ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView txtDay;
		TextView txtDate;
		TextView txtHigh;
		TextView txtLow;
		TextView txtConditions;
		String tempUnit = StaticData.TEMPUNIT;

		inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.forecast_list_item, parent, false);
		// Get the position from the results
		HashMap<String, String> resultp = new HashMap<String, String>();
		resultp = data.get(position);

		// Locate the TextView in listview_item.xml
		txtDay = (TextView) itemView.findViewById(R.id.day);
		txtDate = (TextView) itemView.findViewById(R.id.date);
		txtHigh = (TextView) itemView.findViewById(R.id.high);
		txtLow = (TextView) itemView.findViewById(R.id.low);
		txtConditions = (TextView) itemView.findViewById(R.id.conditions);
		
		// Capture position and set results to the TextViews
		txtDay.setText(resultp.get(StaticData.DAY));
		txtDate.setText(resultp.get(StaticData.DATE));
		txtHigh.setText(Html.fromHtml(resultp.get(StaticData.HIGH) + "\u00B0" + tempUnit));
		txtLow.setText(Html.fromHtml(resultp.get(StaticData.LOW) + "\u00B0" + tempUnit));
		txtConditions.setText(resultp.get(StaticData.CONDITION));
		
		
		return itemView;
	}
}
