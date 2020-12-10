package com.feed.speed;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.transit.realtime.GtfsRealtime;



@SpringBootApplication
public class PbParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PbParserApplication.class, args);
		File initialFile = new File("pbfiles/Alerts_1.pb");
	    try{
	    	InputStream stream = new FileInputStream(initialFile);
	    	GtfsRealtime.FeedMessage feed = GtfsRealtime.FeedMessage.parseFrom(stream);
	    	Path path =Paths.get("pbfiles/Alerts_1.txt");
	    	byte[] Feedmsg = feed.toString().getBytes();
	    	Files.write(path,Feedmsg);
	    	stream.close();
	    }catch(Exception e) {
	    	System.out.println("Error Occuresd : "+e);
	    }
	    
	    
	}

}
