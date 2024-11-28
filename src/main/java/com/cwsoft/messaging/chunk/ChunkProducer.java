package com.cwsoft.messaging.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkProducer {
    private final int maxChunkMsgSize;

    public ChunkProducer(int maxMessageSize) {
        if (maxMessageSize <= 9) {
            throw new IllegalArgumentException("maxMessageSize must be 10 or greater");
        }
        if (maxMessageSize % 2 != 0) {
            throw new IllegalArgumentException("maxMessageSize must be divisible by 2");
        }
        this.maxChunkMsgSize = maxMessageSize / 2;
    }

    public Chunks toChunks(MessageToChunk messageToChunk) {
        String message = messageToChunk.getMessage();
        int messageLength = message.length();
        List<String> messageChunks = new ArrayList<>();

        for (int start = 0; start < messageLength; start += maxChunkMsgSize) {
            int end = Math.min(start + maxChunkMsgSize, messageLength);
            messageChunks.add(message.substring(start, end));
        }

        Chunks chunks = new Chunks();
        UUID chunksId = UUID.randomUUID();

        for (int i = 0; i < messageChunks.size(); i++) {
            chunks.add(
                    Chunk.builder()
                            .id(chunksId)
                            .name(messageToChunk.getName())
                            .destination(messageToChunk.getDestination())
                            .index(i)
                            .total(messageChunks.size())
                            .messageChunk(messageChunks.get(i))
                            .build()
            );
        }
        return chunks;
    }

}
