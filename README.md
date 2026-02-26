# Java Platform Module System (JPMS) Demo

This project demonstrates all the key directives and features of the Java Platform Module System (JPMS), introduced in Java 9.

## Project Structure

```
JPMSDemo/
├── core-api/           # Demonstrates: exports, exports...to (qualified exports)
├── service-provider/   # Demonstrates: provides...with (Service Provider)
├── service-consumer/   # Demonstrates: uses (Service Consumer)
├── reflection-module/  # Demonstrates: opens, opens...to
├── transitive-module/  # Demonstrates: requires transitive, requires static
├── open-module/        # Demonstrates: open module (entire module open for reflection)
└── app/                # Main application combining all modules
```

## JPMS Directives Demonstrated

### 1. `exports` - Basic Export
Makes a package available to all other modules.
```java
exports com.jpms.core.api;
```

### 2. `exports ... to` - Qualified Export
Makes a package available only to specific modules.
```java
exports com.jpms.core.internal to com.jpms.reflection;
```

### 3. `requires` - Module Dependency
Declares a dependency on another module.
```java
requires com.jpms.core;
```

### 4. `requires transitive` - Transitive Dependency
Declares a dependency that is also passed to modules depending on this one.
```java
requires transitive com.jpms.core;
```

### 5. `requires static` - Optional/Compile-time Dependency
Declares a dependency needed at compile time but optional at runtime.
```java
requires static com.optional.module;
```

### 6. `uses` - Service Consumer
Declares that this module uses a service interface.
```java
uses com.jpms.core.api.MessageService;
```

### 7. `provides ... with` - Service Provider
Declares an implementation for a service interface.
```java
provides com.jpms.core.api.MessageService with com.jpms.provider.EmailService;
```

### 8. `opens` - Open Package for Reflection
Opens a package for deep reflection (runtime access).
```java
opens com.jpms.reflection to com.jpms.app;
```

### 9. `open module` - Open Module
Opens the entire module for reflection.
```java
open module com.jpms.openmodule { }
```

## Building and Running

```bash
# Build all modules
mvn clean compile

# Run the application
mvn exec:java -pl app
```

## Module Dependency Graph

```
                          ╔═══════════════════╗
                          ║   com.jpms.app    ║
                          ║    (main app)     ║
                          ╚════════╤══════════╝
                                   │
            ┌──────────────────────┼──────────────────────┐
            │ requires             │ requires             │ requires
            ▼                      ▼                      ▼
╔═════════════════════╗ ╔════════════════════╗ ╔════════════════════════╗
║  com.jpms.consumer  ║ ║ com.jpms.transitive║ ║  com.jpms.reflection   ║
║                     ║ ║                    ║ ║                        ║
║  uses MessageService║ ║ requires transitive║ ║  opens model           ║
║  sendSmsMessage()   ║ ║ requires static    ║ ║  opens...to internal   ║
╚══════════╤══════════╝ ╚═════════╤══════════╝ ╚═══════════╤════════════╝
           │ requires             │ requires               │ requires
           │                      │ transitive             │
           ▼                      ▼                        │
           ╔════════════════════════════════════╗          │
           ║           com.jpms.core            ║◄─────────┘
           ║                                    ║
           ║  exports api                       ║
           ║ exports...to internal ─► reflection║
           ╚════════════════╤═══════════════════╝
                            │
                            │ provides MessageService with
                            │
           ╔════════════════╧═══════════════════╗
           ║         com.jpms.provider          ║
           ║                                    ║
           ║  ┌─────────────┐ ┌──────────────┐  ║
           ║  │EmailService │ │ SmsService   │  ║
           ║  └─────────────┘ └──────────────┘  ║
           ║  ┌──────────────────────────────┐  ║
           ║  │  PushNotificationService     │  ║
           ║  └──────────────────────────────┘  ║
           ╚════════════════════════════════════╝

           ╔════════════════════════════════════╗
           ║    com.jpms.open  (open module)    ║
           ║                                    ║
           ║  All packages open for reflection  ║
           ╚════════════════════════════════════╝

┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
  Legend
┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
  ──────►  requires          (compile + runtime dependency)
  ══════►  requires transitive (dependency passed to dependents)
  ┈┈┈┈┈►  provides...with   (service implementation)
  ╔═════╗  module
```

