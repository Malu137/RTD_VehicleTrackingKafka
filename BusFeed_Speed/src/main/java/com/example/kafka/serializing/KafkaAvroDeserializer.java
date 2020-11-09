/**
 * Copyright (C) Cloudera, Inc. 2018
 */
package com.example.kafka.serializing;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feed.speed.BusPosition;

import java.io.*;
import java.util.Map;

/**
 * {@link Deserializer} implementation that converts byte arrays to {@link org.apache.avro.generic.GenericData.Record} objects.
 * The following configuration is needed<ul>
 *     <li>{@code schemaProviderFactory=<factory_class_name>} for schema discovery</li>
 *     <li>{@code schemaversion.<schema_name>=<schema_version>} for reader schema versions</li>
 * </ul>
 */
public class KafkaAvroDeserializer<T extends GenericContainer> implements Deserializer<GenericData.Record> {


	private static final Logger LOG = LoggerFactory.getLogger(KafkaAvroDeserializer.class);

    @Override
    public GenericData.Record deserialize(String topic, byte[] data) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {

            GenericData.Record avroRecord = readAvroRecord(stream);
            LOG.debug("Message Deserialised");

            return avroRecord;
        } catch (Exception e) {
        	LOG.debug("Could not serialize data");
            throw new SerializationException("Could not deserialize data", e);
        }
    }



    private GenericData.Record readAvroRecord(InputStream stream) throws IOException {
        DatumReader<Object> datumReader = new GenericDatumReader<>();
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(stream, null);
        GenericData.Record record = new GenericData.Record(BusPosition.BusPositionSchema());
        datumReader.read(record, decoder);
        return record;
    }




}
