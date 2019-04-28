package es.upm.babel.cclib;

/**
 * Counting Semaphore.
 */
public class Semaphore {
   /**
    * Implementing on top of java.util.concurrent.Semaphore;
    */
   private java.util.concurrent.Semaphore n;

   /**
    * In order to force more arbitrary interleavings on the use of
    * Semaphores, each call to await will introduce a random sleep
    * time. This attribute indicate the mean time of the sleep. If 0,
    * no sleep will be executed and the interleaving will be the
    * "natural" one.
    */
   static private int meanSleepTimeAfterAwait_ms = 0;

   /**
    * Random number generator for sleeping time after await.
    */
   private java.util.Random random = new java.util.Random(1);

   /**
    * Semaphore constructor. Internal counter initizalised to 0.
    */
   public Semaphore() {
      n = new java.util.concurrent.Semaphore(0,true);
   }
   
   /**
    * Semaphore constructor. Internal counter initizalised to n if n
    * is positive, 0 otherwise.
    */
   public Semaphore(int n) {
      int i = n > 0 ? n : 0;
      this.n = new java.util.concurrent.Semaphore(i,true);
   }
   
   /**
    * If the parameter is greater than 0 more arbitrary interleavings
    * will be introduced.
    */
   static public void setMeanSleepAfterAwait(int ms) {
      meanSleepTimeAfterAwait_ms = ms < 0 ? 0 : ms;
   }

   /**
    * The Dijkstra P operation: delays until the internal counter is
    * greater than 0 and then decrements it.
    *
    * The usual name of this method is wait but, unfortunately, wait
    * is an important predefined method in Object and we don't want to
    * invalidate its semantics.
    */
   public void await() {
      boolean signaled = false;
      while (!signaled) {
         try {
            n.acquire();
            signaled = true;
         } catch (InterruptedException e) {
         }
      }
      if (meanSleepTimeAfterAwait_ms > 0) {
         try {
            Thread.sleep(random.nextInt(2 * meanSleepTimeAfterAwait_ms));
         } catch (InterruptedException e) {
         }
      }
   }

   /**
    * The Dijkstra V operation: increments the internal counter.
    */
   public void signal() {
      n.release();
   }
}
