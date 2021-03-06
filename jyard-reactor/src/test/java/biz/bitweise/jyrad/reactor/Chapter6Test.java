package biz.bitweise.jyrad.reactor;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class Chapter6Test {

  @Test
  void testAppendBoomError() {
    Flux<String> source = Flux.just("One", "Two");

    StepVerifier.create(appendBoomError(source))
        .expectNext("One")
        .expectNext("Two")
        .expectErrorMessage("boom")
        .verify();
  }

  private <T> Flux<T> appendBoomError(final Flux<T> source) {
    return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
  }

  @Test
  void testWithVirtualTime() {
    StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
        .expectSubscription()
        .expectNoEvent(Duration.ofDays(1))
        .expectNext(0L)
        .verifyComplete();
  }

  @Test
  void testWithInitialContext() {
    StepVerifier.create(
            Mono.just(1).map(i -> i + 10),
            StepVerifierOptions.create().withInitialContext(Context.of("ctx1", "val1")))
        .expectAccessibleContext()
        .contains("ctx1", "val1")
        .then()
        .expectNext(11)
        .verifyComplete();
  }

  @Test
  void testSplitPathIsUsed() {
    StepVerifier.create(
            processOrFallback(Mono.just("just a phrase with whitespaces"), Mono.just("FALLBACK")))
        .expectNext("just", "a", "phrase", "with", "whitespaces")
        .verifyComplete();
  }

  @Test
  void testEmptyPathIsUsed() {
    StepVerifier.create(processOrFallback(Mono.empty(), Mono.just("EMPTY")))
        .expectNext("EMPTY")
        .verifyComplete();
  }

  private Flux<String> processOrFallback(final Mono<String> source,
      final Publisher<String> fallback) {
    return source
        .flatMapMany(phrase -> Flux.fromArray(phrase.split("\\s+")))
        .switchIfEmpty(fallback);
  }
}
