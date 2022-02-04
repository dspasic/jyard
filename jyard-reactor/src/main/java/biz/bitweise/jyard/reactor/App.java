package biz.bitweise.jyard.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class App {

  public static void main(String[] args) {
    subscribeHandleSubscriber();
    onErrorContinue();
    sampleSubscriber();
  }

  /**
   * The last signature of the subscribe method includes a Consumer<Subscription>.
   *
   * That variant requires you to do something with the Subscription (perform a request(long)
   * on it or cancel() it). Otherwise, the Flux hangs.
   *
   * This is approach is deprecated, because users tend to forget to request the subscription.
   * If the behavior is really needed, consider using subscribeWith(Subscriber). To be removed
   * in 3.5.
   */
  private static void subscribeHandleSubscriber() {
    System.out.println("-> subscribeHandleSubscriber");
    Flux.range(0, 10)
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("Finished!"),
            // When we subscribe we receive a Subscription. Signal that
            // we want up to 50 elements from the source (which will
            // actually emit 10 elements and complete).
            sub -> sub.request(50));
  }

  /**
   * Some error handling with onErrorContinue
   */
  private static void onErrorContinue() {
    Flux.range(1, 5)
        .map(i -> {
          if (i == 4) throw new RuntimeException("Oh Oh. Bad boy!");
          return i;
        })
        .onErrorContinue(
            (throwable, object) ->
                System.err.printf("This object %s has following issues: %s", object, throwable))
        .subscribe(System.out::println);
  }

  /**
   *  An Alternative to Lambdas: BaseSubscriber
   *
   *  There is an additional subscribe method that is more generic and takes a full-blown
   *  Subscriber rather than composing one out of lambdas. In order to help with writing such
   *  a Subscriber, we provide an extendable class called BaseSubscriber.
   */
  private static void sampleSubscriber() {
    SampleSubscriber<Integer> subscriber = new SampleSubscriber<>();
    Flux.range(0, 9).subscribe(subscriber);
  }

  private static class SampleSubscriber<T> extends BaseSubscriber<T> {
    @Override public void hookOnSubscribe(final Subscription sub) {
      System.out.println(this.getClass().getSimpleName() + " subscribed");
      request(1);
    }

    @Override public void hookOnNext(final T value) {
      System.out.printf("Value %s received%n", value);
      request(1);
    }

    @Override public void hookOnComplete() {
      System.out.println("Complete.");
    }

    @Override public void hookFinally(final SignalType st) {
      System.out.printf("Finally done %s%n", st);
    }

    @Override public void hookOnCancel() {
      System.out.println("Canceled, but why mate?!");
    }

    @Override public void hookOnError(final Throwable t) {
      System.err.printf("Don't care: %s", t);
    }
  }
}
