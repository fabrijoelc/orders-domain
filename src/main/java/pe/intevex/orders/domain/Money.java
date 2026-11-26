package pe.intevex.orders.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
