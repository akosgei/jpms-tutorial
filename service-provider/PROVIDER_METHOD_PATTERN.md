# EventService - Provider Method Pattern Implementation

## Overview

This implementation demonstrates the **Provider Method Pattern** for Java Service Provider Interface (SPI) in JPMS.

## Implementation Details

### Two Classes Created:

1. **EventService.java** - The actual service implementation
   - Implements `MessageService` interface
   - Package-private constructor (controlled instantiation)
   - Tracks instance ID and message count
   - Demonstrates stateful service behavior

2. **EventServiceProvider.java** - The provider factory class
   - Contains a `public static MessageService provider()` method
   - Implements singleton pattern (reuses same instance)
   - Controls service instantiation logic
   - Registered in module-info.java (not EventService directly)

## How It Works

### Traditional Pattern (Direct Provider):
```java
// module-info.java
provides MessageService with EmailService;

// ServiceLoader directly instantiates EmailService
ServiceLoader.load(MessageService.class); // calls new EmailService()
```

### Provider Method Pattern:
```java
// module-info.java
provides MessageService with EventServiceProvider;

// ServiceLoader detects provider() method and calls it
ServiceLoader.load(MessageService.class); // calls EventServiceProvider.provider()
```

## Key Requirements for Provider Method

The provider method MUST be:
- **public** - accessible to ServiceLoader
- **static** - no instance needed
- **named "provider"** - exact name required
- **return type** - must match the service interface (MessageService)
- **no parameters** - takes no arguments

## Benefits of Provider Method Pattern

1. **Lazy Initialization** - Service created only when requested
2. **Singleton Pattern** - Can reuse same instance (shown in EventServiceProvider)
3. **Complex Construction** - Allows sophisticated initialization logic
4. **Dependency Management** - Can inject dependencies or configuration
5. **Instance Control** - Full control over instance lifecycle
6. **Factory Pattern** - Can return different implementations based on conditions

## Module Configuration

```java
// service-provider/src/main/java/module-info.java
module com.jpms.provider {
    requires com.jpms.core;
    
    provides com.jpms.core.api.MessageService with
        com.jpms.provider.EmailService,              // Direct instantiation
        com.jpms.provider.SmsService,                // Direct instantiation
        com.jpms.provider.PushNotificationService,   // Direct instantiation
        com.jpms.provider.EventServiceProvider;      // Provider method pattern
}
```

## Testing the Implementation

When a consumer module uses ServiceLoader:

```java
ServiceLoader<MessageService> loader = ServiceLoader.load(MessageService.class);
for (MessageService service : loader) {
    System.out.println(service.getServiceName());
    // Will output: "Event Service (Provider Method Pattern)"
}
```

The EventServiceProvider.provider() method will be called automatically, and it will:
1. Check if a singleton instance exists
2. If not, create a new EventService with a unique instance ID
3. Return the instance to ServiceLoader
4. Subsequent calls will return the same singleton instance

## Advantages Over Direct Instantiation

| Aspect | Direct Instantiation | Provider Method |
|--------|---------------------|-----------------|
| Instantiation | Public no-arg constructor required | Any constructor can be used |
| Control | Limited | Full control over creation |
| State | Stateless (new instance each time) | Can maintain state (singleton) |
| Initialization | Simple only | Complex logic supported |
| Performance | Multiple instances | Can optimize with pooling/caching |

## Alternative: Non-Singleton Version

If you want a new instance for each ServiceLoader iteration, modify the provider() method:

```java
public static MessageService provider() {
    instanceCounter++;
    String instanceId = "EVT-" + instanceCounter + "-" + System.currentTimeMillis();
    return new EventService(instanceId);
}
```

This demonstrates the flexibility of the provider method pattern!

