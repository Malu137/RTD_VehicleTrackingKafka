package com.feed.speed;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnrTrafficFireDbApplication {
	
	private static Logger LOG = LoggerFactory.getLogger(EnrTrafficFireDbApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EnrTrafficFireDbApplication.class, args);	
		LOG.debug("EnrTraffic_FireDB Application has started");	
		
		
    }
	
	

}
