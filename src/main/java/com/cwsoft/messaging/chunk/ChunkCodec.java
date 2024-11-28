package com.cwsoft.messaging.chunk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class ChunkCodec {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Chunk decode(String jsonChunk) {
        try {
            ObjectNode objectNode = (ObjectNode) OBJECT_MAPPER.readTree(jsonChunk);
            UUID id = UUID.fromString(objectNode.get("id").asText());
            int index = objectNode.get("idx").asInt();
            int total = objectNode.get("ttl").asInt();
            String messageChunk = objectNode.get("msg").asText();
            return Chunk.builder()
                    .id(id)
                    .index(index)
                    .total(total)
                    .messageChunk(messageChunk)
                    .build();
        } catch (IOException ex) {
            log.error("Error decoding Event from JSON string {}", jsonChunk, ex);
            return null;
        }
    }

    public String encode(Chunk chunk) {
        ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        objectNode.put("id", chunk.getId().toString());
        objectNode.put("idx", chunk.getIndex());
        objectNode.put("ttl", chunk.getTotal());
        objectNode.put("msg", chunk.getMessageChunk());
        return objectNode.toString();
    }
}
