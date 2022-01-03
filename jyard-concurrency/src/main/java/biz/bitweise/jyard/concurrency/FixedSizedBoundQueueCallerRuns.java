package biz.bitweise.jyard.concurrency;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Creating a Fixed-sized Thread Pool with a Bounded Queue and the Caller-runs Saturation Policy.
 *
 *The caller-runs policy implements a form of throttling that neither discards tasks nor throws an
 * exception, but instead tries to slow down the flow of new tasks by pushing some of the work back
 * to the caller. It executes the newly submitted task not in a pool thread, but in the thread that
 * calls execute.
 */
public class FixedSizedBoundQueueCallerRuns {

  private final static int N_THREADS = Runtime.getRuntime().availableProcessors() * 2 - 1;
  private final static int CAPACITY = N_THREADS * 10;

  public static void main(String[] args) {
    var executor = new ThreadPoolExecutor(N_THREADS, N_THREADS,
        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(CAPACITY));
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
  }
}
