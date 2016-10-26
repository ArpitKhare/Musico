package environementconditions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("http://api.openweathermap.org/data/2.5/")
public class OpenWeather {
	
	public static HashMap<String, String> fetchWeather(String latitude, String longitude){
		String API_KEY="6abcf05a0800bd66788a0274d59036fe";
		String myURL="http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid="+API_KEY;
		
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 
		
		JSONObject weatherJsonObj = new JSONObject(sb.toString());
		//for temperature
		JSONObject getTemperatureKey = weatherJsonObj.getJSONObject("main");
	    Object temp = getTemperatureKey.get("temp");
	    System.out.println("temp: "+temp.toString());
	    
	    //for weather
	    JSONObject weatherArray = weatherJsonObj.getJSONArray("weather").getJSONObject(0);
	    Object weatherID=weatherArray.get("id");
	    Object weatherDesc=weatherArray.get("description");
	    System.out.println("id: "+weatherID.toString());
	    System.out.println("description: "+weatherDesc.toString());
	    
	   // String tempIOT=getTemperatureFromPlotly();
	    
	    HashMap<String,String> environmentHash = new HashMap<String,String>();
	   // environmentHash.put("Temperature", tempIOT.toString());
	    environmentHash.put("Temperature", temp.toString());
	    environmentHash.put("WeatherID",weatherID.toString());
	    environmentHash.put("WeatherDescription",weatherDesc.toString());
	    
	    return environmentHash;
		
		//return sb.toString();
		
		
	}

	private static String getTemperatureFromPlotly() {
		/*
		String myURL="https://iottemp-arpitkhare.c9users.io/iotTemperature/";
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} */
		
		return "261";//sb.toString();
	}

}
