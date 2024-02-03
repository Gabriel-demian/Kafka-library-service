package com.kafka.libraryeventsproducer.domain;

public record LibraryEvent(

        Integer libraryEventId,

        LibraryEventType libraryEventType,
        Book book

) {
}
