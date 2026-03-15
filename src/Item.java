/**
 * Clase que representa un artículo individual en el carrito de compras.
 * 
 * Esta clase encapsula la información de un producto, incluyendo su nombre,
 * precio y cantidad. Proporciona validación de datos y métodos de acceso.
 * 
 * @author Equipo de Desarrollo
 * @version 2.0
 */
public class Item {
    
    // Constantes para validación
    private static final double PRECIO_MINIMO = 0.01;
    private static final double PRECIO_MAXIMO = 1_000_000.00;
    
    // Atributos privados
    private String nombre;
    private double precio;
    private int cantidad;
    
    /**
     * Constructor que crea un nuevo Item con el nombre y precio especificados.
     * La cantidad inicial se establece en 1.
     * 
     * @param nombre El nombre del artículo
     * @param precio El precio unitario del artículo
     * @throws IllegalArgumentException si el precio es inválido o el nombre es nulo/vacío
     */
    public Item(String nombre, double precio) {
        this(nombre, precio, 1);
    }
    
    /**
     * Constructor que crea un nuevo Item con el nombre, precio y cantidad especificados.
     * 
     * @param nombre El nombre del artículo
     * @param precio El precio unitario del artículo
     * @param cantidad La cantidad de artículos
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public Item(String nombre, double precio, int cantidad) {
        setNombre(nombre);
        setPrecio(precio);
        setCantidad(cantidad);
    }
    
    /**
     * Obtiene el nombre del artículo.
     * 
     * @return El nombre del artículo
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del artículo.
     * 
     * @param nombre El nuevo nombre del artículo
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artículo no puede ser nulo o vacío");
        }
        this.nombre = nombre.trim();
    }
    
    /**
     * Obtiene el precio unitario del artículo.
     * 
     * @return El precio unitario
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Establece el precio unitario del artículo.
     * 
     * @param precio El nuevo precio unitario
     * @throws IllegalArgumentException si el precio está fuera del rango válido
     */
    public void setPrecio(double precio) {
        if (precio < PRECIO_MINIMO || precio > PRECIO_MAXIMO) {
            throw new IllegalArgumentException(
                String.format("El precio debe estar entre %.2f€ y %.2f€. Precio proporcionado: %.2f€",
                    PRECIO_MINIMO, PRECIO_MAXIMO, precio)
            );
        }
        this.precio = precio;
    }
    
    /**
     * Obtiene la cantidad de artículos.
     * 
     * @return La cantidad de artículos
     */
    public int getCantidad() {
        return cantidad;
    }
    
    /**
     * Establece la cantidad de artículos.
     * 
     * @param cantidad La nueva cantidad
     * @throws IllegalArgumentException si la cantidad es menor o igual a 0
     */
    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        this.cantidad = cantidad;
    }
    
    /**
     * Calcula el precio total de este artículo (precio unitario * cantidad).
     * 
     * @return El precio total
     */
    public double calcularPrecioTotal() {
        return precio * cantidad;
    }
    
    /**
     * Devuelve una representación en string del artículo con formato.
     * 
     * @return Cadena con el formato: "Nombre - €precio x cantidad = €total"
     */
    @Override
    public String toString() {
        return String.format("%s - €%.2f x %d = €%.2f",
            nombre, precio, cantidad, calcularPrecioTotal());
    }
    
    /**
     * Compara dos Items basándose en su nombre.
     * 
     * @param obj Objeto a comparar
     * @return true si el nombre es igual, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item other = (Item) obj;
        return nombre.equals(other.nombre);
    }
    
    /**
     * Calcula el código hash del Item basándose en su nombre.
     * 
     * @return El código hash
     */
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
