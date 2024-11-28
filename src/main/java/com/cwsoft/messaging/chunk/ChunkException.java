package com.cwsoft.messaging.chunk;

public class ChunkException extends RuntimeException {
    public ChunkException(String msg) {
        super(msg);
    }

    public ChunkException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
