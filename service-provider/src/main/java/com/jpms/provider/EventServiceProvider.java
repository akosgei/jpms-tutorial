package com.jpms.provider;

import com.jpms.core.api.MessageService;

/**
 * EventServiceProvider demonstrates the Provider Method Pattern.
 *
 * Instead of directly providing EventService in module-info.java, we provide
 * this class which contains a static factory method 'provider()'.
 *
 * The module-info.java will declare:
 *   provides com.jpms.core.api.MessageService with com.jpms.provider.EventServiceProvider;
 *
 * When ServiceLoader loads services, it will:
 * 1. Detect that EventServiceProvider has a public static provider() method
 * 2. Call EventServiceProvider.provider() to obtain the service instance
 * 3. Return the EventService instance to the consumer
 *
 * Benefits of this pattern:
 * - Allows complex initialization logic
 * - Enables singleton pattern (reuse same instance)
 * - Supports lazy initialization
 * - Provides better control over instance creation
 * - Can implement caching or pooling strategies
 */
public class EventServiceProvider {

    private static volatile EventService singletonInstance;
    private static int instanceCounter = 0;

    /**
     * Private constructor to prevent instantiation.
     * This class serves only as a provider factory.
     */
    private EventServiceProvider() {
        throw new AssertionError("EventServiceProvider should not be instantiated");
    }

    /**
     * Provider method called by ServiceLoader.
     *
     * This method MUST be:
     * - public
     * - static
     * - named "provider"
     * - return type matching the service interface (MessageService)
     * - have no parameters
     *
     * @return a MessageService implementation instance
     */
    public static MessageService provider() {
        // Singleton pattern: reuse the same instance
        if (singletonInstance == null) {
            synchronized (EventServiceProvider.class) {
                if (singletonInstance == null) {
                    instanceCounter++;
                    String instanceId = "EVT-" + instanceCounter + "-" + System.currentTimeMillis();
                    singletonInstance = new EventService(instanceId);
                    System.out.println("[PROVIDER] Created singleton EventService instance");
                }
            }
        }
        return singletonInstance;
    }

    /**
     * Alternative: If you want a new instance each time (not singleton),
     * you could use this approach instead:
     *
     * public static MessageService provider() {
     *     instanceCounter++;
     *     String instanceId = "EVT-" + instanceCounter + "-" + System.currentTimeMillis();
     *     return new EventService(instanceId);
     * }
     */
}

