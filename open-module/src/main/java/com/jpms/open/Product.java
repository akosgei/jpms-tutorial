package com.jpms.open;

/**
 * Entity class in an open module.
 *
 * Because this is inside an 'open module', ALL members (including private ones)
 * are accessible via reflection from ANY module, without needing to declare
 * specific 'opens' directives.
 *
 * This is similar to how JPA entity modules often work - they need deep
 * reflection access for the ORM to:
 *   - Instantiate entities using private constructors
 *   - Access and modify private fields
 *   - Invoke lifecycle callback methods
 */
public class Product {

    private Long id;
    private String name;
    private double price;
    private int quantity;
    private boolean available;

    // Private constructor - accessible via reflection due to 'open module'
    private Product() {
        this.available = true;
    }

    public Product(Long id, String name, double price) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    // Private method - accessible via reflection
    private void applyDiscount(double percentage) {
        this.price = this.price * (1 - percentage / 100);
    }

    // Public getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.available = quantity > 0;
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%.2f, quantity=%d, available=%s}",
            id, name, price, quantity, available);
    }
}

