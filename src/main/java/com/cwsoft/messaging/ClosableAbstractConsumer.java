package com.cwsoft.messaging;

public abstract class ClosableAbstractConsumer<T> extends AbstractConsumer<T> implements ClosableConsumer<T> {
    @Override
    public abstract void close();
}