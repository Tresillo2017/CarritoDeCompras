# Guía de Compilación y Ejecución

## Requisitos

- Java Development Kit (JDK) 8 o superior
- Terminal o línea de comandos (CMD, PowerShell, Bash, etc.)

## Pasos para Compilar

1. **Abre una terminal** en el directorio del proyecto (donde se encuentra la carpeta `src`)

2. **Compila todos los archivos Java:**

   ```bash
   javac src/*.java
   ```

   O, si prefieres ser más específico:

   ```bash
   javac src/Item.java src/CarritoDeCompras.java src/Main.java
   ```

3. **Verifica que la compilación fue exitosa:**

   Deberías ver archivos `.class` generados en la carpeta `src`:
   - `src/Item.class`
   - `src/CarritoDeCompras.class`
   - `src/Main.class`

## Pasos para Ejecutar

**Desde el directorio raíz del proyecto:**

```bash
java -cp src Main
```

O, si estás en la carpeta `src`:

```bash
cd src
java Main
cd ..
```

## Salida Esperada

Cuando ejecutes el programa, deberías ver algo como esto:

```
=== BIENVENIDO AL CARRITO DE COMPRAS ===

Agregando artículos al carrito...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Camiseta - €20.99 x 2 = €41.98
Pantalones - €34.99 x 1 = €34.99
Zapatos - €89.99 x 1 = €89.99

----------------------------------------
Total: €166.97 (Total de items: 4)
----------------------------------------

Agregando 1 camiseta más al carrito...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Camiseta - €20.99 x 3 = €62.97
Pantalones - €34.99 x 1 = €34.99
Zapatos - €89.99 x 1 = €89.99

----------------------------------------
Total: €187.96 (Total de items: 5)
----------------------------------------

Actualizando cantidad de pantalones a 3...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Camiseta - €20.99 x 3 = €62.97
Pantalones - €34.99 x 3 = €104.97
Zapatos - €89.99 x 1 = €89.99

----------------------------------------
Total: €257.94 (Total de items: 7)
----------------------------------------

Removiendo zapatos del carrito...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
Camiseta - €20.99 x 3 = €62.97
Pantalones - €34.99 x 3 = €104.97

----------------------------------------
Total: €167.94 (Total de items: 6)
----------------------------------------

=== INFORMACIÓN DEL CARRITO ===
Número de tipos de artículos: 2
Cantidad total de items: 6
Total del carrito: €167.94

=== PRUEBA DE MANEJO DE EXCEPCIONES ===
Intentando remover un artículo que no existe...
Error capturado: El artículo 'Artículo inexistente' no existe en el carrito

Intentando crear un item con precio inválido...
Error capturado: El precio debe estar entre 0.01€ y 1000000.00€. Precio proporcionado: -10.00€

Vaciando el carrito...

----------------------------------------
Detalles del carrito de compras:
----------------------------------------
El carrito está vacío
----------------------------------------
Total: €0.00 (Total de items: 0)
----------------------------------------
```

## Troubleshooting

### Error: "javac: command not found" o "javac no se reconoce"

**Solución:** Java no está instalado o no está en el PATH. Descarga el JDK desde [oracle.com](https://www.oracle.com/java/technologies/downloads/) o instala OpenJDK.

### Error: "clase Item no encontrada"

**Solución:** Asegúrate de que estás en el directorio correcto y de que todos los archivos `.java` están en la carpeta `src`.

### Error: "CLASSPATH no está definido"

**Solución:** Usa la opción `-cp src` al ejecutar:

```bash
java -cp src Main
```

### Los archivos .class no se crean

**Solución:** Verifica que tengas permisos de escritura en la carpeta `src` y que todos los archivos Java sean válidos.

## Limpiar Archivos Compilados

Si deseas eliminar los archivos `.class` generados:

**En Linux/Mac:**
```bash
rm src/*.class
```

**En Windows (CMD):**
```bash
del src\*.class
```

**En Windows (PowerShell):**
```powershell
Remove-Item src\*.class
```

## Generar Documentación Javadoc

Para generar documentación HTML de las clases:

```bash
javadoc -d docs src/*.java
```

Luego abre `docs/index.html` en tu navegador para ver la documentación.

## Recursos Adicionales

- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [Java API Documentation](https://docs.oracle.com/javase/8/docs/api/)
- [Maven Build Tool](https://maven.apache.org/) (para proyectos más grandes)

---

¡Listo! Ahora puedes compilar y ejecutar el proyecto CarritoDeCompras.
