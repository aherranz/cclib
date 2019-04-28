package es.upm.babel.cclib;

/**
 * Las instancias son procesos consumidores.
 */
public class Consumidor extends Thread {
   /**
    * Almacen compartido.
    */
   private Almacen almacenCompartido;

   /**
    * Crea un consumidor.
    * @param a es el almacen del que se extraerán los productos a consumir.
    */
   public Consumidor(Almacen a) {
      almacenCompartido = a;
   }

    /**
     * Los consumidores extraen productos del almacen y los consumen.
     */
    public void run() {
       Producto p;
       while (true) {
          p = almacenCompartido.extraer();
          Consumo.consumir(p);
       }
    }
}
