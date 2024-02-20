package com.kafka.libraryeventsproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.libraryeventsproducer.domain.LibraryEvent;
import com.kafka.libraryeventsproducer.domain.LibraryEventType;
import com.kafka.libraryeventsproducer.producer.LibraryEventsProducer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LibraryEventsController {

    private final LibraryEventsProducer libraryEventsProducer;

    public LibraryEventsController(LibraryEventsProducer libraryEventsProducer) {
        this.libraryEventsProducer = libraryEventsProducer;
    }

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(
            @RequestBody @Valid LibraryEvent libraryEvent
    ) throws JsonProcessingException{
        log.info("libraryEvent: {}", libraryEvent);

        //libraryEventsProducer.sendLibraryEvent(libraryEvent); // asynchronous approach
        //libraryEventsProducer.sendLibraryEvent_approach2(libraryEvent);   // synchronous approach
        libraryEventsProducer.sendLibraryEvent_approach3(libraryEvent); // asynchronous approach

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("/v1/libraryevent")
    public ResponseEntity<?> updateLibraryEvent(
            @RequestBody @Valid LibraryEvent libraryEvent
    ) throws JsonProcessingException {
        log.info("libraryEvent: {}", libraryEvent);

        ResponseEntity<String> BAD_REQUEST = validateLibraryEvent(libraryEvent);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        libraryEventsProducer.sendLibraryEvent_approach3(libraryEvent);

        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }

    private static ResponseEntity<String> validateLibraryEvent(LibraryEvent libraryEvent) {
        if(libraryEvent.libraryEventId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the LibraryEventId");
        }

        if(!libraryEvent.libraryEventType().equals(LibraryEventType.UPDATE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only UPDATE event type is supported");
        }
        return null;
    }

}
