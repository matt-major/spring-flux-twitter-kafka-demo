package io.mattmajor.springfluxtwitterkafkademo.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaJsonSerializer implements Serializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonSerializer.class);

    @Override
    public void configure(final Map map, final boolean b) {
    }

    @Override
    public byte[] serialize(final String s, final Object o) {
        final ObjectMapper objectMapper = new ObjectMapper();

        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsBytes(o);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }

        return retVal;
    }

    @Override
    public void close() {
    }
}
