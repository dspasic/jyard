package biz.bitweise.jyard.performance;

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
    System.out.printf("Millis elapsed %f", (end - start)/4000.0);
  }

}
