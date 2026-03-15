# Ejemplos de Uso del Carrito de Compras

## Ejemplos Básicos

### Ejemplo 1: Carrito Simple

```java
// Crear carrito
CarritoDeCompras carrito = new CarritoDeCompras();

// Crear y agregar items
Item manzana = new Item("Manzana", 1.50, 3);
Item pan = new Item("Pan", 2.99, 1);

carrito.agregarItem(manzana);
carrito.agregarItem(pan);

// Mostrar el carrito
carrito.imprimirDetalle();
```

**Salida:**
```
----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Manzana - €1.50 x 3 = €4.50
Pan - €2.99 x 1 = €2.99

----------------------------------------
Total: €7.49 (Total de items: 4)
----------------------------------------
```

### Ejemplo 2: Agregar Cantidad del Mismo Item

```java
CarritoDeCompras carrito = new CarritoDeCompras();

// Agregar 2 camisetas
carrito.agregarItem(new Item("Camiseta", 20.99, 2));

// Agregar 3 camisetas más del mismo tipo
// Automáticamente se suma la cantidad a 5
carrito.agregarItem(new Item("Camiseta", 20.99, 3));

carrito.imprimirDetalle();
```

**Salida:**
```
----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Camiseta - €20.99 x 5 = €104.95

----------------------------------------
Total: €104.95 (Total de items: 5)
----------------------------------------
```

### Ejemplo 3: Actualizar Cantidad

```java
CarritoDeCompras carrito = new CarritoDeCompras();

carrito.agregarItem(new Item("Laptop", 999.99, 1));
carrito.agregarItem(new Item("Mouse", 29.99, 2));

// El usuario decide cambiar a 2 laptops
carrito.actualizarCantidad("Laptop", 2);

carrito.imprimirDetalle();
```

**Salida:**
```
----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Laptop - €999.99 x 2 = €1999.98
Mouse - €29.99 x 2 = €59.98

----------------------------------------
Total: €2059.96 (Total de items: 4)
----------------------------------------
```

---

## Ejemplos de Validación y Excepciones

### Ejemplo 4: Crear Item con Precio Inválido

```java
try {
    // Precio negativo - ¡Inválido!
    Item item = new Item("Producto", -50.00);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```

**Salida:**
```
Error: El precio debe estar entre 0.01€ y 1000000.00€. Precio proporcionado: -50.00€
```

### Ejemplo 5: Crear Item con Nombre Inválido

```java
try {
    // Nombre vacío - ¡Inválido!
    Item item = new Item("", 25.00);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```

**Salida:**
```
Error: El nombre del artículo no puede ser nulo o vacío
```

### Ejemplo 6: Crear Item con Cantidad Inválida

```java
try {
    // Cantidad negativa - ¡Inválido!
    Item item = new Item("Producto", 10.00, -5);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```

**Salida:**
```
Error: La cantidad debe ser mayor que 0
```

### Ejemplo 7: Remover Item Inexistente

```java
CarritoDeCompras carrito = new CarritoDeCompras();
carrito.agregarItem(new Item("Camiseta", 20.99));

try {
    // Intentar remover algo que no existe
    carrito.removerItem("Pantalones");
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```

**Salida:**
```
Error: El artículo 'Pantalones' no existe en el carrito
```

---

## Ejemplos de Consultas de Información

### Ejemplo 8: Obtener Información del Carrito

```java
CarritoDeCompras carrito = new CarritoDeCompras();

carrito.agregarItem(new Item("Camiseta", 20.99, 2));
carrito.agregarItem(new Item("Pantalones", 34.99, 1));
carrito.agregarItem(new Item("Zapatos", 89.99, 1));

// Consultar información
System.out.println("Tipos de artículos: " + carrito.obtenerNumeroArticulos());
System.out.println("Total de items: " + carrito.obtenerCantidadTotal());
System.out.println("Total a pagar: €" + String.format("%.2f", carrito.calcularTotal()));

// Obtener un item específico
Item item = carrito.obtenerItem("Camiseta");
if (item != null) {
    System.out.println("Camiseta en carrito: " + item);
}
```

