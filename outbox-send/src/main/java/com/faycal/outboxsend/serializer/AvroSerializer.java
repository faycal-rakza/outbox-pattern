package com.faycal.outboxsend.serializer;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class AvroSerializer<T extends SpecificRecord> implements Serializer<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvroSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Nothing to configure
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            org.apache.avro.io.DatumWriter<T> writer = 
                new org.apache.avro.specific.SpecificDatumWriter<>(data.getSchema());
            org.apache.avro.io.BinaryEncoder encoder = 
                org.apache.avro.io.EncoderFactory.get().binaryEncoder(outputStream, null);
            writer.write(data, encoder);
            encoder.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Error serializing Avro message", e);
            throw new RuntimeException("Error serializing Avro message", e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
