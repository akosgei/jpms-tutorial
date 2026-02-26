/**
 * Application Module
 *
 * This module brings together all the JPMS features demonstrated in this project.
 *
 * Module dependencies:
 * - com.jpms.core: Provides the core API (MessageService, StringUtils)
 * - com.jpms.consumer: Provides MessageClient (uses ServiceLoader)
 * - com.jpms.reflection: Demonstrates reflection with opens directive
 * - com.jpms.transitive: Demonstrates requires transitive
 * - com.jpms.provider: Service implementations (runtime only)
 */
module com.jpms.app {

    // Standard requires - direct dependency on the core module
    requires com.jpms.core;

    // Requires the consumer module to use MessageClient
    requires com.jpms.consumer;

    // Requires the reflection module
    requires com.jpms.reflection;

    // Requires the transitive module
    // Note: Because com.jpms.transitive has 'requires transitive com.jpms.core',
    // we would get access to com.jpms.core even without our own requires statement
    requires com.jpms.transitive;

    // Note: com.jpms.provider is NOT required here
    // It's a service provider that's discovered at runtime via ServiceLoader
    // The service-consumer module declares 'uses MessageService'
}

