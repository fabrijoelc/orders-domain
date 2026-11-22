package pe.intevex.orders.domain;

import java.math.BigDecimal;

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

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "%s | %s | S/ %s | stock %s".formatted(sku, name, price, stock);
    }
}
