package org.example.demo.repository.impl.serdes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.example.demo.entity.FullStats;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomSerdes {

    public static Serde<FullStats> fullStatsSerde() {
        JsonSerializer<FullStats> serializer = new JsonSerializer<>();
        JsonDeserializer<FullStats> deserializer = new JsonDeserializer<>(FullStats.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

}
