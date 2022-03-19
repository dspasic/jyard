package biz.bitweise.jyard.reactor;

import java.time.Duration;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Some experiments described in the chapter {@link https://projectreactor.io/docs/core/release/reference/#schedulers}
 */
public class AppChapter45 {

  public static void main(String[] args) {
    try {
      runOnThreadOfSubscriber();
      useSingleThread();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static void runOnThreadOfSubscriber() throws InterruptedException {
    System.out.println("runOnThreadOfSubscriber");
    final Mono<String> mono = Mono.just("Hello ");

    Thread t = new Thread(() -> mono
        .map(msg -> msg + "thread ")
        .subscribe(v ->
            System.out.println(v + Thread.currentThread().getName())
        ), "Inner Thread");
    t.start();
    t.join();
  }

  private static void useSingleThread() {
    System.out.println("useSingleThread");
    final Flux<Long> tick = Flux.interval(Duration.ofMillis(300), Schedulers.newSingle("Test"));
    tick.subscribe(new BaseSubscriber<>() {
      @Override
      protected void hookOnSubscribe(Subscription subscription) {
        request(1);
      }

      @Override
      protected void hookOnNext(Long value) {
        System.out.println(value);
        cancel();
      }

      @Override
      protected void hookOnCancel() {
        System.out.println("Cancel the session");
      }

    });
  }
}
