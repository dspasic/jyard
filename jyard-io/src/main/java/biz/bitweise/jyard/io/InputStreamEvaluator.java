package biz.bitweise.jyard.io;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class InputStreamEvaluator {

  public byte[] readStream() {
    final String redisCommand = "*2\\r\\n$4\\r\\nECHO\\r\\n$3\\r\\nhey\\r\\n";
    final InputStream in = new ByteArrayInputStream(redisCommand.getBytes());
    System.out.println(Arrays.toString(redisCommand.getBytes()));
    return new byte[0];
  }

  public static void main(String[] args) {
    final var in = new InputStreamEvaluator();
    in.readStream();
  }

  static class RedisParser {

    private final InputStream in;

    public RedisParser(InputStream in) {
      this.in = Objects.requireNonNull(in);
    }

    public void parse(InputStream in) throws IOException {

    }
  }
}
