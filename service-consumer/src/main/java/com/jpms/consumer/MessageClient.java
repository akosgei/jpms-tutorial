package com.jpms.consumer;

import com.jpms.core.api.MessageService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * MessageClient demonstrates the 'uses' directive and ServiceLoader API.
 * <p>
 * This class uses ServiceLoader to dynamically discover all implementations
 * of MessageService at runtime. The module must declare:
 * uses com.jpms.core.api.MessageService;
 * <p>
 * in its module-info.java for ServiceLoader to work properly.
 */
public class MessageClient {

    private final List<MessageService> services;

    public MessageClient() {
        // ServiceLoader.load() discovers all providers on the module path
        // that have declared: provides MessageService with ...
        this.services = new ArrayList<>();
        ServiceLoader<MessageService> loader = ServiceLoader.load(MessageService.class);
        loader.forEach(services::add);
    }

    /**
     * Lists all available message services discovered via ServiceLoader.
     */
    public void listAvailableServices() {
        System.out.println("\n=== Available Message Services ===");
        if (services.isEmpty()) {
            System.out.println("No message services found!");
            System.out.println("Make sure a provider module is on the module path.");
            return;
        }

        services.stream()
                .sorted(Comparator.comparingInt(MessageService::getPriority).reversed())
                .forEach(service -> {
                    System.out.printf("  - %s (Priority: %d)%n",
                            service.getServiceName(),
                            service.getPriority());
                });
    }

    /**
     * Sends a message using all available services.
     *
     * @param recipient the message recipient
     * @param message   the message content
     */
    public void broadcastMessage(String recipient, String message) {
        System.out.println("\n=== Broadcasting Message ===");
        for (MessageService service : services) {
            System.out.println("\nUsing: " + service.getServiceName());
            service.sendMessage(recipient, message);
        }
    }

    /**
     * Sends a message using the highest priority service.
     *
     * @param recipient the message recipient
     * @param message   the message content
     * @return true if message was sent
     */
    public boolean sendWithBestService(String recipient, String message) {
        System.out.println("\n=== Sending with Best Service ===");
        Optional<MessageService> bestService = services.stream()
                .max(Comparator.comparingInt(MessageService::getPriority));

        if (bestService.isPresent()) {
            MessageService service = bestService.get();
            System.out.println("Selected: " + service.getServiceName());
            return service.sendMessage(recipient, message);
        } else {
            System.out.println("No service available!");
            return false;
        }
    }

    public boolean sendSmsMessage(String phoneNumber, String message) {
        System.out.println("\n=== Sending SMS Message ===");
        Optional<MessageService> smsService = services.stream()
                .filter(service -> "SMS Service".equals(service.getServiceName()))
                .findFirst();

        if (smsService.isPresent()) {
            MessageService service = smsService.get();
            System.out.println("Selected: " + service.getServiceName());
            String smsMessage = "[SMS] " + message;
            return service.sendMessage(phoneNumber, smsMessage);
        }

        System.out.println("SMS service not available!");
        return false;
    }

    /**
     * Sends an event message using the EventService (Provider Method Pattern).
     * <p>
     * This method demonstrates consuming a service that was registered using
     * the Provider Method Pattern. The EventServiceProvider.provider() method
     * will be called by ServiceLoader to obtain the EventService instance.
     * <p>
     * Because EventServiceProvider uses a singleton pattern, calling this method
     * multiple times will use the same EventService instance, which can be
     * observed by the instance ID and message count in the output.
     *
     * @param recipient the event recipient/topic
     * @param message   the event payload
     */
    public void sendEventMessage(String recipient, String message) {
        System.out.println("\n=== Sending Event Message ===");
        Optional<MessageService> eventService = services.stream()
                .filter(service -> service.getServiceName().contains("Event Service"))
                .findFirst();

        if (eventService.isPresent()) {
            MessageService service = eventService.get();
            System.out.println("Selected: " + service.getServiceName());
            System.out.println("Note: This service uses the Provider Method Pattern");
            System.out.println("      EventServiceProvider.provider() returns a singleton instance");
            service.sendMessage(recipient, message);
            return;
        }

        System.out.println("Event service not available!");
        System.out.println("Make sure EventServiceProvider is registered in module-info.java");
    }

    /**
     * Gets the count of available services.
     *
     * @return number of discovered services
     */
    public int getServiceCount() {
        return services.size();
    }
}
