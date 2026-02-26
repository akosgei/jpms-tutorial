package com.jpms.core.internal;

/**
 * InternalHelper is in a package with QUALIFIED EXPORTS.
 *
 * This class is ONLY accessible to the com.jpms.reflection module because of:
 *   exports com.jpms.core.internal to com.jpms.reflection;
 *
 * Other modules (like com.jpms.app, com.jpms.consumer) CANNOT access this class,
 * even though they depend on com.jpms.core.
 *
 * This is useful for:
 * - Exposing internal APIs to specific trusted modules (like test modules)
 * - Allowing framework modules special access while hiding from end users
 */
public class InternalHelper {

    private final String moduleName;

    public InternalHelper(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Returns internal configuration details.
     * This method is only accessible to privileged modules.
     *
     * @return internal configuration string
     */
    public String getInternalConfig() {
        return "Internal configuration for module: " + moduleName;
    }

    /**
     * Performs an internal operation.
     * Only accessible to the reflection module.
     */
    public void performInternalOperation() {
        System.out.println("[INTERNAL] Performing privileged operation for: " + moduleName);
    }

    /**
     * Debug method only for internal use.
     *
     * @return debug information
     */
    public String getDebugInfo() {
        return String.format("Module: %s, Timestamp: %d", moduleName, System.currentTimeMillis());
    }
}

