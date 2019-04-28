package es.upm.babel.cclib;

/**
 * Las instancias son procesos productores.
 */
public class Productor extends Thread {
   /*
    * Almacen compartido.
    */
   private Almacen almacenCompartido;

   /**
    * Crea un productor.
    * @param a es el almacen compartido que usará el productor.
    */
   public Productor(Almacen a) {
      almacenCompartido = a;
   }

    /**
     * Los productores producen productos y los almacenan.
     */
    public void run() {
       Producto p;
       while (true) {
          p = Fabrica.producir();
          almacenCompartido.almacenar(p);
      }
    }
}