**Salida:**
```
Tipos de artículos: 3
Total de items: 4
Total a pagar: €166.97
Camiseta en carrito: Camiseta - €20.99 x 2 = €41.98
```

### Ejemplo 9: Verificar si el Carrito Está Vacío

```java
CarritoDeCompras carrito = new CarritoDeCompras();

if (carrito.estaVacio()) {
    System.out.println("El carrito está vacío");
} else {
    System.out.println("El carrito tiene items");
}

// Agregar un item
carrito.agregarItem(new Item("Libro", 15.99));

if (carrito.estaVacio()) {
    System.out.println("El carrito está vacío");
} else {
    System.out.println("El carrito tiene items");
}
```

**Salida:**
```
El carrito está vacío
El carrito tiene items
```

---

## Ejemplos Avanzados

### Ejemplo 10: Simulación de Compra Completa

```java
// Crear carrito
CarritoDeCompras carrito = new CarritoDeCompras();

System.out.println("=== TIENDA EN LÍNEA ===\n");

// Fase 1: Agregar productos
System.out.println("Fase 1: Agregando productos al carrito...\n");
carrito.agregarItem(new Item("Monitor 24\"", 249.99, 1));
carrito.agregarItem(new Item("Teclado Mecánico", 149.99, 1));
carrito.agregarItem(new Item("Mouse", 79.99, 2));

carrito.imprimirDetalle();

// Fase 2: Usuario cambia de idea
System.out.println("\nFase 2: El usuario cambia la cantidad de mice...\n");
carrito.actualizarCantidad("Mouse", 1);
carrito.imprimirDetalle();

// Fase 3: Usuario agrega más items
System.out.println("\nFase 3: El usuario agrega más items...\n");
carrito.agregarItem(new Item("Mousepad", 29.99, 1));
carrito.imprimirDetalle();

// Fase 4: Usuario se arrepiente de un item
System.out.println("\nFase 4: El usuario se arrepiente del mousepad...\n");
carrito.removerItem("Mousepad");
carrito.imprimirDetalle();

// Resumen final
System.out.println("\n=== RESUMEN FINAL ===");
System.out.println("Artículos diferentes: " + carrito.obtenerNumeroArticulos());
System.out.println("Total de items: " + carrito.obtenerCantidadTotal());
System.out.println("Total a pagar: €" + String.format("%.2f", carrito.calcularTotal()));
```

**Salida esperada:**
```
=== TIENDA EN LÍNEA ===

Fase 1: Agregando productos al carrito...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Monitor 24" - €249.99 x 1 = €249.99
Teclado Mecánico - €149.99 x 1 = €149.99
Mouse - €79.99 x 2 = €159.98

----------------------------------------
Total: €559.97 (Total de items: 4)
----------------------------------------

Fase 2: El usuario cambia la cantidad de mice...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Monitor 24" - €249.99 x 1 = €249.99
Teclado Mecánico - €149.99 x 1 = €149.99
Mouse - €79.99 x 1 = €79.99

----------------------------------------
Total: €479.98 (Total de items: 3)
----------------------------------------

Fase 3: El usuario agrega más items...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Monitor 24" - €249.99 x 1 = €249.99
Teclado Mecánico - €149.99 x 1 = €149.99
Mouse - €79.99 x 1 = €79.99
Mousepad - €29.99 x 1 = €29.99

----------------------------------------
Total: €509.97 (Total de items: 4)
----------------------------------------

Fase 4: El usuario se arrepiente del mousepad...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Monitor 24" - €249.99 x 1 = €249.99
Teclado Mecánico - €149.99 x 1 = €149.99
Mouse - €79.99 x 1 = €79.99

----------------------------------------
Total: €479.98 (Total de items: 3)
----------------------------------------

=== RESUMEN FINAL ===
Artículos diferentes: 3
Total de items: 3
Total a pagar: €479.98
```

### Ejemplo 11: Gestión de Excepciones Robusta

