package com.feed.speed;


import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Component;

@Component
public class BusPosition {
	
	private static Schema schema = BusPosition.BusPositionSchema();
	public static Schema BusPositionSchema() {
		Schema schema = SchemaBuilder.record("BusPosition").namespace("com.example.demo").fields()
				.name("id").type().optional().stringType()
				.name("timestamp").type().optional().longType()
				.name("longitude").type().optional().doubleType()
				.name("latitude").type().optional().doubleType()
				.name("speed").type().optional().doubleType()
				.endRecord();
		return schema;
	}
	
	public GenericRecord getGenericRecord(String id, long ts, double lon,double lat,double speed) {
		GenericRecord record = new GenericData.Record(BusPosition.schema);
		try {
			record.put("id", id);
			record.put("timestamp", ts);
			record.put("longitude", lon);
			record.put("latitude", lat);
			record.put("speed", speed);
		}catch(Exception e) {;
			System.out.println("Avro record could not be generated");
			//throw new Exception("Avro record could not be generated");
		}
		return record;
		
	}
	
	
	
	
}


