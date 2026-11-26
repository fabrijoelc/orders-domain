package pe.intevex.orders.tarea2;

import pe.intevex.orders.domain.ExchangeRate;
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
        tipoDeCambio();
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

    private static void tipoDeCambio() {
        System.out.println("\n=== 5. Tipo de cambio ===");
        ExchangeRate usdPen = ExchangeRate.of("usd", " PEN ", "3.75");
        Money dolares = Money.of("100", "USD");
        Money soles = usdPen.convert(dolares);
        ExchangeRate penUsd = usdPen.inverse();

        System.out.println(usdPen);
        System.out.println(dolares + " -> " + soles);
        System.out.println(penUsd);

        // De vuelta sale USD 100.01 y no USD 100.00. El céntimo sale del redondeo de la tasa
        // inversa: 1 / 3.75 = 0.26666... pero se guarda con 4 decimales como 0.2667, un poquito
        // más que el valor real. Al multiplicar, ese error crece: 375.00 x 0.2667 = 100.0125,
        // que redondeado da USD 100.01.
        // En un sistema real no convertiría de ida y vuelta con la tasa inversa redondeada:
        // guardaría el monto original y la tasa usada en cada operación, y si tengo que volver
        // dividiría entre la tasa original (375.00 / 3.75 = 100.00), redondeando una sola vez al final.
        System.out.println(soles + " -> " + penUsd.convert(soles));

        // Casos que tienen que lanzar error
        probarError(() -> usdPen.convert(Money.soles("10")));
        probarError(() -> ExchangeRate.of("USD", "usd", "3.75"));
        probarError(() -> ExchangeRate.of("USD", "PEN", "0"));
        probarError(() -> ExchangeRate.of("DOLAR", "PEN", "3.75"));
    }

    /** Ejecuta la acción y, si lanza IllegalArgumentException, imprime el mensaje del error. */
    private static void probarError(Runnable accion) {
        try {
            accion.run();
            System.out.println("No lanzó error y debía lanzarlo");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
