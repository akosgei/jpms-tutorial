# ServiceLoader Usage in JPMS

## Overview

JPMS uses the following construct to load all implementations/providers of a service interface:

```java
ServiceLoader<Interface> loader = ServiceLoader.load(Interface.class);
```

`ServiceLoader` scans for providers on the module path. It implements `Iterable<T>`, so it can be looped
in different ways to obtain particular implementations.

---

## Three Ways to Consume a Service Provider

### 1. Iterator Pattern

```java
Iterator<Interface> iterator = ServiceLoader.load(Interface.class).iterator();

while (iterator.hasNext()) {
    iterator.next().callMethodInTheInterface();
}
```

- Uses the classic `Iterator` API
- Useful when you need fine-grained control over iteration (e.g., break early)
- `iterator.next()` both advances and returns the next provider instance

---

### 2. Enhanced For-Loop (Iterable)

```java
ServiceLoader<Interface> loader = ServiceLoader.load(Interface.class);

for (Interface instance : loader) {
    instance.callMethodInTheInterface();
}
```

- Cleaner syntax — works because `ServiceLoader` implements `Iterable<T>`
- ServiceLoader instantiates each provider lazily as the loop advances
- Equivalent to the iterator pattern but more readable

---

### 3. Stream API

```java
ServiceLoader<Interface> loader = ServiceLoader.load(Interface.class);

loader.stream()
      .map(serviceProvider -> serviceProvider.get()) // returns the provider instance
      .forEach(serviceProvider -> serviceProvider.callMethodInTheInterface());
```

- `loader.stream()` returns a `Stream<ServiceLoader.Provider<T>>`
- `.get()` on each `ServiceLoader.Provider` triggers instantiation of that provider
- Enables chaining of stream operations (e.g., `filter`, `sorted`, `map`, `peek`)

#### Real examples from `MessageClient.java`:

**Broadcast to all providers:**
```java
loader.stream()
      .map(ServiceLoader.Provider::get)
      .peek(service -> System.out.println("Using service: " + service))
      .forEach(service -> service.sendMessage(recipient, message));
```

**Pick highest priority provider:**
```java
Optional<MessageService> best = loader.stream()
      .map(ServiceLoader.Provider::get)
      .max(Comparator.comparingInt(MessageService::getPriority));

best.ifPresent(service -> service.sendMessage(recipient, message));
```

---

### 4. `findFirst()` — Returns an Optional

```java
ServiceLoader<Interface> loader = ServiceLoader.load(Interface.class);

Optional<Interface> first = loader.findFirst();

first.ifPresent(service -> service.callMethodInTheInterface());
```

- `findFirst()` returns an `Optional<T>` containing the first available provider, or `Optional.empty()` if none are found
- Useful when you only need **one** implementation and don't care which one
- Avoids the need to iterate when a single provider is sufficient
- Safely handles the case where no provider is registered (no `NoSuchElementException`)

---

## Comparison

| Approach          | API Used                  | Best For                                                  |
|-------------------|---------------------------|-----------------------------------------------------------|
| Iterator          | `Iterator<T>`             | Manual control, early exit                                |
| Enhanced for-loop | `Iterable<T>`             | Simple iteration over all providers                       |
| Stream            | `Stream<Provider<T>>`     | Filtering, sorting, mapping, chaining ops                 |
| `findFirst()`     | `Optional<T>`             | Retrieving a single provider safely without full iteration |

---

## Key Notes

- `ServiceLoader.load()` does **not** instantiate providers immediately — it is **lazy**
- Each call to `ServiceLoader.load()` creates a **new loader instance**; reuse the same loader to avoid duplicate instantiation
- For the **Provider Method Pattern** (`public static provider()` method), ServiceLoader calls the method instead of the constructor
- For the **Direct Implementation Pattern** (no `provider()` method), ServiceLoader calls the **public no-arg constructor** via reflection

