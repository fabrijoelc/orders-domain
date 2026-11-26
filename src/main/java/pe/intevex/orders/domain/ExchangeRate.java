package pe.intevex.orders.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Objects;

/**
 * Tipo de cambio entre dos monedas: 1 from = rate to. Ej.: 1 USD = 3.7500 PEN.
 * Es inmutable: inverse() y convert() devuelven objetos nuevos.
 * El constructor compacto valida y normaliza las monedas y la tasa.
 */
public record ExchangeRate(String from, String to, BigDecimal rate) {

    public ExchangeRate {
        from = normalizeCurrency(from);
        to = normalizeCurrency(to);
        if (from.equals(to)) {
            throw new IllegalArgumentException("Las monedas deben ser distintas");
        }
        // Valido la tasa ya redondeada: algo como 0.00001 quedaría en 0.0000
        if (rate == null || rate.setScale(4, RoundingMode.HALF_UP).signum() <= 0) {
            throw new IllegalArgumentException("El tipo de cambio debe ser mayor a 0");
        }
        rate = rate.setScale(4, RoundingMode.HALF_UP);
    }

    /** Método de fábrica: ExchangeRate.of("USD", "PEN", "3.75"). */
    public static ExchangeRate of(String from, String to, String rate) {
        return new ExchangeRate(from, to, new BigDecimal(rate));
    }

    /** El tipo de cambio al revés, con 4 decimales: USD -> PEN 3.75 pasa a PEN -> USD 0.2667. */
    public ExchangeRate inverse() {
        return new ExchangeRate(to, from, BigDecimal.ONE.divide(rate, 4, RoundingMode.HALF_UP));
    }

    /** Convierte el monto a la moneda "to". El monto tiene que estar en la moneda "from". */
    public Money convert(Money monto) {
        Objects.requireNonNull(monto, "El monto es obligatorio");
        if (!monto.currency().equals(from)) {
            throw new IllegalArgumentException("Este tipo de cambio convierte " + from + ", no " + monto.currency());
        }
        return new Money(monto.amount().multiply(rate), to);
    }

    @Override
    public String toString() {
        return "1 " + from + " = " + rate.toPlainString() + " " + to;
    }

    /** Lo usa el constructor compacto: exige un código de 3 letras y normaliza " usd " -> "USD". */
    private static String normalizeCurrency(String code) {
        String normalized = code == null ? "" : code.strip().toUpperCase(Locale.ROOT);
        if (!normalized.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Moneda inválida: " + code);
        }
        return normalized;
    }
}
