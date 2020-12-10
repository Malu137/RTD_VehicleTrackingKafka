package com.feed.speed;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import com.example.demo.jsonmessage.BusPos;
import com.example.demo.jsonmessage.CustomBusPosSerdes;


@Service
public class FeedStreamer {
	
	@Autowired
	FBInitialize fbHandle;
	
	

	@PostConstruct
	public void initialize() {
		Properties props = new Properties();
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    	props.put(StreamsConfig.APPLICATION_ID_CONFIG, "EnrTraffic_FireDB");
    	props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,Serdes.String().getClass().getName());
    	props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,CustomBusPosSerdes.BusPosSerdes.class );
    	props.put(JsonDeserializer.TRUSTED_PACKAGES , "*" );

    	final StreamsBuilder builder = new StreamsBuilder();

    	try {
        KStream<String, BusPos> BusSpeedStream = builder.stream("events2");
        BusSpeedStream.foreach((key,value)-> BusProcess(value));
                
        final Topology topology = builder.build();
        
        final KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error occured:"+e);
    	}
	}
	
	private void BusProcess(BusPos buspos)
	{
		HereTraffic ht = new HereTraffic();
		System.out.println(buspos.toString());
		
		if (buspos.getRouteId() == null || buspos.getRouteId()=="" || buspos.getRouteId()==" ")
		{
			try {
			ActiveBus prevBuspos = this.fbHandle.get(buspos.getVehicleID());
			if(prevBuspos==null) {
				//do nothing
			}
			else {
				
				ActiveBus endbus = this.fbHandle.get(buspos.getVehicleID());
				CompTrip comptrip = new CompTrip();
					comptrip.setVehicleID(endbus.getVehicleID());
					comptrip.setTripId(endbus.getTripId());
					comptrip.setRouteId(endbus.getRouteId());
					comptrip.setStartTime(endbus.getStartTime());
					comptrip.setEndTime(endbus.getEndTime());
					comptrip.setAvgSpeed(endbus.getAvgSpeed());
					comptrip.setOverspeedCount(endbus.getOverspeedCount());
					comptrip.setTime(endbus.getTime());
					comptrip.setTripDistance(endbus.getTripDistance());
					comptrip.setCumScore(endbus.getCumScore());
					
				this.fbHandle.deleteActive(buspos.getVehicleID());
				this.fbHandle.postCompTrip(buspos.getVehicleID(), buspos.getTripId(), comptrip);
				
					
				
			}
			}
			catch(Exception e) {
				System.out.println("Error : "+e);
			}
		}
		else
		{
		try {
			System.out.println("Inside BusProcess..." + buspos.getVehicleID());
			ActiveBus prevBuspos = this.fbHandle.get(buspos.getVehicleID());
			
			
			if(prevBuspos == null)
			{
				// starting a trip now
				//generate alert for starting trip
				
				ActiveBus newChild = new ActiveBus();
					newChild.setVehicleID(buspos.getVehicleID());
					newChild.setTimestamp(buspos.getTimestamp());
					newChild.setLatitude(buspos.getLatitude());
					newChild.setLongitude(buspos.getLongitude());
					newChild.setRouteId(buspos.getRouteId());
					newChild.setTripId(buspos.getTripId());
					newChild.setDistance(buspos.getDistance());
					newChild.setSpeed(buspos.getSpeed());
					newChild.setTime(0);
					newChild.setTripDistance(0);
					newChild.setOverspeedCount(0);
					newChild.setCumScore(0);
					newChild.setInstantScore(0);
					newChild.setStartTime(buspos.getTimestamp());
					newChild.setEndTime(0);
					newChild.setCounter(0);
					newChild.setStagCounter(0);
				
			
					
				this.fbHandle.postAlert(buspos.getVehicleID()+": Trip - "+buspos.getTripId()+" has started.", "StartTrip");
					
				 this.fbHandle.post(buspos.getVehicleID(), newChild);
				 
						
			}
			else
			{
				//Checking if its continuing trip
				if(prevBuspos.getRouteId().compareTo(buspos.getRouteId()) == 0)
				{
					prevBuspos.setTripDistance(prevBuspos.getTripDistance()+buspos.getDistance());
					prevBuspos.setTime(
							prevBuspos.getTime() - prevBuspos.getTimestamp() +buspos.getTimestamp()
							);
					prevBuspos.setEndTime(buspos.getTimestamp());
					prevBuspos.setDistance(buspos.getDistance());
					prevBuspos.setSpeed(buspos.getSpeed());
					prevBuspos.setLongitude(buspos.getLongitude());
					prevBuspos.setLatitude(buspos.getLatitude());
					prevBuspos.setTimestamp(buspos.getTimestamp());
					prevBuspos.setEndTime(buspos.getTimestamp());
					
					
					
					double instscore = ht.traffic_prox(buspos.getLatitude(), buspos.getLongitude(), buspos.getSpeed());
					prevBuspos.setInstantScore(instscore);
					prevBuspos.setCumScore(
							(prevBuspos.getCumScore()*prevBuspos.getCounter() + instscore)/(prevBuspos.getCounter()+1)
							);
					prevBuspos.setCounter(prevBuspos.getCounter()+1);
					if (instscore<=3)
					{
						prevBuspos.setOverspeedCount(prevBuspos.getOverspeedCount()+1);
						//generate alert for overspeeding
						this.fbHandle.postAlert(buspos.getVehicleID()+": Bus has overpeeded", "OverSpeed");
					}
					
					if (buspos.getDistance()==0)
					{
						if (prevBuspos.getStagCounter()==5)
						{
							System.out.println("Ending bus trip as its stationary");
							//Ending trip
							this.fbHandle.postAlert(buspos.getVehicleID()+": Trip - "+buspos.getTripId()+" has ended as its stationary.", "EndTrip-S");
							
							CompTrip comptrip = new CompTrip();
								comptrip.setVehicleID(prevBuspos.getVehicleID());
								comptrip.setTripId(prevBuspos.getTripId());
								comptrip.setRouteId(prevBuspos.getRouteId());
								comptrip.setStartTime(prevBuspos.getStartTime());
								comptrip.setEndTime(prevBuspos.getEndTime());
								comptrip.setAvgSpeed(prevBuspos.getAvgSpeed());
								comptrip.setOverspeedCount(prevBuspos.getOverspeedCount());
								comptrip.setTime(prevBuspos.getTime());
								comptrip.setTripDistance(prevBuspos.getTripDistance());
								comptrip.setCumScore(prevBuspos.getCumScore());
							
							
							this.fbHandle.postCompTrip(prevBuspos.getVehicleID(), prevBuspos.getTripId(), comptrip);
							prevBuspos.setStagCounter(6);
							this.fbHandle.post(buspos.getVehicleID(), prevBuspos);
							
							
							
						}
						else if(prevBuspos.getStagCounter()==6) {
							//do nothing
							
						}
						else {
							prevBuspos.setStagCounter(prevBuspos.getStagCounter()+1);
							//Updating value
							this.fbHandle.post(buspos.getVehicleID(), prevBuspos);
						}
					}
					
					
					
				}
				//Ending the current trip and starting new one
				else {
					ActiveBus endbus = this.fbHandle.get(buspos.getVehicleID());
					CompTrip comptrip = new CompTrip();
						comptrip.setVehicleID(endbus.getVehicleID());
						comptrip.setTripId(endbus.getTripId());
						comptrip.setRouteId(endbus.getRouteId());
						comptrip.setStartTime(endbus.getStartTime());
						comptrip.setEndTime(endbus.getEndTime());
						comptrip.setAvgSpeed(endbus.getAvgSpeed());
						comptrip.setOverspeedCount(endbus.getOverspeedCount());
						comptrip.setTime(endbus.getTime());
						comptrip.setTripDistance(endbus.getTripDistance());
						comptrip.setCumScore(endbus.getCumScore());
						
					this.fbHandle.deleteActive(buspos.getVehicleID());
					

					
					if (endbus.getRouteId() == null || endbus.getTripId()==null ||endbus.getRouteId()=="" || endbus.getRouteId()==" " || endbus.getTripId()=="") {
						//dont add to completedTrips
					}
					else {
						//generate alert for ending trip
						this.fbHandle.postCompTrip(endbus.getVehicleID(), endbus.getTripId(), comptrip);
						this.fbHandle.postAlert(endbus.getVehicleID()+": Trip - "+endbus.getTripId()+" has ended.", "EndTrip");
					}
					
					//generate alert for staring trip
					ActiveBus newChild = new ActiveBus();
						newChild.setVehicleID(buspos.getVehicleID());
						newChild.setTimestamp(buspos.getTimestamp());
						newChild.setLatitude(buspos.getLatitude());
						newChild.setLongitude(buspos.getLongitude());
						newChild.setRouteId(buspos.getRouteId());
						newChild.setTripId(buspos.getTripId());
						newChild.setDistance(buspos.getDistance());
						newChild.setSpeed(buspos.getSpeed());
						newChild.setTime(0);
						newChild.setTripDistance(0);
						newChild.setOverspeedCount(0);
						newChild.setCumScore(0);
						newChild.setInstantScore(0);
						newChild.setStartTime(buspos.getTimestamp());
						newChild.setEndTime(0);
						newChild.setStagCounter(0);
				
					
				 this.fbHandle.post(buspos.getVehicleID(), newChild);
				 this.fbHandle.postAlert(newChild.getVehicleID()+": Trip - "+newChild.getTripId()+" has started.", "StartTrip");	
					
					
					
				}
				
				
			}
			
		}
		catch (Exception e)
		{
			System.out.println("Error" + e);
		}	
		}
	}


}

