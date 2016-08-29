package com.pss.weatherChecker;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.*;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView; 

import com.pss.weatherChecker.utils.*;
import com.pss.weatherChecker.*;
import android.text.*;

public class ForecastActivity extends Activity 
{
	// Declare Variables
	ListView listview;
	ForecastListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	private String weatherTitle = "";
	private String currentDate = "";
	private String currentCondition = "";
	private String currentTemperature = "";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_forecast);
		
		new DownloadJSON().execute();
	} 
	
	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(ForecastActivity.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Parsing Yahoo Weather data");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			// Create the array
			arraylist = new ArrayList<HashMap<String, String>>();
			// YQL JSON URL
			String url = "https://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20weather.forecast%20where%20woeid%20in%20%28select%20woeid%20from%20geo.places%281%29%20where%20text%3D%22nome%2c%20ak%22%29&format=json&env=store://datatables.org/alltableswithkeys";
			try {
				// Retrive JSON Objects from the given URL in JSONfunctions.class
				JSONObject json_data = JSONFunctions.getJSONfromURL(url);
				JSONObject json_query = json_data.getJSONObject("query");
				JSONObject json_results = json_query.getJSONObject("results");
				JSONObject channel = json_results.getJSONObject("channel");
				JSONObject units = channel.getJSONObject("units");
				JSONObject item = channel.getJSONObject("item");
				JSONObject condition = item.getJSONObject("condition");
				JSONArray forecast = item.getJSONArray("forecast");

				//set title
				weatherTitle = channel.getString("description");
				
				//set current conditions
				currentDate = condition.getString("date");
				currentCondition = condition.getString("text");
				currentTemperature = condition.getString("temp");
				
				//set temperature units
				StaticData.TEMPUNIT = units.getString("temperature");
				
				for (int i = 0; i < 5; i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject forecastItem = forecast.getJSONObject(i);
					map.put(StaticData.DAY, forecastItem.optString("day"));
					map.put(StaticData.DATE, forecastItem.optString("date"));
					map.put(StaticData.HIGH, forecastItem.optString("high"));
					map.put(StaticData.LOW, forecastItem.optString("low"));
					map.put(StaticData.CONDITION, forecastItem.optString("text"));
					
					arraylist.add(map);
				}

			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) 
		{
			SetWeatherForecast();
			SetCurrentWeather();
			
			TextView title = (TextView) findViewById(R.id.weatherTitle);
			title.setText(weatherTitle);
			
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
		
		private void SetWeatherForecast()
		{
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ForecastListViewAdapter(ForecastActivity.this, arraylist);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
		}
		
		private void SetCurrentWeather()
		{
			TextView currentDateDisplay = (TextView) findViewById(R.id.conditionDate);
			TextView currentConditionDisplay = (TextView) findViewById(R.id.currentCondition);
			TextView currentTempDisplay = (TextView) findViewById(R.id.currentTemp);
			
			currentDateDisplay.setText(currentDate);
			currentConditionDisplay.setText(currentCondition);
			currentTempDisplay.setText(Html.fromHtml(currentTemperature + "\u00B0" + StaticData.TEMPUNIT));
		}
	}
}
