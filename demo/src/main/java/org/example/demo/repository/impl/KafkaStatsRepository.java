package org.example.demo.repository.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.SlidingWindows;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.example.demo.config.AppConfig;
import org.example.demo.entity.FullStats;
import org.example.demo.entity.Stats;
import org.example.demo.model.MeasurementDto;
import org.example.demo.repository.StatsRepository;
import org.example.demo.repository.impl.serdes.CustomSerdes;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaStatsRepository implements StatsRepository {

    private final StreamsBuilder streamsBuilder;
    private KafkaStreams streams;

    @PostConstruct
    public void process() {
        KStream<UUID, MeasurementDto> input = streamsBuilder.stream(AppConfig.METEOROLOGY_TOPIC);
        input.groupByKey()
                .windowedBy(SlidingWindows.ofTimeDifferenceWithNoGrace(Duration.ofHours(3)))
                .aggregate(
                        FullStats::new,
                        (key, value, aggregate) -> {
                            aggregate.addStats(value.getNo2(), value.getCo(), value.getPm10());
                            return aggregate;
                        },
                        Materialized.with(Serdes.UUID(), CustomSerdes.fullStatsSerde())
                )
                .toStream()
                .to(AppConfig.STATS_TOPIC);

        streams = new KafkaStreams(streamsBuilder.build(), new Properties());
        streams.start();
    }

    @Override
    public FullStats get3hStats(UUID cityId) {
        ReadOnlyWindowStore<UUID, FullStats> windowStore =
                streams.store(StoreQueryParameters.fromNameAndType(
                        AppConfig.STATS_TOPIC,
                        QueryableStoreTypes.windowStore()
                ));

        return windowStore.backwardFetch(cityId, Instant.now().minus(Duration.ofHours(3)), Instant.now()).next().value;
    }

    @Override
    public List<Stats> getLastMonthTop10NoUsage() {
        //TODO
        return List.of();
    }
}
