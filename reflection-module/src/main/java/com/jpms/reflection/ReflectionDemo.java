package com.jpms.reflection;

import com.jpms.core.internal.InternalHelper;

/**
 * ReflectionDemo demonstrates access to qualified exports.
 *
 * This module can access com.jpms.core.internal because of:
 *   exports com.jpms.core.internal to com.jpms.reflection;
 *
 * Other modules cannot access InternalHelper even though they require com.jpms.core.
 */
public class ReflectionDemo {

    private final InternalHelper internalHelper;

    public ReflectionDemo() {
        // This module has special access to the internal package
        // because of the qualified export in com.jpms.core
        this.internalHelper = new InternalHelper("com.jpms.reflection");
    }

    /**
     * Demonstrates access to the internal API.
     */
    public void demonstrateInternalAccess() {
        System.out.println("\n=== Demonstrating Qualified Export Access ===");
        System.out.println("This module can access InternalHelper because of:");
        System.out.println("  exports com.jpms.core.internal to com.jpms.reflection;");
        System.out.println();

        // Accessing internal API
        System.out.println("Config: " + internalHelper.getInternalConfig());
        System.out.println("Debug: " + internalHelper.getDebugInfo());
        internalHelper.performInternalOperation();
    }
}

