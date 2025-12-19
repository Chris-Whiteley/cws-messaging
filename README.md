
---
# CWS Messaging

A lightweight, transport-agnostic **messaging abstraction library** providing common interfaces and base classes for producing, consuming, and streaming messages.

This module is intended to be used as a **foundation** for concrete messaging implementations (e.g. Kafka, JMS, SQS), not as a messaging system itself.

---

## Goals

* Provide **simple, consistent interfaces** for messaging
* Avoid coupling to any specific broker or framework
* Encourage clean separation between **API** and **implementation**
* Support safe resource handling via `AutoCloseable`

---

## Core Concepts

### Producer

A `Producer<T>` is responsible for publishing messages of type `T`.

```java
Producer<MyMessage> producer = ...;
producer.send(message);
```

For implementations that hold resources (connections, threads, etc.), use:

```java
ClosableProducer<T>
```

which extends `AutoCloseable`.

---

### Consumer

A `Consumer<T>` retrieves messages in a **pull-based** manner:

```java
Optional<T> message = consumer.consume(Duration.ofSeconds(5));
```

* Blocks until a message is available or the timeout expires
* Returns `Optional.empty()` on timeout
* Throws a runtime exception on failure

For resource-aware implementations, use:

```java
ClosableConsumer<T>
```

---

### Abstract Base Classes

To reduce boilerplate, the library provides abstract base classes:

#### `AbstractProducer<T>`

Provides:

* Common send workflow
* Logging
* Exception handling

Requires implementations to define:

* `getDestination(T message)`
* `getMessageName(T message)`
* Message encoding and dispatch logic

#### `AbstractConsumer<T>`

Provides:

* Timeout-based consumption workflow
* Logging and error handling

Requires implementations to define:

* How messages are retrieved and decoded

Closable variants are also provided:

* `ClosableAbstractProducer<T>`
* `ClosableAbstractConsumer<T>`

---

### Stream API

The `Stream<K, V>` interface represents a **continuous message stream** abstraction.

```java
stream
    .filter((key, value) -> value.isValid())
    .forEach((key, value) -> process(value));

stream.start();
```

Supports:

* Filtering
* Iteration
* Lifecycle control (`start`, `stop`, `close`)

This interface is intentionally minimal to allow different streaming backends.

---

## Exceptions

The library uses **unchecked exceptions** to avoid leaking transport details:

* `MessageProductionException`
* `MessageConsumptionException`

These wrap underlying failures while keeping the API clean and consistent.

---

## Logging

* Uses **SLF4J API only**
* Does **not** include or enforce any logging implementation

Consumers of this library are expected to provide their own logging backend
(e.g. Logback, Log4j2).

---

## Java Version

* Java **21**

---

## Intended Usage

This library is **not** a standalone messaging solution.

It is designed to be:

* Extended by transport-specific modules (e.g. Kafka)
* Consumed by applications that want a consistent messaging API

Example downstream modules:

* `cws-event-router-kafka`
* `cws-kafka-messaging`

---

## Example Implementations

Concrete implementations should:

* Extend the provided abstract classes
* Handle transport-specific concerns
* Manage lifecycle and resources responsibly

---

## Build

```bash
mvn clean install
```

---

## Publishing

Artifacts are published to **GitHub Packages**.

Maintainers can deploy using:

```bash
mvn clean deploy
```

---

## Design Philosophy

> Keep the core API small, explicit, and transport-agnostic.
> Push complexity to the edges.

---

## License

(Insert license here if applicable)

---

