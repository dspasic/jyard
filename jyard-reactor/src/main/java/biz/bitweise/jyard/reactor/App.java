package biz.bitweise.jyard.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class App {

  public static void main(String[] args) {
    simpleSubscriber();
    subscribeHandleSubscriber();
    onErrorContinue();
    sampleSubscriber();
    errorWithoutErrorHandler();
    cancelOnBackPressure();
  }

  /**
   * What happens if I call subscriber without an argument
   * <p>
   * Answer nothing. It will just execute the sequence and that's it.
   */
  private static void simpleSubscriber() {
    System.out.println("-> simpleSubscriber");
    var ints = Flux.range(1, 3);
    ints.subscribe();
  }

  /**
   * The last signature of the subscribe method includes a Consumer<Subscription>.
   * <p>
   * That variant requires you to do something with the Subscription (perform a request(long) on it
   * or cancel() it). Otherwise, the Flux hangs.
   * <p>
   * This is approach is deprecated, because users tend to forget to request the subscription. If
   * the behavior is really needed, consider using subscribeWith(Subscriber). To be removed in 3.5.
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
   * <p>
   * can this be used for dead letter approach?
   */
  private static void onErrorContinue() {
    System.out.println("-> onErrorContinue");
    Flux.range(1, 5)
        .map(i -> {
          if (i == 4) {
            throw new RuntimeException("Oh Oh. Bad boy!");
          }
          return i;
        })
        .onErrorContinue(
            (throwable, object) ->
                System.err.printf("This object %s has following issues: %s%n", object, throwable))
        .subscribe(System.out::println);
  }

  /**
   * What will happen if I have an error but no error handler?
   *
   * Answer: it will respond with the following message
   * [ERROR] (main) Operator called default onErrorDropped - reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.RuntimeException: We reached 11 and this means boom!
   */
  private static void errorWithoutErrorHandler() {
    System.out.println("-> errorWithoutErrorHandler");
    Flux.range(1, 100)
        .map(i -> {
          if (i > 10) {
            throw new RuntimeException("We reached 11 and this means boom!");
          }
          return i;
        })
        .subscribe(System.out::println);
  }

  /**
   * An Alternative to Lambdas: BaseSubscriber
   * <p>
   * There is an additional subscribe method that is more generic and takes a full-blown Subscriber
   * rather than composing one out of lambdas. In order to help with writing such a Subscriber, we
   * provide an extendable class called BaseSubscriber.
   *
   * @link https://projectreactor.io/docs/core/release/reference/#_an_alternative_to_lambdas_basesubscriber
   */
  private static void sampleSubscriber() {
    System.out.println("-> sampleSubscriber");
    SampleSubscriber<Integer> subscriber = new SampleSubscriber<>();
    Flux.range(0, 9).subscribe(subscriber);
  }

  /**
   * Dealing with back pressure.
   *
   * Cancelling after received one message
   */
  private static void cancelOnBackPressure() {
    System.out.println("-> cancelOnBackpressure");
    Flux.range(1, 10)
        .doOnRequest(r -> System.out.println("Processing following request: " + r))
        .subscribe(new BaseSubscriber<Integer>() {
          @Override
          protected void hookOnSubscribe(Subscription subscription) {
            System.out.println("Subscribed and requesting one");
            request(1);
          }

          @Override
          protected void hookOnNext(Integer value) {
            System.out.println("Cancelling after having received: " + value);
            cancel();
          }
        });
  }

  private static class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    public void hookOnSubscribe(final Subscription sub) {
      System.out.println(this.getClass().getSimpleName() + " subscribed");
       request(1);
    }

    @Override
    public void hookOnNext(final T value) {
      System.out.printf("Value %s received%n", value);
      request(1);
    }

    @Override
    public void hookOnComplete() {
      System.out.println("Complete.");
    }

    @Override
    public void hookFinally(final SignalType st) {
      System.out.printf("Finally done %s%n", st);
    }

    @Override
    public void hookOnCancel() {
      System.out.println("Canceled, but why mate?!");
    }

    @Override
    public void hookOnError(final Throwable t) {
      System.err.printf("Don't care: %s", t);
    }
  }
}
