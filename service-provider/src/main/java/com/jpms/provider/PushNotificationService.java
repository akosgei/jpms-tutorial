package com.jpms.provider;

import com.jpms.core.api.MessageService;

/**
 * PushNotificationService is a third implementation of MessageService.
 *
 * This demonstrates that a module can provide multiple implementations
 * of the same service interface using a comma-separated list:
 *
 *   provides MessageService with EmailService, SmsService, PushNotificationService;
 */
public class PushNotificationService implements MessageService {

    @Override
    public boolean sendMessage(String recipient, String message) {
        System.out.println("[PUSH] Sending push notification to device: " + recipient);
        System.out.println("[PUSH] Title: New Notification");
        System.out.println("[PUSH] Body: " + message);
        System.out.println("[PUSH] Status: Notification pushed!");
        return true;
    }

    @Override
    public String getServiceName() {
        return "Push Notification Service";
    }

    @Override
    public int getPriority() {
        return 3; // Lower priority - best for real-time updates
    }
}

