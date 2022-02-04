package biz.bitweise.jyard.reactor;

import reactor.core.publisher.Flux;

public class App {

  public static void main(String[] args) {
    subscribeHandleSubscriber();
    onErrorContinue();
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
}
