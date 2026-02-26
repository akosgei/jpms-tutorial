package com.jpms.reflection.internal;

/**
 * SecretConfig is in a package with QUALIFIED OPENS.
 *
 * Because the module declares:
 *   opens com.jpms.reflection.internal to com.jpms.app;
 *
 * ONLY com.jpms.app can use reflection on this class.
 * Other modules cannot reflectively access private members.
 *
 * This is useful for:
 * - Allowing specific framework modules reflection access
 * - Keeping sensitive internal classes protected from arbitrary reflection
 * - Testing modules that need reflection access to internals
 */
public class SecretConfig {

    private String secretKey = "DEFAULT_SECRET_KEY";
    private String apiEndpoint = "https://api.example.com";
    private int maxRetries = 3;

    public SecretConfig() {
    }

    // Private method - only com.jpms.app can call via reflection
    private void resetToDefaults() {
        this.secretKey = "DEFAULT_SECRET_KEY";
        this.apiEndpoint = "https://api.example.com";
        this.maxRetries = 3;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    // Note: secretKey has no public getter - only accessible via reflection

    @Override
    public String toString() {
        return String.format("SecretConfig{apiEndpoint='%s', maxRetries=%d}",
            apiEndpoint, maxRetries);
    }
}

