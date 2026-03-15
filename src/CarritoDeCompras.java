import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Clase que representa un carrito de compras.
 * 
 * Gestiona una colección de artículos con sus cantidades, permitiendo agregar,
 * remover y consultar información sobre los productos. Utiliza un HashMap para
 * optimizar la búsqueda y actualización de cantidades de artículos.
 * 
 * @author Equipo de Desarrollo
 * @version 2.0
 */
public class CarritoDeCompras {
    
    // Constantes
    private static final String MONEDA = "€";
    private static final String ENCABEZADO = "Detalles del carrito de compras:";
    private static final String SEPARADOR = "----------------------------------------";
    private static final String LINEA_VACIA = "";
    private static final String TOTAL = "Total: ";
    
    // Estructura de datos: HashMap para mejor escalabilidad
    // Key: nombre del item (para búsqueda rápida)
    // Value: Item con su cantidad actualizada
    private Map<String, Item> items;
    
    /**
     * Constructor que inicializa un carrito de compras vacío.
     */
    public CarritoDeCompras() {
        this.items = new HashMap<>();
    }
    
    /**
     * Agrega un artículo al carrito.
     * Si el artículo ya existe, incrementa su cantidad.
     * 
     * @param item El artículo a agregar
     * @throws IllegalArgumentException si el item es nulo
     */
    public void agregarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }
        
        if (items.containsKey(item.getNombre())) {
            // Si el item ya existe, incrementar cantidad
            Item itemExistente = items.get(item.getNombre());
            itemExistente.setCantidad(itemExistente.getCantidad() + item.getCantidad());
        } else {
            // Si es nuevo, agregarlo
            items.put(item.getNombre(), item);
        }
    }
    
    /**
     * Remueve un artículo del carrito por nombre.
     * 
     * @param nombreItem El nombre del artículo a remover
     * @throws IllegalArgumentException si el artículo no existe en el carrito
     */
    public void removerItem(String nombreItem) {
        if (nombreItem == null || nombreItem.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del item no puede ser nulo o vacío");
        }
        
        if (!items.containsKey(nombreItem)) {
            throw new IllegalArgumentException(
                "El artículo '" + nombreItem + "' no existe en el carrito"
            );
        }
        
        items.remove(nombreItem);
    }
    
    /**
     * Remueve un artículo específico del carrito.
     * 
     * @param item El artículo a remover
     * @throws IllegalArgumentException si el artículo no existe en el carrito
     */
    public void removerItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }
        removerItem(item.getNombre());
    }
    
    /**
     * Calcula el precio total de todos los artículos en el carrito.
     * 
     * @return El precio total del carrito
     */
    public double calcularTotal() {
        double total = 0;
        for (Item item : items.values()) {
            total += item.calcularPrecioTotal();
        }
        return total;
    }
    
    /**
     * Obtiene la cantidad total de artículos en el carrito.
     * 
     * @return La cantidad total de items (considerando cantidades)
     */
    public int obtenerCantidadTotal() {
        int cantidadTotal = 0;
        for (Item item : items.values()) {
            cantidadTotal += item.getCantidad();
        }
        return cantidadTotal;
    }
    
    /**
     * Obtiene la cantidad de tipos de artículos diferentes en el carrito.
     * 
     * @return El número de artículos diferentes
     */
    public int obtenerNumeroArticulos() {
        return items.size();
    }
    
    /**
     * Verifica si el carrito está vacío.
     * 
     * @return true si el carrito no tiene artículos, false en caso contrario
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }
    
    /**
     * Obtiene un artículo específico del carrito por nombre.
     * 
     * @param nombreItem El nombre del artículo
     * @return El artículo si existe, null en caso contrario
     */
    public Item obtenerItem(String nombreItem) {
        return items.get(nombreItem);
    }
    
    /**
     * Vacía completamente el carrito, removiendo todos los artículos.
     */
    public void vaciar() {
        items.clear();
    }
    
    /**
     * Actualiza la cantidad de un artículo en el carrito.
     * 
     * @param nombreItem El nombre del artículo
     * @param nuevaCantidad La nueva cantidad
     * @throws IllegalArgumentException si el artículo no existe o la cantidad es inválida
     */
    public void actualizarCantidad(String nombreItem, int nuevaCantidad) {
        if (!items.containsKey(nombreItem)) {
            throw new IllegalArgumentException(
                "El artículo '" + nombreItem + "' no existe en el carrito"
            );
        }
        
        items.get(nombreItem).setCantidad(nuevaCantidad);
    }
    
    /**
     * Imprime el detalle completo del carrito en la consola.
     * Incluye lista de artículos y total.
     */
    public void imprimirDetalle() {
        System.out.println(SEPARADOR);
        System.out.println(ENCABEZADO);
        System.out.println(SEPARADOR);
        
        if (estaVacio()) {
            System.out.println("El carrito está vacío");
        } else {
            imprimirListaArticulos();
        }
        
        System.out.println(SEPARADOR);
        imprimirTotal();
        System.out.println(SEPARADOR);
    }
    
    /**
     * Imprime la lista de artículos del carrito.
     * Responsabilidad delegada para mayor claridad.
     */
    private void imprimirListaArticulos() {
        for (Item item : items.values()) {
            System.out.println(item.toString());
        }
        System.out.println(LINEA_VACIA);
    }
    
    /**
     * Imprime el total del carrito con información adicional.
     * Responsabilidad delegada para mayor claridad.
     */
    private void imprimirTotal() {
        double total = calcularTotal();
        int cantidadTotal = obtenerCantidadTotal();
        System.out.printf("%s%s%.2f (Total de items: %d)%n", 
            TOTAL, MONEDA, total, cantidadTotal);
    }
}
