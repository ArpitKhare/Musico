package com.sjsu.edu.RecommenderApp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;



public class UserIDGenerator extends Recommend {
	
	public static String getTemperatureID(String temperature){
		String temperatureID="";
		System.out.println("temp:::"+temperature);
		Double tempDouble= Double.parseDouble(temperature);
		if(tempDouble>=313.15)
		{
			temperatureID="3";
		}
		else if(tempDouble>=298.15 && tempDouble<313.15)
		{
			temperatureID="2";
		}
		else
			temperatureID="1";
		
		System.out.println("temperatureID: "+temperatureID);
			
		return temperatureID;
	}

	public static String getWeatherID(String weatherID) {
		String weather="";
		Integer weatherInt=Integer.parseInt(weatherID);
		
		if(weatherInt>=200 && weatherInt<=232)
			weather="1";
		
		if(weatherInt>=300 && weatherInt<=321)
			weather="2";
		
		if(weatherInt>=500 && weatherInt<=531)
			weather="3";
		
		if(weatherInt>=600 && weatherInt<=622)
			weather="4";
		
		if(weatherInt>=701 && weatherInt<=781)
			weather="5";
		
		if(weatherInt==800)
			weather="6";
		
		if(weatherInt>=801 && weatherInt<=804)
			weather="7";
		
		if(weatherInt>=900 && weatherInt<=906)
			weather="8";
		
		if(weatherInt>=951 && weatherInt<=962)
			weather="9";
		
		
		System.out.println("weather:: "+weather);
		return weather;
	}

	public static String getTimeID(String latitude, String longitude) {
		
		String APIKey ="AIzaSyB9QVq6FQrZ6_25f826J_oVy4VjaLvrdcg";
		
		String loc= latitude+","+longitude;
		String response ="not set initially";
		String url ="https://maps.googleapis.com/maps/api/timezone/json?location="+loc+"&timestamp=1331161200&key="+APIKey;
		String timeZone= callURL(url);
		try {
			JSONObject j = new JSONObject(timeZone);
			String tz=j.get("timeZoneId").toString();
			String timeOfDevice = getTime(tz);
			String ar[]= timeOfDevice.split(":");
			
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(ar[0]));
			cal.set(Calendar.MINUTE,Integer.parseInt(ar[1]));
			cal.set(Calendar.SECOND,Integer.parseInt(ar[2]));
			Date d = cal.getTime();
			
			Date d1 = new Date();
			d1.setHours(01);
			d1.setMinutes(00);
			d1.setSeconds(00);
			
			Date d5 = new Date();
			d5.setHours(05);
			d5.setMinutes(00);
			d5.setSeconds(00);
			
			Date d10 = new Date();
			d10.setHours(10);
			d10.setMinutes(00);
			d10.setSeconds(00);
			
			Date d15 = new Date();
			d15.setHours(15);
			d15.setMinutes(00);
			d15.setSeconds(00);
			
		
			Date d20 = new Date();
			d20.setHours(20);
			d20.setMinutes(00);
			d20.setSeconds(00);
			
			
			System.out.println(d+""+d1+""+d5+""+d10+""+d15+""+d20);
			System.out.println(d.after(d15));
			System.out.println(d.before(d20));
			if(d.after(d5)&&d.before(d10))
			{
				response ="1";
			}
			else if(d.after(d10)&&d.before(d15))
			{
				response ="2";
			}
			else if(d.after(d15)&&d.before(d20))
			{
				response ="3";
			}
			else if(d.after(d20))
			{
				if(d.after(d1))
				{
				response ="4";
				}
			}
			else if(d.after(d1)&&d.before(d5))
			{
				response ="5";
			}
			System.out.println(response);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(callURL(url));
		return response;
	}
	public static String callURL(String myURL) {
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

		return sb.toString();
	}
	public static String getTime(String timeZone)
	{
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone(timeZone);
		java.util.Calendar c = java.util.Calendar.getInstance(tz);
		System.out.println(c.get(java.util.Calendar.HOUR_OF_DAY)+":"+c.get(java.util.Calendar.MINUTE)+":"+c.get(java.util.Calendar.SECOND));
		return c.get(java.util.Calendar.HOUR_OF_DAY)+":"+c.get(java.util.Calendar.MINUTE)+":"+c.get(java.util.Calendar.SECOND);
	}
	
public static HashMap<String,String> getGenre(HashMap<Long,Float> recommendation){
	
	HashMap<String,String> genreName=new HashMap<String,String>();
	
	Iterator it = recommendation.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        System.out.println(pair.getKey() + " = " + pair.getValue());
        String genreKey=getGenreName(pair.getKey().toString());
        
        genreName.put(pair.getKey().toString(), genreKey);
        
        it.remove(); // avoids a ConcurrentModificationException
    } 
	return genreName;
}

private static String getGenreName(String keyStr) {
	
	if(keyStr.equalsIgnoreCase("1"))
		return "Pop";
	
	if(keyStr.equalsIgnoreCase("2"))
		return "Chill";

	if(keyStr.equalsIgnoreCase("3"))
		return "Party";
	
	if(keyStr.equalsIgnoreCase("4"))
		return "Hip Hop";
	
	if(keyStr.equalsIgnoreCase("5"))
		return "Dance";
	
	if(keyStr.equalsIgnoreCase("6"))
		return "Sleep";
	
	if(keyStr.equalsIgnoreCase("7"))
		return "Rock";
	
	if(keyStr.equalsIgnoreCase("8"))
		return "Jazz";
	
	
	
	return null;
}

}
