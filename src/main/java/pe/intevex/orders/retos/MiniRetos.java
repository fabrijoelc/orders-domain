package pe.intevex.orders.retos;

import pe.intevex.orders.domain.CustomerType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Mini retos del bloque de sintaxis. */
public class MiniRetos {

    private static final BigDecimal IGV = new BigDecimal("0.18");

    /** Reto 1: calcular el IGV (18%) de un subtotal, redondeado a 2 decimales. */
    public static BigDecimal calcularIgv(BigDecimal subtotal) {
        return subtotal.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
    }

    /** Reto 1 (continuación): total = subtotal + IGV. */
    public static BigDecimal calcularTotal(BigDecimal subtotal) {
        return subtotal.add(calcularIgv(subtotal));
    }

    /** Reto 2: descuento según el tipo de cliente, con switch expression. */
    public static BigDecimal porcentajeDescuento(CustomerType tipo) {
        return switch (tipo) {
            case NEW -> BigDecimal.ZERO;
            case FREQUENT -> new BigDecimal("0.05");
            case VIP -> new BigDecimal("0.10");
        };
    }

    public static void main(String[] args) {
        var subtotal = new BigDecimal("250.00");
        System.out.println("Subtotal: S/ " + subtotal);
        System.out.println("IGV 18%:  S/ " + calcularIgv(subtotal));
        System.out.println("Total:    S/ " + calcularTotal(subtotal));

        for (CustomerType tipo : CustomerType.values()) {
            System.out.println(tipo + " -> descuento " + porcentajeDescuento(tipo).movePointRight(2).stripTrailingZeros().toPlainString() + "%");
        }
    }
}
