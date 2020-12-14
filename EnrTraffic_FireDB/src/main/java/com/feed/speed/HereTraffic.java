package com.feed.speed;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;



import kong.unirest.Unirest;

@Component
public class HereTraffic {
	
	private String HereApiKey ="<Here API KEY>";
	private String HereFlowURL = "https://traffic.ls.hereapi.com/traffic/6.3/flow.json";
	
	
	public HereTraffic() {
		super();
		//traffic_prox(39.6351,-104.89602);
		// TODO Auto-generated constructor stub
	}
	
	public double kmph(double mph) {
		return 1.609344*mph;
	}

	public double traffic_prox(double latitude, double longitude,double speed) {
		 String compURL = HereFlowURL+"?apiKey="+HereApiKey+"&prox="+Double.toString(latitude)+","+Double.toString(longitude)+",1";
		 TrafficParam tfp = new TrafficParam();
		 ArrayList<Double> SPvalues = new ArrayList<Double>();
		 
		 
		 
			 Unirest.get(compURL)
			 .thenConsume(rawResponse -> {
				 try {
				 InputStream stream = rawResponse.getContent();
				 Reader reader = new InputStreamReader(stream, "UTF-8");
				 JSONParser jsonParser = new JSONParser();
				 JSONObject jsonObject = (JSONObject)jsonParser.parse(
				       new InputStreamReader(stream, "UTF-8"));
				 JSONArray RWS = (JSONArray) jsonObject.get("RWS");
				 JSONObject RW = (JSONObject) RWS.get(0);
				 JSONArray FISarray = (JSONArray) RW.get("RW");
				 
				 
				 int n=0;
				 
				 System.out.println(jsonObject.toString());
				 for(int k=0;k<FISarray.size();k++)
				 {
					 JSONObject FIS = (JSONObject) FISarray.get(k);
					 JSONArray FIarray = (JSONArray) FIS.get("FIS");
					 
					 
					 //System.out.println(RW.toString());
					 //System.out.println(FIS.toString());
					 //System.out.println(FIarray.toString());
					 
					 for(int i=0; i<FIarray.size();i++)
					 {
						 JSONObject FI = (JSONObject) FIarray.get(i);
						 JSONArray FIinternal = (JSONArray) FI.get("FI");
						 
						 for(int j=0;j<FIinternal.size();j++) {
							 JSONObject CF = (JSONObject) FIinternal.get(j);
							 JSONArray CFinternal = (JSONArray) CF.get("CF");
							 JSONObject CFObj = (JSONObject) CFinternal.get(0);
							 //System.out.println(CFObj.toString());
							
							 n++;
							 
							 
							 
							 double ff = kmph(Double.parseDouble(CFObj.get("FF").toString()));
							 double cn = kmph(Double.parseDouble(CFObj.get("CN").toString()));
							 double sp = kmph(Double.parseDouble(CFObj.get("SP").toString()));
							 double su = kmph(Double.parseDouble(CFObj.get("SU").toString()));
							 double jf = kmph(Double.parseDouble(CFObj.get("JF").toString()));
							 
							 SPvalues.add(sp);
							 
							 tfp.setAvgFF(ff,n);
							 tfp.setAvgCN(cn,n);
							 tfp.setAvgSP(sp,n);
							 tfp.setAvgSU(su,n);
							 tfp.setAvgJF(jf,n);
							 
							 tfp.setMaxFF(ff);
							 tfp.setMaxCN(cn);
							 tfp.setMaxSP(sp);
							 tfp.setMaxSU(su);
							 tfp.setMaxJF(jf);
							 
							 tfp.setMinFF(ff);
							 tfp.setMinCN(cn);
							 tfp.setMinSP(sp);
							 tfp.setMinSU(su);
							 tfp.setMinJF(jf);
							 
							 //System.out.println(tfp.toString()); 
						 }
					 }
				 }
				 
				
			 }
				 catch(Exception e) {
					System.out.print("Error in traffic_prox : "+e);
				 }
			});
			 
		Collections.sort(SPvalues);
		int per20 = (int) 2*SPvalues.size()/10;
		
		double newAvgSpeed = SPvalues.get(per20);
		
		System.out.println(tfp.toString()); 
		 System.out.println(speed+" "+newAvgSpeed);
		 
		 
		 double score = instScore(tfp,speed,newAvgSpeed);
		 return score;
		
	}
	
	public double instScore(TrafficParam tfp, double speed, double per20speed) {
		if (speed<= tfp.getAvgSP())
			return 10;
		else {
			double expectedSpeed = per20speed;
			double maxSpeed = tfp.getMaxSP();
			maxSpeed =  Math.max(expectedSpeed+15,maxSpeed );
			if (speed>=maxSpeed)
				return 0.0;
			double score = 10.0 - ((speed-expectedSpeed)/(maxSpeed-expectedSpeed))*10;
			return score;
			
		}
	}
	
	
}
