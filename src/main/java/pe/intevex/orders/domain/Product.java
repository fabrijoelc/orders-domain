package pe.intevex.orders.domain;

import java.math.BigDecimal;

/**
 * Un producto que vende la tienda.
 * El precio es BigDecimal: el dinero NUNCA se guarda en double.
 */
public class Product {

    private final String sku;
    private final String name;
    private final BigDecimal price;
    private int stock;

    public Product(String sku, String name, BigDecimal price, int stock) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "%s | %s | S/ %s | stock %d".formatted(sku, name, price, stock);
    }
}
