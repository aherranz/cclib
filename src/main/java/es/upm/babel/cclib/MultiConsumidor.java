package es.upm.babel.cclib;

/**
 * Las instancias son procesos multiconsumidores (consumen paquetes de productos).
 */
public class MultiConsumidor extends Thread {
   /**
    * Multialmacen compartido.
    */
   private MultiAlmacen multiAlmacenCompartido;

   /**
    * Máximo número de productos a extraer y consumir (por paquete).
    */
   private int maxCons;

   /**
    * El generador de números aleatorios es común a todos los consumidores.
    */
    private static java.util.Random random = new java.util.Random(4);

   /**
    * Crea un multiconsumidor.
    * @param ma es el multialmacen compartido que usará el consumidor.
    * @param max es el número máximo de productos por paquete que extraerá el consumidor.
    */
   public MultiConsumidor(MultiAlmacen ma,
                          int max) {
      multiAlmacenCompartido = ma;
      maxCons = max;
   }

    /**
     * Los consumidores extraen paquetes de productos y los consumen.
     */
    public void run() {
       while (true) {
          Producto[] ps;
          int n;
          n = random.nextInt(maxCons) + 1;
          ConcIO.printfnl("inicio extracción de " + n + " productos...");
          ps = multiAlmacenCompartido.extraer(n);
          ConcIO.printfnl("fin de extracción de " + n + " productos...");
          Consumo.consumir(ps);
       }
    }
}
