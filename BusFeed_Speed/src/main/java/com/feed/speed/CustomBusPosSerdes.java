package com.feed.speed;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.demo.jsonmessage.BusPos;

public final class CustomBusPosSerdes {

	static public final class BusPosSerdes extends Serdes.WrapperSerde<BusPos> {

		public BusPosSerdes() {
			super( new JsonSerializer<>(), new JsonDeserializer<BusPos>(BusPos.class));
		}
		
		public static Serde<BusPos> BusPos() {
			return new CustomBusPosSerdes.BusPosSerdes();
		}
	}
}
