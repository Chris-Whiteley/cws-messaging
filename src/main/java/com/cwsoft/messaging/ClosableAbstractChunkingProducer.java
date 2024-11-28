package com.cwsoft.messaging;

public abstract class ClosableAbstractChunkingProducer<T> extends AbstractChunkingProducer<T> implements ClosableProducer<T>{
    public ClosableAbstractChunkingProducer(int maxMessageSize) {
        super(maxMessageSize);
    }

    public abstract void close();
}
