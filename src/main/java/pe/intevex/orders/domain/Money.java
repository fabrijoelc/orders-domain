package pe.intevex.orders.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Dinero: un monto y una moneda. Es inmutable: cada operación devuelve un Money nuevo.
 * El constructor compacto valida y normaliza a 2 decimales.
 */
public record Money(BigDecimal amount, String currency) {

    public static final String PEN = "PEN";

    public Money {
        Objects.requireNonNull(amount, "El monto es obligatorio");
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La moneda es obligatoria");
        }
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money soles(String amount) {
        return new Money(new BigDecimal(amount), PEN);
    }

    public static Money soles(BigDecimal amount) {
        return new Money(amount, PEN);
    }

    public static Money zero() {
        return soles(BigDecimal.ZERO);
    }

    /** Suma una lista de montos de la misma moneda. Si la lista está vacía devuelve S/ 0.00. */
    public static Money sum(List<Money> montos) {
        if (montos.isEmpty()) {
            return zero();
        }
        // Empiezo en 0 con la moneda del primer monto para poder sumar soles, dólares, etc.
        Money total = new Money(BigDecimal.ZERO, montos.get(0).currency());
        for (Money m : montos) {
            total = total.add(m);
        }
        return total;
    }

    public Money add(Money other) {
        checkSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        checkSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    public Money multiply(int quantity) {
        return new Money(amount.multiply(BigDecimal.valueOf(quantity)), currency);
    }

    /** Porcentaje del monto. Ej.: 0.10 = 10%. */
    public Money percentage(BigDecimal rate) {
        return new Money(amount.multiply(rate), currency);
    }

    /**
     * Reparte el monto en partes iguales sin perder ni inventar céntimos.
     * Si no sale exacto, los céntimos que sobran se dan uno a uno a las primeras partes.
     * Ej.: S/ 100.00 en 3 -> [S/ 33.34, S/ 33.33, S/ 33.33]
     */
    public List<Money> split(int partes) {
        if (partes <= 0) {
            throw new IllegalArgumentException("El número de partes debe ser mayor a 0");
        }
        BigDecimal cantidad = BigDecimal.valueOf(partes);
        // Parte base truncada a 2 decimales: 100.00 / 3 = 33.33
        BigDecimal base = amount.divide(cantidad, 2, RoundingMode.DOWN);
        // Lo que no se pudo repartir: 100.00 - 33.33 * 3 = 0.01 -> sobra 1 céntimo
        BigDecimal sobrante = amount.subtract(base.multiply(cantidad));
        int centimosQueSobran = sobrante.abs().movePointRight(2).intValueExact();
        // Si el monto es negativo lo que "sobra" también es negativo
        BigDecimal centimo = sobrante.signum() < 0 ? new BigDecimal("-0.01") : new BigDecimal("0.01");

        List<Money> resultado = new ArrayList<>();
        for (int i = 0; i < partes; i++) {
            BigDecimal parte = i < centimosQueSobran ? base.add(centimo) : base;
            resultado.add(new Money(parte, currency));
        }
        return List.copyOf(resultado);
    }

    /**
     * Recibe un monto que YA incluye IGV y lo separa en [base imponible, IGV].
     * El IGV se calcula restando (total - base), así base + IGV es exactamente el total.
     * Ej.: S/ 118.00 con 0.18 -> base S/ 100.00 · IGV S/ 18.00
     */
    public Money[] splitIgv(BigDecimal tasa) {
        if (tasa == null || tasa.signum() < 0) {
            throw new IllegalArgumentException("La tasa de IGV debe ser 0 o mayor");
        }
        BigDecimal base = amount.divide(BigDecimal.ONE.add(tasa), 2, RoundingMode.HALF_UP);
        BigDecimal igv = amount.subtract(base);
        return new Money[]{new Money(base, currency), new Money(igv, currency)};
    }

    public boolean isGreaterOrEqualThan(Money other) {
        checkSameCurrency(other);
        return amount.compareTo(other.amount) >= 0;
    }

    public boolean isNegative() {
        return amount.signum() < 0;
    }

    private void checkSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("No se pueden operar monedas distintas: " + currency + " y " + other.currency);
        }
    }

    @Override
    public String toString() {
        return (PEN.equals(currency) ? "S/ " : currency + " ") + amount.toPlainString();
    }
}
