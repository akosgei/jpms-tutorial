package com.jpms.app;

import com.jpms.consumer.MessageClient;
import com.jpms.core.api.MessageService;
import com.jpms.core.api.StringUtils;
import com.jpms.reflection.ReflectionDemo;
import com.jpms.reflection.model.User;
import com.jpms.transitive.MessageFacade;

import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

/**
 * Main application demonstrating all JPMS directives in action.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     Java Platform Module System (JPMS) Demo Application      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // 1. Demonstrate 'exports' - using public API
        demonstrateExports();

        // 2. Demonstrate 'uses' and 'provides...with' - Service Provider Interface
        demonstrateServiceLoader();

        // 3. Demonstrate 'exports...to' - qualified exports
        demonstrateQualifiedExports();

        // 4. Demonstrate 'opens' - reflection access
        demonstrateOpens();

        // 5. Demonstrate 'opens...to' - qualified reflection access
        demonstrateQualifiedOpens();

        // 6. Demonstrate 'requires transitive'
        demonstrateTransitive();

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("                    Demo Complete!");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    /**
     * Demonstrates the 'exports' directive.
     * We can access public types from exported packages.
     */
    private static void demonstrateExports() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  1. EXPORTS DIRECTIVE                                       │");
        System.out.println("│     Using public API from com.jpms.core.api                 │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        // StringUtils is accessible because com.jpms.core exports com.jpms.core.api
        System.out.println("StringUtils.isEmpty(null): " + StringUtils.isEmpty(null));
        System.out.println("StringUtils.isEmpty(\"\"): " + StringUtils.isEmpty(""));
        System.out.println("StringUtils.isEmpty(\"hello\"): " + StringUtils.isEmpty("hello"));
        System.out.println("StringUtils.capitalize(\"jpms\"): " + StringUtils.capitalize("jpms"));
    }

    /**
     * Demonstrates 'uses' and 'provides...with' directives.
     * ServiceLoader discovers providers at runtime.
     */
    private static void demonstrateServiceLoader() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  2. USES and PROVIDES...WITH DIRECTIVES                     │");
        System.out.println("│     Service Provider Interface (SPI) with ServiceLoader     │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        // MessageClient uses ServiceLoader internally
        // It discovers providers because:
        // - com.jpms.consumer declares: uses MessageService
        // - com.jpms.provider declares: provides MessageService with ...
        MessageClient client = new MessageClient();

        System.out.println("Services found: " + client.getServiceCount());
        client.listAvailableServices();

        // Send using all services
        client.broadcastMessage("user@example.com", "Hello from JPMS!");

        // Send using best service
        client.sendWithBestService("admin@example.com", "Priority message");
    }

    /**
     * Demonstrates 'exports...to' (qualified exports).
     * Only specific modules can access certain packages.
     */
    private static void demonstrateQualifiedExports() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  3. EXPORTS...TO DIRECTIVE (Qualified Export)               │");
        System.out.println("│     com.jpms.core.internal is ONLY exported to reflection   │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        // ReflectionDemo can access InternalHelper because of qualified export
        ReflectionDemo demo = new ReflectionDemo();
        demo.demonstrateInternalAccess();

        System.out.println("\nNote: If com.jpms.app tried to import InternalHelper directly,");
        System.out.println("it would fail with a compilation error!");
        System.out.println("  'package com.jpms.core.internal is not visible'");
    }

    /**
     * Demonstrates 'opens' directive for reflection.
     * Allows deep reflective access to a package.
     */
    private static void demonstrateOpens() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  4. OPENS DIRECTIVE                                         │");
        System.out.println("│     Deep reflection access to com.jpms.reflection.model     │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        try {
            // Create User normally
            User user = new User(1L, "johndoe", "john@example.com");
            System.out.println("Created user: " + user);

            // Use reflection to access private field 'password'
            // This works because of: opens com.jpms.reflection.model;
            Field passwordField = User.class.getDeclaredField("password");
            passwordField.setAccessible(true);  // Would fail without 'opens'
            passwordField.set(user, "secret123");

            String password = (String) passwordField.get(user);
            System.out.println("Password set via reflection: " + password);

            // Use reflection to call private constructor
            Constructor<User> privateConstructor = User.class.getDeclaredConstructor();
            privateConstructor.setAccessible(true);
            User emptyUser = privateConstructor.newInstance();
            System.out.println("Created user via private constructor: " + emptyUser);

            System.out.println("\n✓ Reflection worked because of 'opens' directive!");

        } catch (Exception e) {
            System.out.println("✗ Reflection failed: " + e.getMessage());
        }
    }

    /**
     * Demonstrates 'opens...to' (qualified opens).
     * Only specific modules can use reflection.
     */
    private static void demonstrateQualifiedOpens() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  5. OPENS...TO DIRECTIVE (Qualified Opens)                  │");
        System.out.println("│     Only com.jpms.app can reflect on internal package       │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        try {
            // Load SecretConfig class
            Class<?> secretConfigClass = Class.forName("com.jpms.reflection.internal.SecretConfig");

            // Create instance
            Object config = secretConfigClass.getDeclaredConstructor().newInstance();
            System.out.println("Created config: " + config);

            // Access private field 'secretKey'
            Field secretKeyField = secretConfigClass.getDeclaredField("secretKey");
            secretKeyField.setAccessible(true);
            String secretKey = (String) secretKeyField.get(config);
            System.out.println("Secret key accessed via reflection: " + secretKey);

            // Modify the secret key
            secretKeyField.set(config, "MY_NEW_SECRET_KEY");
            System.out.println("Secret key modified to: " + secretKeyField.get(config));

            System.out.println("\n✓ Qualified opens allowed com.jpms.app to use reflection!");
            System.out.println("  Other modules would get IllegalAccessException.");

        } catch (Exception e) {
            System.out.println("✗ Reflection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Demonstrates 'requires transitive'.
     * Transitive dependencies are automatically available.
     */
    private static void demonstrateTransitive() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  6. REQUIRES TRANSITIVE DIRECTIVE                           │");
        System.out.println("│     Transitive dependency passing                           │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        System.out.println("com.jpms.transitive declares:");
        System.out.println("  requires transitive com.jpms.core;");
        System.out.println();
        System.out.println("This means any module requiring com.jpms.transitive");
        System.out.println("automatically has access to com.jpms.core types.");
        System.out.println();

        // Create a simple service implementation for demo
        MessageService simpleService = new MessageService() {
            @Override
            public boolean sendMessage(String recipient, String message) {
                System.out.println("[SIMPLE] To: " + recipient + ", Msg: " + message);
                return true;
            }

            @Override
            public String getServiceName() {
                return "Simple Service";
            }
        };

        // MessageFacade's constructor takes MessageService from com.jpms.core
        // This works because of 'requires transitive'
        MessageFacade facade = new MessageFacade(simpleService);
        facade.sendFormattedMessage("demo@example.com", "transitive dependencies work!");

        // Get the underlying service - returns MessageService type
        MessageService underlying = facade.getUnderlyingService();
        System.out.println("Underlying service: " + underlying.getServiceName());

        // Demonstrate optional logging (requires static)
        facade.logOperation("Demo completed");

        System.out.println("\n✓ Transitive dependency allowed access to MessageService!");
    }
}

