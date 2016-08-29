package com.pss.weatherChecker.models;


public class WeatherInformation
{
    private String title = "";
	private String description = "";
	private int currentTemperature = 0;
	
	public void setCurrentTemperature(int temperature) {
	    currentTemperature = temperature;
	}

	public int getCurrentTemperature() {
		return currentTemperature;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}
}
