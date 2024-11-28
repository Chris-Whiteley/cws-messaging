package com.cwsoft.messaging;

import com.cwsoft.messaging.chunk.Chunk;
import com.cwsoft.messaging.chunk.ChunkProducer;
import com.cwsoft.messaging.chunk.MessageToChunk;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractChunkingProducer<T> extends AbstractProducer<T> {

    private final ChunkProducer chunkProducer;

    // Constructor for dependency injection of ChunkProducer
    public AbstractChunkingProducer(int maxMessageSize) {
        this.chunkProducer = new ChunkProducer(maxMessageSize);
    }

    /**
     * Implementation for sending the encoded message to the destination.
     */
    @Override
    protected void sendToDestination(String messageName, String destination, String encodedMessage) {
        var chunks = chunkProducer.toChunks(
                MessageToChunk.builder()
                        .name(messageName)
                        .destination(destination)
                        .message(encodedMessage)
                        .build()
        );

        if (chunks.isEmpty()) {
            log.warn("No chunks created for message [{}] to destination [{}]", messageName, destination);
            return; // Or throw an exception if appropriate
        }

        log.debug("Got {} chunks with a total of {} for sending to destination [{}]",
                chunks.size(), chunks.chunkCount(), destination);

        chunks.forEach(chunk -> {
            log.debug("Sending chunk for message [{}] with index [{}] of size [{}] to destination [{}]",
                    chunk.getName(), chunk.getIndex(), chunk.getMessageChunk().length(), chunk.getDestination());
            sendChunk(chunk);
        });
    }

    /**
     * Abstract method that must be implemented by subclasses to send the chunk.
     */
    abstract void sendChunk(Chunk chunk);
}