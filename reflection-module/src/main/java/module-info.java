/**
 * Reflection Module
 *
 * This module demonstrates:
 * - requires: Standard module dependency
 * - opens: Opens a package for deep reflection (to all modules)
 * - opens...to: Opens a package for deep reflection to specific modules only
 * - exports: Standard export for compile-time access
 *
 * IMPORTANT DISTINCTION:
 * - 'exports' allows compile-time access to public types
 * - 'opens' allows runtime reflective access (including to private members)
 *
 * This is crucial for frameworks like:
 * - Dependency Injection (Spring, Guice)
 * - ORM frameworks (Hibernate, JPA)
 * - Serialization libraries (Jackson, Gson)
 */
module com.jpms.reflection {

    // REQUIRES DIRECTIVE
    requires com.jpms.core;

    // EXPORTS DIRECTIVE
    // Compile-time access to public types only
    exports com.jpms.reflection;

    // EXPORTS + OPENS for the model package
    // 'exports' allows compile-time access to PUBLIC types
    // 'opens' allows runtime reflection access to PRIVATE members
    // Both are needed: exports for normal use, opens for frameworks needing reflection
    exports com.jpms.reflection.model;
    opens com.jpms.reflection.model;

    // QUALIFIED OPENS DIRECTIVE (opens...to)
    // Opens the 'internal' package for reflection ONLY to com.jpms.app
    // Other modules cannot use reflection on this package
    opens com.jpms.reflection.internal to com.jpms.app;
}

