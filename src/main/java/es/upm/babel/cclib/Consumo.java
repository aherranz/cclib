package es.upm.babel.cclib;

/**
 * Clase <em>singleton</em> (sin instancias) para simular el consumo
 * de productos.
 */
public class Consumo {
    /**
     * Tiempo medio de consumo.
     */
    static private int tiempo_medio_cons_ms = 1000;

    /**
     * Generador de números aleatorios (tiempo de consumo).
     */
    static private java.util.Random random = new java.util.Random(2);

    /**
     * No es posible generar objetos de la clase Consumo.
     */
    private Consumo() {
    }

    /**
     * Establece el tiempo medio de consumo de cada producto (en
     * milisegundos). Lo establece a 0 si el parámetro es negativo.
     */
    static public void establecerTiempoMedioCons(int tmc_ms) {
       tiempo_medio_cons_ms = tmc_ms < 0 ? 0 : tmc_ms;
    }

    /**
     * Simula el consumo de un producto.
     */
    static public void consumir(Producto prod) {
      ConcIO.printfnl("inicio consumo: " + prod + "...");
      simularConsumo(prod);
      ConcIO.printfnl("fin consumo: " + prod);
    }

    /**
     * Simula el consumo de un <em>paquete</em> de productos.
     */
    static public void consumir(Producto[] prods) {
        ConcIO.printfnl("inicio consumo de "
                        + prods.length + " productos...");
        for (int i = 0; i < prods.length; i++) {
          simularConsumo(prods[i]);
          ConcIO.printfnl("producto consumido: " + prods[i]);
        }
        ConcIO.printfnl("fin consumo del paquete de "
                        + prods.length + " productos");
    }

    /**
     * Simula el consumo de un producto.
     *
     * @return tiempo de consumo
     */
    static public void simularConsumo(Producto prod) {
      if (tiempo_medio_cons_ms > 0) {
        int t = random.nextInt(2 * tiempo_medio_cons_ms);
        try {
          Thread.sleep(t);
        } catch (Exception ex) {
          ConcIO.printfnl("excepción capturada: " + ex);
          ex.printStackTrace();
        }
      }
    }
}
