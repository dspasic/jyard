package biz.bitweise.jyard.io;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
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
      var c = in.read();
      if (c == -1) {
        return;
      }

      switch ((char) c) {
        case '*':
          var it = readInt();
          for (int i = 0; i < it; i++) {
            parse(in);
          }

          break;
      }
    }

    int readInt() throws IOException {
      return (int) readLong();
    }

    long readLong() throws IOException {
      long v = 0;

      var c = in.read();
      final boolean negative = c == '-';
      if (!negative) {
        v = c;
      }

      while (true) {
        c = in.read();
        if (c == '\r') {
          int eol = in.read();
          if (eol != '\n') {
            throw new EOFException();
          }
          break;
        } else {
          v = v * 10 + c;
        }
      }

      return v;
    }
  }
}
