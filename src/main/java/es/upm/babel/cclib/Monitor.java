package es.upm.babel.cclib;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * Monitors.
 */
public class Monitor {
   /**
    * First barrier to mutex. This is also the barrier where signalled
    * threads are moved to.
    */
   private Lock mutex = new ReentrantLock();

   /**
    * Second barrier to mutex. This is the barrier where threads
    * entering the monitor get blocked if some signalling is halfway.
    */
   private Condition purgatory = mutex.newCondition();

   /**
    * Number of threads that were moved to the purgatory and have not
    * reached heaven yet (they may have been signalled).
    */
   private volatile int inPurgatory = 0;

   /**
    * Number of signalled threads which have not completed monitor
    * re-entrance, not counting those signalled in purgatory.
    */
   private volatile int pendingSignals = 0;

   /**
    * In order to force more arbitrary interleavings on the use of
    * Monitors, each call to await will introduce a random sleep
    * time. This attribute indicate the mean time of the sleep. If 0,
    * no sleep will be executed and the interleaving will be the
    * "natural" one.
    */
   private volatile int meanSleepTimeAfterAwait_ms = 0;

   /**
    * Random number generator for sleeping time after await.
    */
   private java.util.Random random = new java.util.Random(1);

   /**
    * Monitor constructor. No additional initialization required.
    */
   public Monitor() {
   }

   /**
    * If the parameter is greater than 0 more arbitrary interleavings
    * will be introduced.
    */
   public void setMeanSleepAfterAwait(int ms) {
      meanSleepTimeAfterAwait_ms = ms < 0 ? 0 : ms;
   }

   /**
    * Enters the monitor.
    */
   public void enter() {
      // First barrier
      mutex.lock();
      // The thread will go to purgatory unless no older blocked
      // thread exists that should be given access.
      if (pendingSignals > 0 || inPurgatory > 0) {
         inPurgatory++;
         try { purgatory.await(); }
         catch (InterruptedException e) {
            // TODO: manage this exception.
         }
         inPurgatory--;
      }
   }

   /**
    * Leaves the monitor.
    */
   public void leave() {
      // TODO: test the method is not being invoked outside of the monitor.

      // Signal a thread in the second barrier if no thread in a
      // condition is pendingSignals.
      if (pendingSignals == 0 && inPurgatory > 0) {
         purgatory.signal();
      }
      mutex.unlock();
   }

   /**
    * Returns a new condition associated to this monitor.
    */
   public Cond newCond() {
      return new Cond();
   }

   /**
    * Conditions.
    */
   public class Cond {
      /**
       * Implementation of the queue.
       */
      private Condition condition;

      /**
       * Number of threads blocked in this condition.
       */
      private int waiting;

      /**
       * Constructor.
       */
      private Cond() {
         condition = mutex.newCondition();
         waiting = 0;
      }

      /**
       * The thread that invokes this method will block until
       * signalled.
       */
      public void await() {
         // TODO: test the method is not being invoked outside of the monitor.
         waiting++;
         // Before blocking we need to signal threads in the second
         // barrier since they are not automatically unblocked
         if (pendingSignals == 0 && inPurgatory > 0 ) {
            purgatory.signal();
         }
         try { condition.await(); }
         catch (InterruptedException e) {
            // TODO: manage this exception.
         }
         pendingSignals--;
         if (meanSleepTimeAfterAwait_ms > 0) {
            try {
               Thread.sleep(random.nextInt(2 * meanSleepTimeAfterAwait_ms));
            } catch (InterruptedException e) {
            }
         }
      }

      /**
       * Signal (and continue) the first thread blocked in the
       * condition.
       */
      public void signal() {
         // TODO: test the method is not being invoked outside of the monitor.
         if (waiting > 0) {
            if (pendingSignals > 0) {
              throw new Error("Just one pending signal is allowed");
            }
            pendingSignals++;
            waiting--;
            // Moving a thread to the first barrier
            condition.signal();
         }
      }

      /**
       * Signal (and continue) all the threads blocked in the
       * condition.
       *
       * WARNING: We recommend not to use signalAll since the current
       * internal state of a resource is not guarantee for all
       * signalled threads (except for the first one).
       */
      private void signalAll() {
         // TODO: test the method is not being invoked outside of the monitor.
         pendingSignals += waiting;
         waiting = 0;
         // Moving all the threads to the first barrier
         condition.signalAll();
      }

      /**
       * Number of threads waiting in this Cond.
       */
      public int waiting() {
         // TODO: test the method is not being invoked outside of the monitor.
         return waiting;
      }
   }
}
