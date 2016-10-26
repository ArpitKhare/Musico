package com.sjsu.edu.RecommenderApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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


public class Recommend {
	public static HashMap<Long,Float> generateRecommendation(String tuple) throws TasteException, IOException{
		
		HashMap<Long,Float> hm = new HashMap<Long,Float>();
    	DataModel datamodel = new FileDataModel(new File("data/music_set2.csv"));
    	
    	//Creating UserSimilarity object.
        UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);
     
        //Creating UserNeighbourHHood object.
        UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(0.1, usersimilarity, datamodel);
     
        //Create UserRecomender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);
       
        List<RecommendedItem> recommendations = recommender.recommend(Integer.parseInt(tuple), 2);
        //System.out.println(recommendations);
        for (RecommendedItem recommendation : recommendations) {
           System.out.println(recommendation);
           hm.put(recommendation.getItemID(), recommendation.getValue());
        }
       
    	
		return hm;
		}
	
	public static void writeToCSV(HashMap<Long,Float> recommendation, String user_tuple) throws IOException
	{
		System.out.println("writeToCSV: "+recommendation);
		System.out.println("writeToCSV: "+user_tuple); 
		
		FileWriter pw = new FileWriter("data/music_set.csv",true);
	    Set<Long> s = recommendation.keySet();
	    int i=0;
	    for(Long l : s )
	    { 
	    	if(i<1)
	    	{
	    	pw.append("\n");
	    	pw.append(user_tuple.toLowerCase());
	    	pw.append(",");
	    	pw.append(l.toString());
	    	pw.append(",");
	    	pw.append(recommendation.get(l).toString());
	    	
	    	i++;
	    	}
	    }
	        pw.flush();
	        pw.close();
	}
	}


