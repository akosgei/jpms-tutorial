package com.jpms.reflection.model;

/**
 * User is a model class in an OPEN package.
 *
 * Because the module declares:
 *   opens com.jpms.reflection.model;
 *
 * ANY module can use reflection to:
 * - Access private fields (like 'password')
 * - Invoke private constructors
 * - Call private methods
 *
 * This is essential for frameworks that need deep reflection access:
 * - JSON serializers (Jackson, Gson) need to read/write private fields
 * - ORM frameworks (Hibernate) need to instantiate entities
 * - DI frameworks (Spring) need to inject dependencies
 */
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;  // Private, but accessible via reflection
    private boolean active;

    // Private no-arg constructor for frameworks like Hibernate
    private User() {
    }

    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = true;
    }

    // Private method - accessible via reflection due to 'opens'
    private void encryptPassword(String plainPassword) {
        // Simple demonstration - real encryption would be more complex
        this.password = "ENCRYPTED:" + plainPassword.hashCode();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', email='%s', active=%s}",
            id, username, email, active);
    }
}

