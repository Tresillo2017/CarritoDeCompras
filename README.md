# Carrito de Compras - Proyecto Refactorizado

## Descripción General

Este proyecto es una refactorización completa de un carrito de compras implementado en Java. El objetivo principal es demostrar buenas prácticas de ingeniería de software, incluyendo separación de responsabilidades, encapsulación, manejo de excepciones y escalabilidad.

## Mejoras Realizadas

### 1. Separación de Programa Principal y Lógica de Negocio
- **Antes:** La clase `CarritoDeCompras` contenía tanto la lógica del carrito como el método `main`.
- **Después:** Se creó una clase `Main` independiente que actúa como punto de entrada de la aplicación, separando completamente la lógica de negocio de la ejecución.

### 2. Separación de Responsabilidades
La clase `CarritoDeCompras` ahora tiene responsabilidades claras y bien definidas:
- Gestión de la colección de items
- Cálculo del total
- Obtención de información del carrito

La clase `Item` es responsable únicamente de:
- Almacenar información del artículo
- Validar sus propios datos
- Formatear su representación en string (mediante `toString()`)

Se eliminó la duplicación de código mediante métodos privados (`imprimirListaArticulos()` e `imprimirTotal()`).

### 3. Reducción del Acoplamiento
- La clase `CarritoDeCompras` ya no accede directamente a atributos públicos de `Item`.
- Se utilizan getters y setters para acceder a los datos del item, permitiendo mayor flexibilidad en cambios futuros.
- La impresión del detalle de los items se delega completamente a la clase `Item` mediante su método `toString()`.

### 4. Encapsulación y Validación en Item
Se implementaron getters y setters con validación exhaustiva:

```java
// Validación de precio
private static final double PRECIO_MINIMO = 0.01;
private static final double PRECIO_MAXIMO = 1_000_000.00;

public void setPrecio(double precio) {
    if (precio < PRECIO_MINIMO || precio > PRECIO_MAXIMO) {
        throw new IllegalArgumentException(...);
    }
    this.precio = precio;
}
```

**Restricciones implementadas:**
- Precios entre €0.01 y €1,000,000.00
- Nombres no vacíos ni nulos
- Cantidades mayores a 0
- Excepciones descriptivas para casos de error

### 5. Eliminación de Duplicación de Código
Se extrajeron métodos privados para eliminar la repetición:

```java
private void imprimirListaArticulos() { ... }
private void imprimirTotal() { ... }
```

Estos métodos se utilizan en `imprimirDetalle()`, evitando la duplicación de lógica.

### 6. Manejo Robusto de Excepciones
Se implementó manejo exhaustivo de errores:

```java
public void removerItem(String nombreItem) {
    if (nombreItem == null || nombreItem.trim().isEmpty()) {
        throw new IllegalArgumentException("...");
    }
    if (!items.containsKey(nombreItem)) {
        throw new IllegalArgumentException(
            "El artículo '" + nombreItem + "' no existe en el carrito"
        );
    }
    items.remove(nombreItem);
}
```

**Mejoras en Main.java:**
```java
try {
    carrito.removerItem("Artículo inexistente");
} catch (IllegalArgumentException e) {
    System.out.println("Error capturado: " + e.getMessage());
}
```

### 7. Parámetros como Constantes
Se eliminaron los valores codificados ("magic strings") usando constantes:

```java
private static final String MONEDA = "€";
private static final String ENCABEZADO = "Detalles del carrito de compras:";
private static final String SEPARADOR = "----------------------------------------";
private static final double PRECIO_MINIMO = 0.01;
private static final double PRECIO_MAXIMO = 1_000_000.00;
```

### 8. Método toString() Personalizado
Se sobrescribió el método `toString()` en la clase `Item` para proporcionar una representación clara y formateada:

```java
@Override
public String toString() {
    return String.format("%s - €%.2f x %d = €%.2f",
        nombre, precio, cantidad, calcularPrecioTotal());
}
```

**Resultado:** `Camiseta - €20.99 x 2 = €41.98`

