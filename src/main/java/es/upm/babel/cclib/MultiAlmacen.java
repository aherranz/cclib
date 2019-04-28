package es.upm.babel.cclib;

/**
 * Interfaz para multialmacen almacén concurrente.
 */
public interface MultiAlmacen {
   /**
    * Almacena (como últimos) un paquete de productos. Si no hay
    * huecos suficientes el proceso que ejecute el método bloqueará
    * hasta que hayas huecos.
    */
   public void almacenar(Producto[] productos);

   /**
    * Extrae un paquete de productos del tamaño indicado por n. Si no
    * hay productos suficientes el proceso que ejecute el método
    * bloqueará hasta que los haya.
    */
   Producto[] extraer(int n);
}
