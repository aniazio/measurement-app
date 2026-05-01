package org.example.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.config.AppConfig;
import org.example.demo.entity.Measurement;
import org.example.demo.repository.MeasurementRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeasurementRepositoryImpl implements MeasurementRepository {

    private final KafkaTemplate<UUID, Measurement> kafkaTemplate;

    @Override
    public void save(Measurement measurement) {
        kafkaTemplate.send(AppConfig.METEOROLOGY_TOPIC, measurement.getCityId(), measurement)
                .whenComplete(this::handleException);
    }

    private void handleException(SendResult<UUID, Measurement> sendResult, Throwable throwable) {
        if (throwable != null) {
            log.error("Error while sending message", throwable);
        } else {
            log.info("Message {} sent successfully", sendResult);
        }
    }
}
