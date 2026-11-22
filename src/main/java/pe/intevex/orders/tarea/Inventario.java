package pe.intevex.orders.tarea;

import pe.intevex.orders.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLUCIÓN DE LA TAREA 1 (solo para el docente).
 * Se resuelve con bucles a propósito: Streams llega en la sesión 3.
 */
public class Inventario {

    /** Valor total del inventario = suma de (precio × stock) de cada producto. */
    public static BigDecimal valorTotal(List<Product> productos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p : productos) {
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(p.getStock())));
        }
        return total;
    }

    /** Productos cuyo stock es menor o igual al umbral. */
    public static List<Product> conPocoStock(List<Product> productos, int umbral) {
        List<Product> resultado = new ArrayList<>();
        for (Product p : productos) {
            if (p.getStock() <= umbral) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
