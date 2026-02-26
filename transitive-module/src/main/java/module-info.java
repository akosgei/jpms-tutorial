/**
 * Transitive Module
 *
 * This module demonstrates:
 * - requires transitive: Passes the dependency to modules that require this one
 * - requires static: Optional compile-time dependency
 *
 * REQUIRES TRANSITIVE explained:
 * When module A declares: requires transitive B;
 * Any module C that requires A will AUTOMATICALLY also require B.
 * <p>
 * This is useful when:
 * - Your module's public API exposes types from another module
 * - You want to create "aggregator" modules that bundle multiple modules together
 * <p>
 * REQUIRES STATIC explained:
 * The dependency is needed at compile time but optional at runtime.
 * Useful for:
 * - Annotation processors
 * - Optional features that are only used if present
 */
module com.jpms.transitive {

    // REQUIRES TRANSITIVE DIRECTIVE
    // Any module that requires com.jpms.transitive will automatically
    // have access to com.jpms.core WITHOUT explicitly declaring it
    requires transitive com.jpms.core;

    // REQUIRES STATIC DIRECTIVE
    // This dependency is optional at runtime
    // The module will work even if java.logging is not present
    // (Note: java.logging is always present in standard JDK, this is just for demonstration)
    requires static java.logging;

    // Export our package
    exports com.jpms.transitive;
}

