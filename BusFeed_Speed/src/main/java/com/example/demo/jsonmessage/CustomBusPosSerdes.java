package com.example.demo.jsonmessage;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CustomBusPosSerdes {

	static public final class BusPosSerdes extends Serdes.WrapperSerde<BusPos> {

		public BusPosSerdes() {
			super( new BusPosSerializer<>(), new BusPosDeserializer<BusPos>(BusPos.class));
		}
		
		public static Serde<BusPos> BusPos() {
			return new CustomBusPosSerdes.BusPosSerdes();
		}
	}
}
