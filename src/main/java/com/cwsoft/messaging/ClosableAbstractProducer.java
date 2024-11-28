package com.cwsoft.messaging;

public abstract class ClosableAbstractProducer<T> extends AbstractProducer<T> implements ClosableProducer<T>{
    public abstract void close();
}
