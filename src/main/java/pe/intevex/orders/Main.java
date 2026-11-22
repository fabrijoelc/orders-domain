package pe.intevex.orders;

import pe.intevex.orders.domain.Customer;
import pe.intevex.orders.domain.CustomerType;
import pe.intevex.orders.domain.Product;
import pe.intevex.orders.retos.MiniRetos;
import pe.intevex.orders.tarea.Inventario;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Catálogo ===");
        List<Product> catalogo = List.of(
                new Product("KB-001", "Teclado mecánico", new BigDecimal("199.90"), 15),
                new Product("MS-002", "Mouse inalámbrico", new BigDecimal("79.90"), 3),
                new Product("MN-003", "Monitor 27\"", new BigDecimal("1099.00"), 5),
                new Product("HS-004", "Audífonos gamer", new BigDecimal("249.50"), 0)
        );
        catalogo.forEach(System.out::println);

        System.out.println("\n=== Clientes ===");
        var maria = new Customer("C-1", "María Rojas", "maria@correo.com", CustomerType.VIP);
        var juan = new Customer("C-2", "Juan Pérez", "juan@correo.com", CustomerType.NEW);
        System.out.println(maria);
        System.out.println(juan);

        System.out.println("\n=== Mini retos ===");
        var subtotal = new BigDecimal("279.80");
        System.out.println("Subtotal: S/ " + subtotal);
        System.out.println("IGV:      S/ " + MiniRetos.calcularIgv(subtotal));
        System.out.println("Total:    S/ " + MiniRetos.calcularTotal(subtotal));
        System.out.println("Descuento de " + maria.getFullName() + ": " + MiniRetos.porcentajeDescuento(maria.getType()));

        System.out.println("\n=== Tarea 1 ===");
        System.out.println("Valor del inventario: S/ " + Inventario.valorTotal(catalogo));
        System.out.println("Poco stock (<= 5):");
        Inventario.conPocoStock(catalogo, 5).forEach(p -> System.out.println("  " + p));
    }
}
