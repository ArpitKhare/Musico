package com.sjsu.edu.RecommenderApp;
/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration; 
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; 
import org.springframework.web.filter.CorsFilter; 
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.qos.logback.classic.Logger;
import controller.AppController;
@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class App 
{
    public static void main( String[] args )
    {
      SpringApplication.run(AppController.class,args);
    }
    
    @Bean 
    public WebMvcConfigurer corsConfigurer() { 
        return new WebMvcConfigurerAdapter() { 
            @Override 
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*").allowedOrigins("*");
            } 
        }; 
    } 
    @Bean 
    public FilterRegistrationBean corsFilter() { 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        // return new CorsFilter(source); 
        final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        //Logger.logMsg(1,"arpit"); 
        System.out.println("arpit khare");
        return bean;
 
    } 
}

/*
public class App 
{
    public static void main( String[] args ) throws IOException, TasteException
    {	String str="";
    	DataModel datamodel = new FileDataModel(new File("data/movienight_2.csv"));
    	
    	//Creating UserSimilarity object.
        UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);
     
        //Creating UserNeighbourHHood object.
        UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(0.1, usersimilarity, datamodel);
     
        //Create UserRecomender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);
       
        List<RecommendedItem> recommendations = recommender.recommend(4L, 3);
        //System.out.println(recommendations);
        for (RecommendedItem recommendation : recommendations) {
           System.out.println(recommendation);
        }
       
    	}




    } */

