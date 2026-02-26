package com.jpms.core.api;

/**
 * MessageService is a Service Provider Interface (SPI).
 *
 * This interface is exported by the com.jpms.core module and can be:
 * - Implemented by service providers (using 'provides...with')
 * - Consumed by service consumers (using 'uses')
 *
 * The ServiceLoader API will discover implementations at runtime.
 */
public interface MessageService {

    /**
     * Sends a message to the specified recipient.
     *
     * @param recipient the message recipient
     * @param message the message content
     * @return true if the message was sent successfully
     */
    boolean sendMessage(String recipient, String message);

    /**
     * Returns the name of this message service implementation.
     *
     * @return the service name
     */
    String getServiceName();

    /**
     * Returns the priority of this service (higher = preferred).
     * Used when multiple implementations are available.
     *
     * @return the priority value
     */
    default int getPriority() {
        return 0;
    }
}

