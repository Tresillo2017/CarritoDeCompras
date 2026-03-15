# Análisis Detallado de la Refactorización

## Índice
1. [Paso 1: Separación del Programa Principal](#paso-1-separación-del-programa-principal)
2. [Paso 2: Separación de Responsabilidades](#paso-2-separación-de-responsabilidades)
3. [Paso 3: Reducción del Acoplamiento](#paso-3-reducción-del-acoplamiento)
4. [Paso 4: Encapsulación y Validación](#paso-4-encapsulación-y-validación)
5. [Paso 5: Eliminación de Duplicación](#paso-5-eliminación-de-duplicación)
6. [Paso 6: Manejo de Excepciones](#paso-6-manejo-de-excepciones)
7. [Paso 7: Constantes en lugar de Valores Mágicos](#paso-7-constantes-en-lugar-de-valores-mágicos)
8. [Paso 8: toString() Personalizado](#paso-8-tostring-personalizado)
9. [Paso 9: Escalabilidad](#paso-9-escalabilidad)
10. [Paso 10: Soporte para Cantidades](#paso-10-soporte-para-cantidades)

---

## Paso 1: Separación del Programa Principal

### Problema Original
```java
public class CarritoDeCompras {
    private List<Item> items;
    
    // ... métodos del carrito ...
    
    public static void main(String[] args) {
        // Lógica de demostración aquí
        CarritoDeCompras carrito = new CarritoDeCompras();
        // ...
    }
}
```

**Problemas:**
- La clase tiene dos responsabilidades: ser un carrito Y ser el programa principal
- Difícil de testear (no se puede instanciar el carrito sin ejecutar main)
- Difícil de reutilizar en otros contextos

### Solución Implementada
Se crearon dos clases separadas:

**CarritoDeCompras.java** - Solo lógica de negocio
```java
public class CarritoDeCompras {
    private Map<String, Item> items;
    // ... solo métodos del carrito ...
}
```

**Main.java** - Punto de entrada de la aplicación
```java
public class Main {
    public static void main(String[] args) {
        CarritoDeCompras carrito = new CarritoDeCompras();
        // ... demostración ...
    }
}
```

**Ventajas:**
✓ Cada clase tiene una única responsabilidad
✓ Fácil de testear
✓ Reutilizable en otros contextos
✓ Más mantenible y profesional

---

## Paso 2: Separación de Responsabilidades

### Problema Original
```java
public void imprimirDetalle() {
    System.out.println("Detalles del carrito de compras:");
    for (Item item : items) {
        System.out.println(item.nombre + " - $" + item.precio);
    }

    double total = 0;
    for (Item item : items) {
        total += item.precio;
    }

    System.out.println("Total: €" + total);
}
```

**Problemas:**
- Una método hace múltiples cosas: itera items, calcula total, imprime
- Difícil de modificar sin afectar otras partes
- El cálculo del total está mezclado con la impresión

### Solución Implementada

**Método para calcular total (responsabilidad de CarritoDeCompras):**
```java
public double calcularTotal() {
    double total = 0;
    for (Item item : items.values()) {
        total += item.calcularPrecioTotal();
    }
    return total;
}
```

**Métodos privados reutilizables:**
```java
private void imprimirListaArticulos() {
    for (Item item : items.values()) {
        System.out.println(item.toString());
    }
}

private void imprimirTotal() {
    double total = calcularTotal();
    System.out.printf("%s%s%.2f (Total de items: %d)%n", 
        TOTAL, MONEDA, total, obtenerCantidadTotal());
}

public void imprimirDetalle() {
    System.out.println(SEPARADOR);
    System.out.println(ENCABEZADO);
    // ...
    imprimirListaArticulos();
    imprimirTotal();
    System.out.println(SEPARADOR);
}
```

**Ventajas:**
✓ Cada método tiene una única responsabilidad
✓ El cálculo del total está independiente de la impresión
✓ Se pueden reutilizar `calcularTotal()` y `imprimirTotal()` sin imprimir toda la lista

---

## Paso 3: Reducción del Acoplamiento

### Problema Original
```java
// Acceso directo a atributos públicos
System.out.println(item.nombre + " - $" + item.precio);
```

**Problemas:**
- CarritoDeCompras depende directamente de la estructura de Item
- Si cambio los atributos de Item, tengo que cambiar CarritoDeCompras
- No hay validación de datos

### Solución Implementada

**Item con atributos privados y getters:**
```java
public class Item {
    private String nombre;
    private double precio;
    private int cantidad;
    
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    
    @Override
    public String toString() {
        return String.format("%s - €%.2f x %d = €%.2f",
            nombre, precio, cantidad, calcularPrecioTotal());
    }
}
```

**Uso en CarritoDeCompras:**
```java
// En lugar de acceder directamente al atributo
// item.nombre + " - $" + item.precio

// Ahora usamos el toString():
System.out.println(item.toString());

// O accedemos por getters cuando sea necesario:
item.getNombre();
item.getPrecio();
```

**Ventajas:**
✓ Item controla su propia representación
✓ Bajo acoplamiento entre clases
✓ Cambios en Item no afectan CarritoDeCompras
✓ Datos validados y seguros

---

## Paso 4: Encapsulación y Validación

### Problema Original
```java
class Item {
    public String nombre;
    public double precio;
    
    public Item(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}

// Permitía valores inválidos:
Item item = new Item(null, -10.00);  // ¡Válido pero incorrecto!
```

### Solución Implementada

**Setters con validación exhaustiva:**
```java
private static final double PRECIO_MINIMO = 0.01;
private static final double PRECIO_MAXIMO = 1_000_000.00;

public void setNombre(String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
        throw new IllegalArgumentException(
            "El nombre del artículo no puede ser nulo o vacío");
    }
    this.nombre = nombre.trim();
}

public void setPrecio(double precio) {
    if (precio < PRECIO_MINIMO || precio > PRECIO_MAXIMO) {
        throw new IllegalArgumentException(
            String.format("El precio debe estar entre %.2f€ y %.2f€",
                PRECIO_MINIMO, PRECIO_MAXIMO));
    }
    this.precio = precio;
}

public void setCantidad(int cantidad) {
    if (cantidad <= 0) {
        throw new IllegalArgumentException(
            "La cantidad debe ser mayor que 0");
    }
    this.cantidad = cantidad;
}
```

**Constructores que validan:**
```java
public Item(String nombre, double precio, int cantidad) {
    setNombre(nombre);      // Valida en el setter
    setPrecio(precio);      // Valida en el setter
    setCantidad(cantidad);  // Valida en el setter
}
```

**Uso:**
```java
// Ahora esto falla con un mensaje claro:
try {
    Item item = new Item("Producto", -10.00);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
    // Output: "El precio debe estar entre 0.01€ y 1000000.00€"
}
```

**Ventajas:**
✓ Imposible crear Items inválidos
✓ Excepciones descriptivas
✓ Datos siempre garantizados como válidos
✓ Mejora la robustez de la aplicación

---

## Paso 5: Eliminación de Duplicación

### Problema Original
```java
public void imprimirDetalle() {
    System.out.println("Detalles del carrito de compras:");
    for (Item item : items) {
        System.out.println(item.nombre + " - $" + item.precio);
    }

    double total = 0;
    for (Item item : items) {  // ← DUPLICACIÓN: iteramos dos veces
        total += item.precio;
    }

    System.out.println("Total: €" + total);
}
```

**Problemas:**
- Iteramos dos veces sobre los mismos items
- Si hay cambios, hay que hacerlos en dos lugares
- Código redundante y propenso a errores

### Solución Implementada

**Métodos privados reutilizables:**
```java
public double calcularTotal() {
    double total = 0;
    for (Item item : items.values()) {
        total += item.calcularPrecioTotal();
    }
    return total;  // Se reutiliza en múltiples lugares
}

private void imprimirListaArticulos() {
    // Una sola iteración para imprimir
    for (Item item : items.values()) {
        System.out.println(item.toString());
    }
    System.out.println(LINEA_VACIA);
}

private void imprimirTotal() {
    // Reutiliza calcularTotal() sin iteración extra
    double total = calcularTotal();
    System.out.printf("%s%s%.2f (Total de items: %d)%n", 
        TOTAL, MONEDA, total, obtenerCantidadTotal());
}

public void imprimirDetalle() {
    System.out.println(SEPARADOR);
    System.out.println(ENCABEZADO);
    System.out.println(SEPARADOR);
    
    if (estaVacio()) {
        System.out.println("El carrito está vacío");
    } else {
        imprimirListaArticulos();  // Usa método reutilizable
    }
    
    System.out.println(SEPARADOR);
    imprimirTotal();  // Usa método reutilizable
    System.out.println(SEPARADOR);
}
```

**Ventajas:**
✓ No hay duplicación de lógica
✓ Una sola fuente de verdad para cada lógica
✓ Cambios en una línea afectan todos los usos
✓ Más fácil de mantener

---

## Paso 6: Manejo de Excepciones

### Problema Original
```java
public void removerItem(Item item) {
    items.remove(item);  // ¿Qué pasó? ¿Se removió o no?
}

// Uso:
carrito.removerItem(itemInexistente);  // No hay error, solo silencio
```

**Problemas:**
- No hay feedback sobre si se removió correctamente
- Difícil detectar errores
- Programación defensiva débil

### Solución Implementada

**Validación exhaustiva con excepciones:**
```java
public void removerItem(String nombreItem) {
    if (nombreItem == null || nombreItem.trim().isEmpty()) {
        throw new IllegalArgumentException(
            "El nombre del item no puede ser nulo o vacío");
    }
    
    if (!items.containsKey(nombreItem)) {
        throw new IllegalArgumentException(
            "El artículo '" + nombreItem + "' no existe en el carrito");
    }
    
    items.remove(nombreItem);
}
```

**Uso con manejo de errores:**
```java
try {
    carrito.removerItem("Producto inexistente");
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
    // Output: "Error: El artículo 'Producto inexistente' no existe en el carrito"
}
```

**También en Main.java:**
```java
System.out.println("Intentando remover un artículo que no existe...");
try {
    carrito.removerItem("Artículo inexistente");
} catch (IllegalArgumentException e) {
    System.out.println("Error capturado: " + e.getMessage());
}
```

**Ventajas:**
✓ Errores detectados inmediatamente
✓ Mensajes claros sobre qué fue mal
✓ El programa no continúa con datos inválidos
✓ Facilita debugging

---

## Paso 7: Constantes en lugar de Valores Mágicos

### Problema Original
```java
public void imprimirDetalle() {
    System.out.println("Detalles del carrito de compras:");
    // ...
    System.out.println("Total: €" + total);
}
```

**Problemas:**
- Valores mágicos esparcidos por el código
- Difícil de cambiar (si cambio el símbolo € a $, tengo que buscar por todo)
- Difícil de entender qué son estos valores

### Solución Implementada

**Constantes en la clase:**
```java
private static final String MONEDA = "€";
private static final String ENCABEZADO = "Detalles del carrito de compras:";
private static final String SEPARADOR = "----------------------------------------";
private static final String LINEA_VACIA = "";
private static final String TOTAL = "Total: ";

private static final double PRECIO_MINIMO = 0.01;
private static final double PRECIO_MAXIMO = 1_000_000.00;
```

**Uso:**
```java
private void imprimirTotal() {
    double total = calcularTotal();
    System.out.printf("%s%s%.2f (Total de items: %d)%n", 
        TOTAL, MONEDA, total, obtenerCantidadTotal());
}

private void imprimirListaArticulos() {
    for (Item item : items.values()) {
        System.out.println(item.toString());
    }
    System.out.println(LINEA_VACIA);
}
```

**Ventajas:**
✓ Fácil cambiar valores (una línea vs múltiples búsquedas)
✓ Código autodocumentado
✓ Menos propenso a errores por typos
✓ Mantenimiento centralizado

---

## Paso 8: toString() Personalizado

### Problema Original
```java
public class Item {
    public String nombre;
    public double precio;
}

// Uso:
System.out.println(item.nombre + " - $" + item.precio);  // ← Lógica esparcida
```

**Problemas:**
- La lógica de formateo está fuera de Item
- Acoplamiento entre CarritoDeCompras e Item
- Si quiero cambiar el formato, tengo que cambiar CarritoDeCompras

### Solución Implementada

**toString() en Item:**
```java
@Override
public String toString() {
    return String.format("%s - €%.2f x %d = €%.2f",
        nombre, precio, cantidad, calcularPrecioTotal());
}
```

**Uso simplificado:**
```java
// Antes:
System.out.println(item.nombre + " - $" + item.precio);

// Después:
System.out.println(item.toString());  // O simplemente:
System.out.println(item);              // Java llama toString() automáticamente
```

**Resultado:**
```
Camiseta - €20.99 x 2 = €41.98
```

**Ventajas:**
✓ Item controla su propia representación
✓ Fácil cambiar el formato sin afectar otras clases
✓ Código más limpio y legible
✓ Patrón común en Java

---

## Paso 9: Escalabilidad

### Problema Original
```java
private List<Item> items;

public void agregarItem(Item item) {
    items.add(item);  // O(1) al final, pero...
}

public void removerItem(Item item) {
    items.remove(item);  // O(n) porque busca comparando
}
```

**Problemas con ArrayList:**
- Remover un item requiere búsqueda lineal: O(n)
- Actualizar cantidad requiere búsqueda: O(n)
- Con 1,000,000 items, es lentísimo

### Solución Implementada

**HashMap en lugar de ArrayList:**
```java
private Map<String, Item> items;  // Key: nombre del item

public void agregarItem(Item item) {
    if (item == null) {
        throw new IllegalArgumentException("El item no puede ser nulo");
    }
    
    if (items.containsKey(item.getNombre())) {
        // O(1): búsqueda instantánea
        Item itemExistente = items.get(item.getNombre());
        itemExistente.setCantidad(itemExistente.getCantidad() + item.getCantidad());
    } else {
        items.put(item.getNombre(), item);  // O(1) inserción
    }
}

public void removerItem(String nombreItem) {
    if (!items.containsKey(nombreItem)) {  // O(1): búsqueda instantánea
        throw new IllegalArgumentException("...");
    }
    items.remove(nombreItem);  // O(1): remoción instantánea
}
```

**Comparación de rendimiento:**

| Operación | ArrayList | HashMap |
|-----------|-----------|---------|
| add() al final | O(1) | O(1) |
| remove() | O(n) | O(1) |
| contains() | O(n) | O(1) |
| get() | O(1) por índice, O(n) por búsqueda | O(1) |
| Con 1,000,000 items | ~1s para remover | ~0.001s para remover |

**Código de prueba teórico:**
```java
// ArrayList (lento con muchos items)
List<Item> items = new ArrayList<>();
for (int i = 0; i < 1_000_000; i++) {
    items.add(new Item("Producto" + i, 10.00));
}
items.remove(items.size() - 1);  // O(n) - casi instantáneo
items.remove(0);  // O(n) - ¡LENTÍSIMO!

// HashMap (rápido con muchos items)
Map<String, Item> items = new HashMap<>();
for (int i = 0; i < 1_000_000; i++) {
    items.put("Producto" + i, new Item("Producto" + i, 10.00));
}
items.remove("Producto999999");  // O(1) - instantáneo
items.remove("Producto0");        // O(1) - instantáneo
```

**Ventajas:**
✓ Operaciones O(1) en lugar de O(n)
✓ Escalable a millones de items
✓ No puede haber duplicados (un item por nombre)
✓ Búsqueda por nombre natural

---

## Paso 10: Soporte para Cantidades

### Problema Original
```java
class Item {
    public String nombre;
    public double precio;
}

// Para agregar 5 camisetas, hay que crear 5 Items:
carrito.agregarItem(new Item("Camiseta", 20.99));
carrito.agregarItem(new Item("Camiseta", 20.99));
carrito.agregarItem(new Item("Camiseta", 20.99));
carrito.agregarItem(new Item("Camiseta", 20.99));
carrito.agregarItem(new Item("Camiseta", 20.99));
```

**Problemas:**
- Ineficiente e impractico
- Duplica items en la lista
- Mal diseño de datos

### Solución Implementada

**Item con cantidad integrada:**
```java
public class Item {
    private String nombre;
    private double precio;
    private int cantidad;  // ← Nueva propiedad
    
    public Item(String nombre, double precio) {
        this(nombre, precio, 1);  // Cantidad por defecto = 1
    }
    
    public Item(String nombre, double precio, int cantidad) {
        setNombre(nombre);
        setPrecio(precio);
        setCantidad(cantidad);
    }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { ... }
    
    public double calcularPrecioTotal() {
        return precio * cantidad;  // ← Cálculo automático
    }
    
    @Override
    public String toString() {
        return String.format("%s - €%.2f x %d = €%.2f",
            nombre, precio, cantidad, calcularPrecioTotal());
    }
}
```

**CarritoDeCompras inteligente:**
```java
public void agregarItem(Item item) {
    if (items.containsKey(item.getNombre())) {
        // Si ya existe, suma cantidades
        Item existente = items.get(item.getNombre());
        existente.setCantidad(existente.getCantidad() + item.getCantidad());
    } else {
        // Si es nuevo, lo agrega
        items.put(item.getNombre(), item);
    }
}

public void actualizarCantidad(String nombreItem, int nuevaCantidad) {
    if (!items.containsKey(nombreItem)) {
        throw new IllegalArgumentException("...");
    }
    items.get(nombreItem).setCantidad(nuevaCantidad);
}

public int obtenerCantidadTotal() {
    int total = 0;
    for (Item item : items.values()) {
        total += item.getCantidad();
    }
    return total;
}
```

**Uso ahora es intuitivo:**
```java
// Agregar 5 camisetas en una línea:
carrito.agregarItem(new Item("Camiseta", 20.99, 5));

// Agregar 3 más:
carrito.agregarItem(new Item("Camiseta", 20.99, 3));
// Total: 8 camisetas, sin duplicar Items

// Actualizar cantidad:
carrito.actualizarCantidad("Camiseta", 10);

// Obtener información:
System.out.println("Total items: " + carrito.obtenerCantidadTotal());
System.out.println("Total precio: " + carrito.calcularTotal());
```

**Resultado en el carrito:**
```
Camiseta - €20.99 x 8 = €167.92
Pantalones - €34.99 x 3 = €104.97
Zapatos - €89.99 x 1 = €89.99

Total: €362.88 (Total de items: 12)
```

**Ventajas:**
✓ Intuitivo y fácil de usar
✓ Eficiente (un solo Item por producto)
✓ Automático sumar cantidades al agregar
✓ Cálculo correcto de totales

---

## Resumen de la Refactorización

| Paso | Mejora | Beneficio |
|------|--------|----------|
| 1 | Separación programa principal | Reutilizable, testeable |
| 2 | Separación de responsabilidades | Código más limpio, mantenible |
| 3 | Reducción de acoplamiento | Menos dependencias entre clases |
| 4 | Encapsulación y validación | Datos siempre válidos |
| 5 | Eliminación de duplicación | Una sola fuente de verdad |
| 6 | Manejo de excepciones | Errores claros y controlados |
| 7 | Constantes | Fácil de modificar |
| 8 | toString() personalizado | Código limpio y profesional |
| 9 | Escalabilidad (HashMap) | O(1) en operaciones |
| 10 | Soporte para cantidades | Intuitivo y eficiente |

---

**Resultado final:** Un carrito de compras profesional, escalable, mantenible y fácil de usar.
