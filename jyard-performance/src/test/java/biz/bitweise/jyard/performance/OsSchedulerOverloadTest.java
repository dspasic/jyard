package biz.bitweise.jyard.performance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OsSchedulerOverloadTest {

  @Test
  void givenDefault_whenExecuteOverload_thenMeasureElapsedTime() {
    new OsSchedulerOverload().overloadOsScheduler();
  }
}