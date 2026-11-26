package pe.intevex.orders.tarea2;

import pe.intevex.orders.domain.Money;

import java.util.List;

/**
 * Tarea 2 · Value objects con record.
 * Pruebo los métodos nuevos de Money y el record ExchangeRate.
 */
public class TareaMoney {

    public static void main(String[] args) {
        suma();
    }

    private static void suma() {
        System.out.println("=== 1. Suma ===");
        List<Money> precios = List.of(Money.soles("199.90"), Money.soles("79.90"), Money.soles("1099.00"));

        // Primer intento: un acumulador que no reasigna el resultado.
        // Money total = Money.zero();
        // for (Money m : precios) {
        //     total.add(m);
        // }
        // System.out.println(total);   // imprime S/ 0.00
        // Falla porque Money es inmutable: add() no cambia "total", devuelve un Money nuevo que
        // nadie guarda, así que total se queda en 0. Lo correcto es reasignar: total = total.add(m);

        System.out.println(Money.sum(precios));
        System.out.println(Money.sum(List.of()));
    }
}
