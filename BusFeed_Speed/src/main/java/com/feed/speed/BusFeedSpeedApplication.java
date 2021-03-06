package com.feed.speed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.demo.jsonmessage.BusPos;
import com.example.demo.jsonmessage.CustomBusPosSerdes;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;


@SpringBootApplication
public class BusFeedSpeedApplication {
	
	private static Logger LOG = LoggerFactory.getLogger(BusFeedSpeedApplication.class);

    private static HashMap<String, BusPos> previousBusPositionMap = new HashMap<String, BusPos>();

	public static void main(String[] args) {
		SpringApplication.run(BusFeedSpeedApplication.class, args);
		LOG.debug("BusFeed_Speed Application has started");
    	Properties props = new Properties();
    	props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    	props.put(StreamsConfig.APPLICATION_ID_CONFIG, "BusFeed_Speed");
    	props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,Serdes.String().getClass().getName());
    	props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,CustomBusPosSerdes.BusPosSerdes.class );
    	props.put(JsonDeserializer.TRUSTED_PACKAGES , "*" );
    	
    	//props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.ByteArray().getClass().getName());

    	
    	final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, BusPos> rtdBusPositionStream = builder.stream("events");
        
        rtdBusPositionStream.print(Printed.toSysOut());
        
        rtdBusPositionStream.mapValues(value -> {
            value = BusFeedSpeedApplication.calcBusPosSpeed(value);
            return value;
        }).to("events2");
        
        final Topology topology = builder.build();
        
        final KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        
    
	}
	
    private static BusPos calcBusPosSpeed(BusPos busPosition) {

        // current bus ID
        String busId = busPosition.getVehicleID();

        // if there is a previous location for that bus ID, calculate the speed based on its previous position/timestamp.
        if (previousBusPositionMap.containsKey(busId)){
            BusPos previousBusPosition = previousBusPositionMap.get(busId);

            // calculate distance and time between last two measurements
            HaversineDistanceCalculator haversineDistanceCalculator = new HaversineDistanceCalculator();
            double distance = haversineDistanceCalculator.calculateDistance(
                    previousBusPosition.getLatitude(),
                    previousBusPosition.getLongitude(),
                    busPosition.getLatitude(),
                    busPosition.getLongitude()); // distance is in kilometers

            long timedelta = busPosition.getTimestamp() - previousBusPosition.getTimestamp(); // time delta is in seconds
            double kmph = calculateKMPH(distance, timedelta);
            
            busPosition.setDistance(distance);
            busPosition.setSpeed(kmph);

        }

        previousBusPositionMap.put(busId, busPosition);

        return busPosition;

    }
    
    private static double calculateKMPH(double km, long seconds) {
        if (seconds == 0){
            return 0;
        } else {
            double kmph = km*60*60 / seconds;
            return kmph;
        }
    }

}
