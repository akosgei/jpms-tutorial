package com.jpms.transitive;

import com.jpms.core.api.MessageService;
import com.jpms.core.api.StringUtils;

/**
 * MessageFacade demonstrates the 'requires transitive' directive.
 *
 * This class uses types from com.jpms.core in its public API.
 * Because we declare 'requires transitive com.jpms.core', any module
 * that requires com.jpms.transitive will automatically have access
 * to com.jpms.core types like MessageService and StringUtils.
 *
 * WITHOUT transitive:
 *   - Module X requires com.jpms.transitive
 *   - Module X CANNOT use MessageService (compilation error)
 *
 * WITH transitive:
 *   - Module X requires com.jpms.transitive
 *   - Module X CAN use MessageService (works!)
 */
public class MessageFacade {

    private final MessageService service;

    /**
     * Constructor takes a MessageService parameter.
     * Because MessageService is from com.jpms.core and we use
     * 'requires transitive', calling modules can pass implementations.
     */
    public MessageFacade(MessageService service) {
        this.service = service;
    }

    /**
     * Sends a formatted message.
     * Note: This method signature exposes StringUtils behavior.
     */
    public boolean sendFormattedMessage(String recipient, String message) {
        if (StringUtils.isBlank(recipient)) {
            System.out.println("[FACADE] Error: recipient is required");
            return false;
        }

        String formattedMessage = StringUtils.capitalize(message);
        System.out.println("[FACADE] Sending via: " + service.getServiceName());
        return service.sendMessage(recipient, formattedMessage);
    }

    /**
     * Returns the underlying service.
     * This method RETURNS a type from com.jpms.core, so transitive is needed.
     */
    public MessageService getUnderlyingService() {
        return service;
    }

    /**
     * Demonstrates optional logging feature using requires static.
     */
    public void logOperation(String operation) {
        try {
            // java.util.logging is declared as 'requires static'
            // This will work because java.logging is present in the JDK
            java.util.logging.Logger logger =
                java.util.logging.Logger.getLogger(MessageFacade.class.getName());
            logger.info("Operation performed: " + operation);
        } catch (Exception e) {
            // If logging wasn't available, we gracefully handle it
            System.out.println("[FACADE] Logging unavailable: " + operation);
        }
    }
}

