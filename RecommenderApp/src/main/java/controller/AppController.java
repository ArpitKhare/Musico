package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.validation.Valid;

import org.apache.mahout.cf.taste.common.TasteException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import environementconditions.OpenWeather;
import com.sjsu.edu.RecommenderApp.*;

@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/cmpe239/MusicRecommendation/v1/*")
public class AppController {
	

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody
	String cancelReservation(){
		System.out.println("hi");
		return "hello";
	}
	@CrossOrigin(origins = "*")
	 @RequestMapping(value = "/getEnvironment",method=RequestMethod.GET)//,headers="Content-Type=application/json")
	   // @ResponseStatus(HttpStatus.OK)
	    public @ResponseBody
	    HashMap<String, Object> fetch
	    (
	     @Valid @RequestParam(value="latitude") String latitude,
	     @Valid @RequestParam(value="longitude") String longitude) throws TasteException, IOException{
	   //  @Valid @RequestParam(value="tempIOT") String tempIOT) throws TasteException, IOException{
	        System.out.println("latitude"+latitude);
	        System.out.println("longitude"+longitude);
	       
	        HashMap<String, String> tempWeather = OpenWeather.fetchWeather(latitude,longitude);
	        System.out.println("tempWeather: "+tempWeather);
	        System.out.println("Temperature"+tempWeather.get("Temperature"));
	        System.out.println("WeatherID"+tempWeather.get("WeatherID"));
	        System.out.println("WeatherDescription"+tempWeather.get("WeatherDescription"));
	        
	        String temperature=tempWeather.get("Temperature");
	        
	        String weatherID=tempWeather.get("WeatherID");
	        
	        String tempID=com.sjsu.edu.RecommenderApp.UserIDGenerator.getTemperatureID(temperature);
	        String weather_id=com.sjsu.edu.RecommenderApp.UserIDGenerator.getWeatherID(weatherID);
	        String time=com.sjsu.edu.RecommenderApp.UserIDGenerator.getTimeID(latitude,longitude);
	        System.out.println("time:::::::::"+time);
	        
	        String user_tuple=weather_id+time+tempID;
	        System.out.println("user_tuple:"+user_tuple);
	        
			HashMap<Long,Float> recommendation = com.sjsu.edu.RecommenderApp.Recommend.generateRecommendation(user_tuple);
			System.out.println("send this"+recommendation);
			//update recommendation
			//com.sjsu.edu.RecommenderApp.Recommend.writeToCSV( recommendation,  user_tuple);
			
			HashMap<String,String> result=com.sjsu.edu.RecommenderApp.UserIDGenerator.getGenre(recommendation);
			System.out.println(":result:"+result);  
			
			
			ArrayList<HashMap<String, String>> al= new ArrayList<HashMap<String, String>>();
		    //al.add(tempWeather);
		    al.add(result);

		    HashMap<String,Object> hm = new HashMap<String, Object>();
		    //hm.put("genre", result);
		    hm.put("weather", tempWeather);
		    hm.put("genre", (new ArrayList( result.values())));
		    //hm.put("weather", (new ArrayList( tempWeather.values())).toString());
		    //hm.put("weather", new JSONObject(tempWeather).toString());
		    
		    
	        return hm;
	    }
	 
	 @RequestMapping(value = "/getRecommendation",method=RequestMethod.GET)//,headers="Content-Type=application/json")
	   // @ResponseStatus(HttpStatus.OK)
	    public @ResponseBody
	    HashMap<String, Object> recommendation
	    (
	     @Valid @RequestParam(value="latitude") String latitude,
	     @Valid @RequestParam(value="longitude") String longitude) throws TasteException, IOException{
	        System.out.println("latitude"+latitude);
	        System.out.println("longitude"+longitude);
	       
	        HashMap<String, String> tempWeather = OpenWeather.fetchWeather(latitude,longitude);
	        //System.out.println("tempWeather: "+tempWeather);
	        System.out.println("Temperature"+tempWeather.get("Temperature"));
	        System.out.println("WeatherID"+tempWeather.get("WeatherID"));
	        System.out.println("WeatherDescription"+tempWeather.get("WeatherDescription"));
	        
	        String temperature=tempWeather.get("Temperature");
	        
	        String weatherID=tempWeather.get("WeatherID");
	        
	        String tempID=com.sjsu.edu.RecommenderApp.UserIDGenerator.getTemperatureID(temperature);
	        String weather_id=com.sjsu.edu.RecommenderApp.UserIDGenerator.getWeatherID(weatherID);
	        String time=com.sjsu.edu.RecommenderApp.UserIDGenerator.getTimeID(latitude,longitude);
	        System.out.println("time:::::::::"+time);
	        
	        String user_tuple=weather_id+time+tempID;
	        System.out.println("user_tuple:"+user_tuple);
	        
			HashMap<Long,Float> recommendation = com.sjsu.edu.RecommenderApp.Recommend.generateRecommendation(user_tuple);
			System.out.println("send this"+recommendation);
			//update recommendation
			//com.sjsu.edu.RecommenderApp.Recommend.writeToCSV( recommendation,  user_tuple);
			
			HashMap<String,String> result=com.sjsu.edu.RecommenderApp.UserIDGenerator.getGenre(recommendation);
			System.out.println(":result:"+result);  
			
			
			ArrayList<HashMap<String, String>> al= new ArrayList<HashMap<String, String>>();
		    //al.add(tempWeather);
		    al.add(result);

		    HashMap<String,Object> hm = new HashMap<String, Object>();
		    //hm.put("genre", result);
		    hm.put("weather", tempWeather);
		    hm.put("genre", (new ArrayList( result.values())).toString());
		    //hm.put("weather", (new ArrayList( tempWeather.values())).toString());
		    //hm.put("weather", new JSONObject(tempWeather).toString());
		    
		    
	        return hm;
	    }

}
