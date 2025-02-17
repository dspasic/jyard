package biz.bitweise.jyard.performance;

/**
 * One of the easiest ways to see the action and behavior of a scheduler is to try to observe the
 * overhead imposed by the OS to achieve scheduling. The following code executes 2,000 separate 2 ms
 * sleeps. Each of these sleeps will involve the thread being sent to the back of the run queue, and
 * having to wait for a new time quantum. So, the total elapsed time of the code gives us some idea
 * of the overhead of scheduling for a typical process
 */
public class OsSchedulerOverload {

  public void overloadOsScheduler() {
    long start = System.currentTimeMillis();
    for (var i = 0; i < 2_000; i++) {
      try {
        Thread.sleep(2);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    long end = System.currentTimeMillis();
    System.out.printf("Millis elapsed %f", (end - start) / 4000.0);
  }
}
