/**
 * Service Consumer Module
 *
 * This module demonstrates:
 * - requires: Declares dependency on com.jpms.core
 * - uses: Declares that this module consumes the MessageService
 * - exports: Makes the consumer package available to other modules
 *
 * The 'uses' directive tells the module system that this module will use
 * ServiceLoader to discover implementations of MessageService.
 */
module com.jpms.consumer {

    // REQUIRES DIRECTIVE
    // Needed to access the MessageService interface
    requires com.jpms.core;

    // USES DIRECTIVE
    // Declares that this module is a SERVICE CONSUMER
    // Without this directive, ServiceLoader.load(MessageService.class) would fail
    // even if provider modules are on the module path
    uses com.jpms.core.api.MessageService;

    // EXPORTS DIRECTIVE
    // Makes the MessageClient class available to other modules (like the app)
    exports com.jpms.consumer;
}
