package com.cwsoft.messaging.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class Chunk {
    private final UUID id;
    private final String destination;
    private final String name;
    private int index;
    private int total;
    private final String messageChunk;
}
