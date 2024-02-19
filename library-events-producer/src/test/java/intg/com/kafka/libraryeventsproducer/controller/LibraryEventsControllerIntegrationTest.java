package com.kafka.libraryeventsproducer.controller;

import com.kafka.libraryeventsproducer.domain.LibraryEvent;
import com.kafka.libraryeventsproducer.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = "library-events")
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
class LibraryEventsControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    // Configure embeddedKafkaBroker, - Done
    //Override the kafka producer bootstrap address to the embedded broker ones - Done

    // Configure a Kafka consumer in the test case
    // Wire KafkaConsumer and EmbeddedKafkaBroker
    // Consume the record from the EmbeddedkafkaBroker and then assert on it.


    @Test
    void postLibraryEvent() {
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON.toString());

        var httpEntity = new HttpEntity<>(TestUtil.libraryEventRecord(), httpHeaders);
        //when
        var responseEntity = restTemplate
                .exchange("/v1/libraryevent", HttpMethod.POST,
                        httpEntity, LibraryEvent.class);

        //then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }
}