package pe.intevex.orders.tarea2;

import pe.intevex.orders.domain.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tarea 2 · Value objects con record.
 * Pruebo los métodos nuevos de Money y el record ExchangeRate.
 */
public class TareaMoney {

    private static final BigDecimal IGV = new BigDecimal("0.18");

    public static void main(String[] args) {
        suma();
        repartir();
        igv();
        ordenarYComparar();
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

    private static void repartir() {
        System.out.println("\n=== 2. Repartir ===");
        List<Money> partes = Money.soles("100.00").split(3);
        System.out.println(partes);
        System.out.println(Money.soles("10.00").split(4));
        // Compruebo que no se perdió ni apareció ningún céntimo
        System.out.println("Suma de las partes: " + Money.sum(partes));
    }

    private static void igv() {
        System.out.println("\n=== 3. IGV ===");
        imprimirIgv(Money.soles("118.00"));
        imprimirIgv(Money.soles("100.00"));

        // ¿Y si calculo el IGV multiplicando la base por 0.18 en vez de restar? Con S/ 100.00:
        //   base = 100.00 / 1.18 = 84.7457... -> S/ 84.75
        //   IGV  = 84.75 x 0.18  = 15.255     -> S/ 15.26
        //   base + IGV = S/ 100.01 -> aparece un céntimo que el cliente nunca pagó.
        // No conviene porque se redondea dos veces (la base y después el IGV) y los dos errores
        // se suman. Restando (IGV = total - base) solo se redondea la base y el IGV se queda con
        // la diferencia, así base + IGV siempre da exactamente el total.
    }

    private static void imprimirIgv(Money total) {
        Money[] partes = total.splitIgv(IGV);
        System.out.println(total + " -> base " + partes[0] + " | IGV " + partes[1]);
    }

    private static void ordenarYComparar() {
        System.out.println("\n=== 4. Ordenar y comparar ===");
        List<Money> precios = new ArrayList<>(List.of(
                Money.soles("50"), Money.soles("9.90"), Money.soles("120.5"), Money.soles("9.9")));
        Collections.sort(precios);
        System.out.println(precios);
        System.out.println("Mayor: " + Collections.max(precios));

        // Dan resultados distintos porque:
        // - Money normaliza el monto a 2 decimales en el constructor compacto, entonces "10" y
        //   "10.00" quedan los dos como 10.00, y el equals del record compara monto y moneda.
        // - El equals de BigDecimal compara el valor Y la escala: 10 (escala 0) y 10.00 (escala 2)
        //   valen lo mismo pero no son "iguales". Para comparar solo el valor se usa compareTo.
        System.out.println("Money equals: " + Money.soles("10").equals(Money.soles("10.00")));
        System.out.println("BigDecimal equals: " + new BigDecimal("10").equals(new BigDecimal("10.00")));
    }
}