### 9. Escalabilidad Mejorada
**Cambio de estructura de datos:**
- **Antes:** `List<Item>` (ArrayList)
- **Después:** `Map<String, Item>` (HashMap)

**Ventajas del HashMap:**
- **Búsqueda O(1):** Encontrar un item por nombre es instantáneo.
- **Actualización eficiente:** Al agregar un item existente, se incrementa su cantidad directamente.
- **Mejor para grandes volúmenes:** Con miles de items, el HashMap es significativamente más rápido.
- **Elimina duplicados:** No puede haber dos items con el mismo nombre.

**Impacto en el rendimiento:**
```
ArrayList: O(n) para búsqueda, O(n) para actualización
HashMap:   O(1) promedio para búsqueda, O(1) promedio para actualización
```

### 10. Soporte para Cantidades
Se añadió soporte completo para cantidad de items:

```java
// Constructor con cantidad
Item item = new Item("Camiseta", 20.99, 2);

// Actualizar cantidad
carrito.actualizarCantidad("Camiseta", 5);

// Agregar más cantidad del mismo item
carrito.agregarItem(new Item("Camiseta", 20.99, 1));
```

**Comportamiento:**
- Cuando se agrega un item existente, se incrementa su cantidad.
- Se puede actualizar la cantidad directamente.
- El precio total se calcula automáticamente: `precio × cantidad`.

## Estructura del Proyecto

```
CarritoDeCompras/
├── src/
│   ├── Main.java              # Clase principal de ejecución
│   ├── CarritoDeCompras.java  # Lógica del carrito
│   └── Item.java              # Modelo de datos del artículo
├── docs/                      # Documentación
└── README.md                  # Este archivo
```

## Clases Principales

### Item.java
Representa un artículo en el carrito con validación de datos.

**Atributos:**
- `nombre`: String (privado)
- `precio`: double (privado)
- `cantidad`: int (privado)

**Métodos principales:**
- `getNombre()`, `setNombre(String)`: Acceso al nombre con validación
- `getPrecio()`, `setPrecio(double)`: Acceso al precio con validación (0.01-1,000,000)
- `getCantidad()`, `setCantidad(int)`: Acceso a la cantidad con validación (>0)
- `calcularPrecioTotal()`: Calcula precio × cantidad
- `toString()`: Representación formateada del item

### CarritoDeCompras.java
Gestiona la colección de items.

**Estructura de datos:**
- `Map<String, Item> items`: HashMap para búsqueda y actualización eficiente

**Métodos principales:**
- `agregarItem(Item)`: Agrega un item o incrementa cantidad si existe
- `removerItem(String)`: Remueve un item por nombre
- `calcularTotal()`: Calcula el total del carrito
- `obtenerCantidadTotal()`: Cantidad total de items
- `actualizarCantidad(String, int)`: Actualiza cantidad de un item
- `imprimirDetalle()`: Imprime el carrito formateado
- `estaVacio()`: Verifica si el carrito está vacío
- `vaciar()`: Vacía el carrito

### Main.java
Programa principal que demuestra todas las funcionalidades.

**Demostraciones incluidas:**
- Crear items con cantidades
- Agregar items al carrito
- Agregar más cantidad del mismo item
- Actualizar cantidades
- Remover items
- Manejo de excepciones
- Información del carrito

## Uso del Proyecto

### Compilación
```bash
javac src/*.java
```

### Ejecución
```bash
java -cp src Main
```

### Ejemplo de Uso en Código
```java
// Crear carrito
CarritoDeCompras carrito = new CarritoDeCompras();

// Crear items con cantidad
Item camiseta = new Item("Camiseta", 20.99, 2);
Item pantalones = new Item("Pantalones", 34.99, 1);

// Agregar al carrito
carrito.agregarItem(camiseta);
carrito.agregarItem(pantalones);

// Actualizar cantidad
carrito.actualizarCantidad("Camiseta", 5);

// Obtener información
System.out.println("Total: " + carrito.calcularTotal());
System.out.println("Items: " + carrito.obtenerCantidadTotal());

// Mostrar carrito
carrito.imprimirDetalle();

// Remover item
carrito.removerItem("Pantalones");
```

