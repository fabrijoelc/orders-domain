package pe.intevex.orders.tarea;

import pe.intevex.orders.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Tarea 1 · Inventario.
 * Resuelto con bucles for (Streams llega en la sesión 3).
 */
public class Inventario {

    /** Suma de precio × stock de cada producto. */
    public static BigDecimal valorTotal(List<Product> productos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p : productos) {
            BigDecimal valorProducto = p.getPrice().multiply(BigDecimal.valueOf(p.getStock()));
            total = total.add(valorProducto);
        }
        return total;
    }

    /** Productos con stock menor o igual al umbral. */
    public static List<Product> conPocoStock(List<Product> productos, int umbral) {
        List<Product> resultado = new ArrayList<>();
        for (Product p : productos) {
            if (p.getStock() <= umbral) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /** Extra: productos con stock 0. */
    public static List<Product> agotados(List<Product> productos) {
        List<Product> resultado = new ArrayList<>();
        for (Product p : productos) {
            if (p.getStock() == 0) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
