# Orders Domain

Práctica de inventario en Java 21 desarrollada por **Fabrizio Allcca** durante el curso **Java y Microservicios con Kafka de Intevex Academy**, a partir del proyecto inicial del curso.

La versión actual es una aplicación de consola con productos y clientes de demostración en memoria. Corresponde a la **Tarea 1 · Inventario** y reúne ejercicios de programación orientada a objetos, colecciones y manejo de importes con `BigDecimal`.

## Funciones

- Representar productos con SKU, nombre, precio y stock, y clientes con su tipo.
- Calcular el valor total del inventario: suma de precio por stock.
- Consultar productos con stock menor o igual a un umbral.
- Identificar productos agotados.

## Requisitos

- JDK 21.
- Maven; el curso recomienda la versión 3.9 o posterior.

## Ejecutar

Desde la raíz del repositorio:

```bash
mvn compile exec:java
```

El `pom.xml` configura `exec-maven-plugin` para iniciar `pe.intevex.orders.Main`. La consola muestra el catálogo, clientes de ejemplo, valor total, productos con poco stock y productos agotados.

## Origen

Proyecto de aprendizaje basado en el material de **Intevex Academy**. El proyecto inicial del docente está en [PaulNike/Microservicios-kafka](https://github.com/PaulNike/Microservicios-kafka).