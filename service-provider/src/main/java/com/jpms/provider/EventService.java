package com.jpms.provider;

import com.jpms.core.api.MessageService;
import com.jpms.core.api.StringUtils;

/**
 * EventService is a service implementation for event-based messaging.
 *
 * This class uses the Provider Method Pattern directly — no separate provider
 * class is needed. ServiceLoader detects the public static provider() method
 * and calls it instead of the constructor.
 *
 * This pattern allows for:
 * - Lazy initialization
 * - Singleton or pooled instances
 * - Complex construction logic
 * - Dependency injection-like patterns
 */
public class EventService implements MessageService {

    private static volatile EventService singletonInstance;
    private static int instanceCounter = 0;

    private final String instanceId;
    private int messageCount = 0;

    /**
     * Package-private constructor - not called directly by ServiceLoader.
     * ServiceLoader uses the provider() method instead.
     */
    EventService(String instanceId) {
        this.instanceId = instanceId;
        System.out.println("[EVENT SERVICE] Initialized with ID: " + instanceId);
    }

    /**
     * Provider method called by ServiceLoader.
     * <p>
     * This method MUST be:
     * - public
     * - static
     * - named "provider"
     * - return type matching the service interface (MessageService)
     * - have no parameters
     * <p>
     * Because this method exists, ServiceLoader will never touch the constructor,
     * so the constructor can be any visibility.
     *
     * @return a singleton MessageService instance
     */
    public static MessageService provider() {
        if (singletonInstance == null) {
            synchronized (EventService.class) {
                if (singletonInstance == null) {
                    instanceCounter++;
                    String instanceId = "EVT-" + instanceCounter + "-" + System.currentTimeMillis();
                    singletonInstance = new EventService(instanceId);
                    System.out.println("[EVENT SERVICE] Created singleton instance");
                }
            }
        }
        return singletonInstance;
    }

    @Override
    public boolean sendMessage(String recipient, String message) {
        if (StringUtils.isBlank(recipient) || StringUtils.isBlank(message)) {
            System.out.println("[EVENT] Error: Recipient and message cannot be blank");
            return false;
        }

        messageCount++;
        System.out.println("[EVENT] Publishing event to: " + recipient);
        System.out.println("[EVENT] Event Type: Notification Event");
        System.out.println("[EVENT] Payload: " + message);
        System.out.println("[EVENT] Instance ID: " + instanceId);
        System.out.println("[EVENT] Total messages sent by this instance: " + messageCount);
        System.out.println("[EVENT] Status: Event published successfully!");
        return true;
    }

    @Override
    public String getServiceName() {
        return "Event Service (Provider Method Pattern)";
    }

    @Override
    public int getPriority() {
        return 5; // Medium priority
    }
}

