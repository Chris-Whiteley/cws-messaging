package com.cwsoft.messaging;

public interface ClosableProducer<T> extends Producer<T>, AutoCloseable {
    @Override
    void close();
}
