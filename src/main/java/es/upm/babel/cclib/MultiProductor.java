package es.upm.babel.cclib;

/**
 * Las instancias son procesos multiproductores (producen paquetes de productos).
 */
public class MultiProductor extends Thread {
   /**
    * Multialmacen compartido.
    */
   private MultiAlmacen multiAlmacenCompartido;

   /**
    * Máximo número de productos a producir y almacenar (por paquete).
    */
   private int maxProd;

   /**
    * El generador de números aleatorios es común a todos los productores.
    */
    private static java.util.Random random = new java.util.Random(3);

   /**
    * Crea un multiproductor.
    * @param ma es el multialmacen compartido que usará el productor.
    * @param max es el número máximo de productos por paquete que producirá el productor.
    */
   public MultiProductor(MultiAlmacen ma,
                         int max) {
      multiAlmacenCompartido = ma;
      maxProd = max;
   }

    /**
     * Los productores producen paquetes de productos y los almacenan.
     */
    public void run() {
       while (true) {
          Producto[] ps;
          int n;
          n = random.nextInt(maxProd) + 1;
          ps = Fabrica.producir(n);
          ConcIO.printfnl("inicio almacenamiento de " + n + " productos...");
          multiAlmacenCompartido.almacenar(ps);
          ConcIO.printfnl("fin almacenamiento de " + n + " productos...");
      }
    }
}
