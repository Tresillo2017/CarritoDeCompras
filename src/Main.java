/**
 * Clase principal que demuestra el funcionamiento del carrito de compras.
 * 
 * Esta clase es responsable de la ejecución del programa y la interacción
 * del usuario con el carrito de compras. Está completamente separada de la
 * lógica de negocio del carrito.
 * 
 * @author Equipo de Desarrollo
 * @version 2.0
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación.
     * Demuestra las funcionalidades del carrito de compras.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            // Crear una instancia del carrito
            CarritoDeCompras carrito = new CarritoDeCompras();
            
            System.out.println("=== BIENVENIDO AL CARRITO DE COMPRAS ===\n");
            
            // Crear items con sus cantidades
            Item camiseta = new Item("Camiseta", 20.99, 2);
            Item pantalones = new Item("Pantalones", 34.99, 1);
            Item zapatos = new Item("Zapatos", 89.99, 1);
            
            // Agregar items al carrito
            System.out.println("Agregando artículos al carrito...\n");
            carrito.agregarItem(camiseta);
            carrito.agregarItem(pantalones);
            carrito.agregarItem(zapatos);
            
            // Mostrar detalles del carrito
            carrito.imprimirDetalle();
            
            // Ejemplo: intentar agregar más cantidad del mismo item
            System.out.println("\nAgregando 1 camiseta más al carrito...\n");
            carrito.agregarItem(new Item("Camiseta", 20.99, 1));
            carrito.imprimirDetalle();
            
            // Ejemplo: actualizar cantidad de un item
            System.out.println("\nActualizando cantidad de pantalones a 3...\n");
            carrito.actualizarCantidad("Pantalones", 3);
            carrito.imprimirDetalle();
            
            // Ejemplo: remover un item
            System.out.println("\nRemoviendo zapatos del carrito...\n");
            carrito.removerItem("Zapatos");
            carrito.imprimirDetalle();
            
            // Mostrar información adicional del carrito
            System.out.println("\n=== INFORMACIÓN DEL CARRITO ===");
            System.out.println("Número de tipos de artículos: " + carrito.obtenerNumeroArticulos());
            System.out.println("Cantidad total de items: " + carrito.obtenerCantidadTotal());
            System.out.println("Total del carrito: €" + String.format("%.2f", carrito.calcularTotal()));
            
            // Ejemplo: intentar remover un item que no existe (manejo de excepciones)
            System.out.println("\n=== PRUEBA DE MANEJO DE EXCEPCIONES ===");
            System.out.println("Intentando remover un artículo que no existe...");
            try {
                carrito.removerItem("Artículo inexistente");
            } catch (IllegalArgumentException e) {
                System.out.println("Error capturado: " + e.getMessage());
            }
            
            // Ejemplo: intentar crear un item con precio inválido
            System.out.println("\nIntentando crear un item con precio inválido...");
            try {
                Item itemInvalido = new Item("Producto", -10.00);
            } catch (IllegalArgumentException e) {
                System.out.println("Error capturado: " + e.getMessage());
            }
            
            // Vaciar el carrito
            System.out.println("\n\nVaciando el carrito...\n");
            carrito.vaciar();
            carrito.imprimirDetalle();
            
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
