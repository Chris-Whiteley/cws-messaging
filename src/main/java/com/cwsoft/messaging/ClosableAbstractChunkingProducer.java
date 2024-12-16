package com.cwsoft.messaging;

public abstract class ClosableAbstractChunkingProducer<T> extends AbstractChunkingProducer<T> implements ClosableProducer<T>{
    protected ClosableAbstractChunkingProducer(int chunkSize) {
        super(chunkSize);
    }

    public abstract void close();
}
