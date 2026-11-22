package pe.intevex.orders.domain;

/** Un cliente de la tienda. */
public class Customer {

    private final String id;
    private final String fullName;
    private final String email;
    private final CustomerType type;

    public Customer(String id, String fullName, String email, CustomerType type) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public CustomerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "%s (%s) | %s".formatted(fullName, type, email);
    }
}
