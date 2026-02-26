package com.jpms.provider;

import com.jpms.core.api.MessageService;
import com.jpms.core.api.StringUtils;

/**
 * SmsService is another service provider implementation of MessageService.
 *
 * Multiple implementations can be registered for the same service interface.
 * The ServiceLoader will find all of them, allowing the consumer to choose.
 */
public class SmsService implements MessageService {

    private static final int MAX_SMS_LENGTH = 160;

    @Override
    public boolean sendMessage(String recipient, String message) {
        if (StringUtils.isBlank(recipient)) {
            System.out.println("[SMS] Error: Phone number required");
            return false;
        }

        String truncatedMessage = message;
        if (message != null && message.length() > MAX_SMS_LENGTH) {
            truncatedMessage = message.substring(0, MAX_SMS_LENGTH - 3) + "...";
        }

        System.out.println("[SMS] Sending SMS to: " + recipient);
        System.out.println("[SMS] Message: " + truncatedMessage);
        System.out.println("[SMS] Status: Delivered!");
        return true;
    }

    @Override
    public String getServiceName() {
        return "SMS Service";
    }

    @Override
    public int getPriority() {
        return 5; // Medium priority - good for quick notifications
    }
}

