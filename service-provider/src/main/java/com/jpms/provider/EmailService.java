package com.jpms.provider;

import com.jpms.core.api.MessageService;
import com.jpms.core.api.StringUtils;

/**
 * EmailService is a service provider implementation of MessageService.
 *
 * This class is registered via the 'provides...with' directive in module-info.java:
 *   provides com.jpms.core.api.MessageService with com.jpms.provider.EmailService;
 *
 * It will be discovered by ServiceLoader when a consumer module uses:
 *   ServiceLoader.load(MessageService.class)
 */
public class EmailService implements MessageService {

    @Override
    public boolean sendMessage(String recipient, String message) {
        if (StringUtils.isBlank(recipient) || StringUtils.isBlank(message)) {
            System.out.println("[EMAIL] Error: Recipient and message cannot be blank");
            return false;
        }

        System.out.println("[EMAIL] Sending email to: " + recipient);
        System.out.println("[EMAIL] Subject: Notification");
        System.out.println("[EMAIL] Body: " + message);
        System.out.println("[EMAIL] Status: Sent successfully!");
        return true;
    }

    @Override
    public String getServiceName() {
        return "Email Service";
    }

    @Override
    public int getPriority() {
        return 10; // High priority - preferred for formal communications
    }
}