```java
public class GestorCarrito {
    
    private CarritoDeCompras carrito;
    
    public GestorCarrito() {
        this.carrito = new CarritoDeCompras();
    }
    
    public void agregarItemSeguro(String nombre, double precio, int cantidad) {
        try {
            Item item = new Item(nombre, precio, cantidad);
            carrito.agregarItem(item);
            System.out.println("✓ " + nombre + " agregado exitosamente");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error al agregar " + nombre + ": " + e.getMessage());
        }
    }
    
    public void removerItemSeguro(String nombre) {
        try {
            carrito.removerItem(nombre);
            System.out.println("✓ " + nombre + " removido exitosamente");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error al remover " + nombre + ": " + e.getMessage());
        }
    }
    
    public void actualizarCantidadSegura(String nombre, int cantidad) {
        try {
            carrito.actualizarCantidad(nombre, cantidad);
            System.out.println("✓ Cantidad de " + nombre + " actualizada a " + cantidad);
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error al actualizar cantidad: " + e.getMessage());
        }
    }
    
    public void mostrarCarrito() {
        carrito.imprimirDetalle();
    }
}

// Uso:
public static void main(String[] args) {
    GestorCarrito gestor = new GestorCarrito();
    
    gestor.agregarItemSeguro("Laptop", 999.99, 1);
    gestor.agregarItemSeguro("Mouse", -30.00, 1);  // Error
    gestor.agregarItemSeguro("Mouse", 29.99, 2);
    
    gestor.mostrarCarrito();
    
    gestor.removerItemSeguro("Monitor");  // Error
    gestor.removerItemSeguro("Mouse");
    
    gestor.mostrarCarrito();
}
```

**Salida:**
```
✓ Laptop agregado exitosamente
✗ Error al agregar Mouse: El precio debe estar entre 0.01€ y 1000000.00€. Precio proporcionado: -30.00€
✓ Mouse agregado exitosamente

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Laptop - €999.99 x 1 = €999.99
Mouse - €29.99 x 2 = €59.98

----------------------------------------
Total: €1059.97 (Total de items: 3)
----------------------------------------

✗ Error al remover Monitor: El artículo 'Monitor' no existe en el carrito
✓ Mouse removido exitosamente

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Laptop - €999.99 x 1 = €999.99

----------------------------------------
Total: €999.99 (Total de items: 1)
----------------------------------------
```

### Ejemplo 12: Carrito Multicategoría

```java
CarritoDeCompras carrito = new CarritoDeCompras();

// Electrónica
carrito.agregarItem(new Item("Smartphone", 699.99, 1));
carrito.agregarItem(new Item("Headphones", 149.99, 2));

// Ropa
carrito.agregarItem(new Item("Camiseta", 29.99, 3));
carrito.agregarItem(new Item("Jeans", 79.99, 1));

// Libros
carrito.agregarItem(new Item("Java Programming", 45.99, 2));
carrito.agregarItem(new Item("Clean Code", 49.99, 1));

// Mostrar resumen
System.out.println("=== RESUMEN DE COMPRA ===\n");
carrito.imprimirDetalle();

System.out.println("\n=== ESTADÍSTICAS ===");
System.out.println("Tipos diferentes de productos: " + carrito.obtenerNumeroArticulos());
System.out.println("Cantidad total de items: " + carrito.obtenerCantidadTotal());
System.out.println("Ticket a pagar: €" + String.format("%.2f", carrito.calcularTotal()));
```

---

## Patrones de Uso Recomendados

### Patrón 1: Crear y Verificar

```java
Item item = new Item("Producto", 25.00);
if (item != null) {
    carrito.agregarItem(item);
    System.out.println("Item agregado");
}
```

### Patrón 2: Validar Antes de Modificar

```java
String nombreItem = "Camiseta";
Item item = carrito.obtenerItem(nombreItem);
if (item != null) {
    carrito.actualizarCantidad(nombreItem, 5);
}
```

### Patrón 3: Try-Catch para Operaciones Críticas

```java
try {
    carrito.removerItem(nombreItem);
} catch (IllegalArgumentException e) {
    // Registrar error, notificar usuario, etc.
    System.err.println("No se pudo remover el item: " + e.getMessage());
}
```

---

## Conclusión

El carrito de compras refactorizado proporciona una API clara, segura y fácil de usar para gestionar compras. La validación automática, el manejo de excepciones y la encapsulación hacen que sea confiable para producción.
