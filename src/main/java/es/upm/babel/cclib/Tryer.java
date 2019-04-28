package es.upm.babel.cclib;

/**
 * A wrapper process that tries to monitor if the execution of the
 * abstract method toTry is blocked or not.
 */
public abstract class Tryer extends Thread {
   volatile private boolean started = false;
   volatile private boolean blocked = true;
   volatile private boolean raisedException = false;
   volatile private Throwable throwable = null; 

   public boolean isBlocked() {
      if (!started) gimmeTime(0);
      return blocked;
   }

   public boolean raisedException() {
      if (!started) gimmeTime(0);
      return raisedException;
   }

   public Throwable getException() {
       return throwable;
   }

   // Un sleep sin excepciones
   public static void sleep (int ms) {
      long initialTimeMillis = System.currentTimeMillis();
      long remainTimeMillis = ms;
      
      while (remainTimeMillis > 0) {
         try {
            Thread.sleep(remainTimeMillis);
         }
         catch (InterruptedException e) {
         }
         remainTimeMillis =
            ms - (System.currentTimeMillis() - initialTimeMillis);
      }
   }

   public  void gimmeTime(int ms) {
      while (!started) { }
      Tryer.sleep(ms);
   }

   public void run() {
      blocked = true;
      started = true;
      try { this.toTry(); blocked = false; }
      catch (Throwable t) { raisedException = true; throwable = t; }
   }

   abstract public void toTry() throws Throwable;
}
