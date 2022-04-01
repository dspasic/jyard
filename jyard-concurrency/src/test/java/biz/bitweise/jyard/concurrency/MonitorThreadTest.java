package biz.bitweise.jyard.concurrency;

import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Synchronizing on Value-Based Classes
 * <p>
 * Demonstrating how to monitor Threads
 * <p>
 * We can find out whether a thread is currently locking with synchronized. The ThreadMXBean
 * provides ThreadInfo objects on which we can get information about the locks using
 * getLockedMonitors().
 *
 * @see <a href="https://www.javaspecialists.eu/archive/Issue299-Synchronizing-on-Value-Based-Classes.html">Synchronizing
 * on Value-Based Classes</a>
 */
public class MonitorThreadTest {

  @Test
  public void monitorDemoTest() {
    printInfo();

    var lock1 = new Object();
    synchronized (lock1) {
      printInfo();
    }

    var lock2 = new Object();
    synchronized (lock1) {
      synchronized (lock2) {
        printInfo();
      }
    }
  }

  private static void printInfo() {
    System.out.println("Monitors locked by current thread:");
    var monitors = Monitor.findLockedMonitors();
    if (monitors.length == 0) {
      System.out.println("\tnone");
    } else {
      Arrays.stream(monitors)
          .forEach(m -> System.out.println("\t" + m));
    }
    System.out.println();
  }

  static class Monitor {

    private static final ThreadMXBean tmb = ManagementFactory.getThreadMXBean();

    private Monitor() {
    }

    public static MonitorInfo[] findLockedMonitors() {
      long[] ids = {Thread.currentThread().getId()};
      var threadInfo = tmb.getThreadInfo(ids, true, false)[0];
      return threadInfo.getLockedMonitors();
    }
  }
}
