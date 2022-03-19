package biz.bitweise.jyrad.reactor;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

  @Test
  void testWithVirtualTime() {
    StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
        .expectSubscription()
        .expectNoEvent(Duration.ofDays(1))
        .expectNext(0L)
        .verifyComplete();
  }

  private <T> Flux<T> appendBoomError(final Flux<T> source) {
    return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
  }
}
