package biz.bitweise.jyard.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * The Java Language and JDK Class Library have two distinct, yet connected, ways to group elements:
 * arrays and Collections. There are pros and cons for using either one, so both are prevalent in
 * real programs. To aid conversion between the two, there are standard methods to make a reference
 * array appear as a Collection (e.g. Arrays.asList), and to copy from Collection to array (e.g.
 * several Collection.toArray methods). In this post, we will try to answer a controversial
 * question: which toArray conversion pattern is faster?
 *
 * @link https://shipilev.net/blog/2016/arrays-wisdom-ancients/
 */

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgsAppend = {"-XX:+UseParallelGC", "-Xms1g", "-Xmx1g"})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class ArrayOfWisdom {

  @Param({"0", "1", "10", "100", "1000"})
  int size;

  @Param({"arraylist", "hashset"})
  String type;

  Collection<Foo> coll;

  public static void main(String[] args) throws RunnerException {
    new Runner(
        new OptionsBuilder().include(ArrayOfWisdom.class.getSimpleName()).build()).run();
  }

  @Setup
  public void setup() {
    if (type.equals("arraylist")) {
      coll = new ArrayList<Foo>();
    } else if (type.equals("hashset")) {
      coll = new HashSet<Foo>();
    } else {
      throw new IllegalStateException();
    }
    for (int i = 0; i < size; i++) {
      coll.add(new Foo(i));
    }
  }

  @Benchmark
  public Object[] simple() {
    return coll.toArray();
  }

  @Benchmark
  public Foo[] zero() {
    return coll.toArray(new Foo[0]);
  }

  @Benchmark
  public Foo[] sized() {
    return coll.toArray(new Foo[coll.size()]);
  }

  public static class Foo {

    private final int i;

    public Foo(int i) {
      this.i = i;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Foo foo = (Foo) o;
      return i == foo.i;
    }

    @Override
    public int hashCode() {
      return i;
    }
  }

}
