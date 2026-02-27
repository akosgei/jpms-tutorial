package com.jpms.provider;

import com.jpms.core.api.MessageService;
import com.jpms.core.api.StringUtils;

/**
 * EventService is a service implementation for event-based messaging.
 *
 * This implementation is NOT directly registered in module-info.java.
 * Instead, it is instantiated via the provider method pattern using
 * EventServiceProvider.provider() static factory method.
 *
 * This pattern allows for:
 * - Lazy initialization
 * - Singleton or pooled instances
 * - Complex construction logic
 * - Dependency injection-like patterns
 */
public class EventService implements MessageService {

    private final String instanceId;
    private int messageCount = 0;

    /**
     * Package-private constructor - only accessible to EventServiceProvider
     */
    EventService(String instanceId) {
        this.instanceId = instanceId;
        System.out.println("[EVENT SERVICE] Initialized with ID: " + instanceId);
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

