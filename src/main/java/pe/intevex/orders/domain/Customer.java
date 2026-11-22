package pe.intevex.orders.domain;

import java.math.BigDecimal;

/** Un cliente de la tienda. */
public class Customer {
    private final String id;
    private final String fullname;
    private final String email;
    private final CustomerType type;

    public Customer(String id, String fullname, String email, CustomerType type) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public CustomerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "%s (%s) | %s".formatted(fullname, email, type);
    }

}
