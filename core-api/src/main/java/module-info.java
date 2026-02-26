/**
 * Core API Module
 *
 * This module demonstrates:
 * - exports: Makes com.jpms.core.api available to all modules
 * - exports...to: Makes com.jpms.core.internal available ONLY to com.jpms.reflection
 */
module com.jpms.core {

    // EXPORTS DIRECTIVE
    // Makes the api package accessible to ALL other modules
    exports com.jpms.core.api;

    // QUALIFIED EXPORTS DIRECTIVE (exports...to)
    // Makes the internal package accessible ONLY to the reflection module
    // Other modules cannot access classes in this package
    exports com.jpms.core.internal to com.jpms.reflection;
}

