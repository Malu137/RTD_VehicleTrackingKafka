/**
 * Copyright (C) Cloudera, Inc. 2018
 */
package com.example.kafka.serializing;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
/**
 * {@link Serializer} implementation that converts byte arrays to {@link org.apache.avro.generic.GenericData.Record} objects.
 * The following configuration is needed<ul>
 *     <li>{@code schemaProviderFactory=<factory_class_name>} for schema discovery</li>
 *     <li>{@code schemaversion.<schema_name>=<schema_version>} for reader schema versions</li>
 * </ul>
 */
public class KafkaAvroSerializer<T extends GenericContainer> implements Serializer<T> {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaAvroSerializer.class);

    @Override
    public byte[] serialize(String topic, T data) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            writeSerializedAvro(stream, data, data.getSchema());
            LOG.debug("Message Serialised");

            return stream.toByteArray();
        } catch (Exception e) {
        	LOG.debug("Could not serialize data");
            throw new SerializationException("Could not serialize data", e);
        }
    }

    private void writeSerializedAvro(ByteArrayOutputStream stream, T data, Schema schema) throws IOException {
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
        DatumWriter<T> datumWriter = new GenericDatumWriter<>(schema);
        datumWriter.write(data, encoder);
        encoder.flush();
    }


}