## Manejo de Excepciones

El código implementa validación exhaustiva:

```java
// Precio inválido
try {
    Item item = new Item("Producto", -10.00);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
// Resultado: "El precio debe estar entre 0.01€ y 1000000.00€..."

// Item no existe
try {
    carrito.removerItem("Producto inexistente");
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
// Resultado: "El artículo 'Producto inexistente' no existe en el carrito"

// Cantidad inválida
try {
    Item item = new Item("Producto", 20.00, -1);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
// Resultado: "La cantidad debe ser mayor que 0"
```

## Documentación Javadoc

Todas las clases, métodos y atributos están documentados con Javadoc:

```java
/**
 * Clase que representa un artículo individual en el carrito de compras.
 * 
 * Esta clase encapsula la información de un producto, incluyendo su nombre,
 * precio y cantidad. Proporciona validación de datos y métodos de acceso.
 * 
 * @author Equipo de Desarrollo
 * @version 2.0
 */
public class Item { ... }
```

### Generar Documentación HTML
```bash
javadoc -d docs src/*.java
```

## Análisis de Mejoras

### Antes vs Después

| Aspecto | Antes | Después |
|---------|-------|---------|
| **Estructura de datos** | ArrayList | HashMap |
| **Acoplamiento** | Alto (acceso directo a atributos) | Bajo (getters/setters) |
| **Separación de código** | Mezclado (main + lógica) | Separado (Main independiente) |
| **Validación** | Nula | Exhaustiva en constructores y setters |
| **Manejo de errores** | Inexistente | Excepciones descriptivas |
| **Duplicación** | Código repetido | Métodos privados reutilizables |
| **Documentación** | Nula | Javadoc completo |
| **Escalabilidad** | O(n) para búsqueda | O(1) para búsqueda (HashMap) |
| **Formato de salida** | Manual | toString() automático |

### Complejidad Algorítmica

**Operaciones comunes:**

| Operación | ArrayList | HashMap |
|-----------|-----------|---------|
| Agregar | O(1) amortizado | O(1) promedio |
| Buscar | O(n) | O(1) promedio |
| Remover | O(n) | O(1) promedio |
| Actualizar cantidad | O(n) | O(1) promedio |

**Conclusión:** Para carritos con muchos items, HashMap es significativamente más eficiente.

## Futuras Mejoras

1. **Persistencia:** Guardar carrito en archivo o base de datos
2. **Interfaz gráfica:** GUI con Swing o JavaFX
3. **Sistema de descuentos:** Aplicar descuentos por cantidad o categoría
4. **Gestión de categorías:** Organizar items por categoría
5. **Histórico de cambios:** Registrar cambios en el carrito
6. **Interfaz de usuario:** Menú interactivo en consola
7. **Sincronización:** Soporte para múltiples usuarios concurrentes
8. **Validación de stock:** Verificar disponibilidad antes de agregar

## Conclusiones

Esta refactorización demuestra cómo aplicar principios SOLID y buenas prácticas de ingeniería de software:

- **S**ingle Responsibility Principle: Cada clase tiene una única responsabilidad
- **O**pen/Closed Principle: Abierto para extensión (nueva funcionalidad puede agregarse sin modificar código existente)
- **L**iskov Substitution Principle: Implementación coherente de métodos
- **I**nterface Segregation Principle: Métodos específicos y enfocados
- **D**ependency Inversion Principle: Uso de abstracciones (HashMap en lugar de ArrayList específico)

El código resultante es más mantenible, escalable y profesional.

## Autor

Equipo de Desarrollo

## Versión

2.0 - Refactorización completa

## Licencia

Este proyecto es de uso educativo.

---

*Nota: Este README ha sido revisado y modificado por los desarrolladores del proyecto, asistidos por herramientas de IA para mejorar la claridad y completitud de la documentación.*
