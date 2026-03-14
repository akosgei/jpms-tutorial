/**
 * Service Provider Module
 *
 * This module demonstrates:
 * - requires: Declares dependency on com.jpms.core
 * - provides...with: Declares service implementations
 *
 * The 'provides...with' directive registers implementations that can be
 * discovered by ServiceLoader in modules that declare 'uses'.
 */
module com.jpms.provider {

    // REQUIRES DIRECTIVE
    // Declares a dependency on the core module to access MessageService interface
    requires com.jpms.core;

    // PROVIDES...WITH DIRECTIVE
    // Registers implementations of the MessageService interface
    // Multiple implementations can be provided for the same interface
    //
    // Note: EventService uses the Provider Method Pattern directly via its own
    // static provider() method — no separate provider class is needed
    provides com.jpms.core.api.MessageService with
        com.jpms.provider.EmailService,
        com.jpms.provider.SmsService,
        com.jpms.provider.PushNotificationService,
        com.jpms.provider.EventService;
}
