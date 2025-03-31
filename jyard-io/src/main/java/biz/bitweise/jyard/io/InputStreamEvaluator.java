package biz.bitweise.jyard.io;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class InputStreamEvaluator {

  public InputStream createStream() {
    final String redisCommand = "*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n";
    // System.out.println(Arrays.toString(redisCommand.getBytes()));
    return new ByteArrayInputStream(redisCommand.getBytes());
  }

  public static void main(String[] args) throws IOException {
    final var in = new InputStreamEvaluator();

    var parsedReq = new RedisParser().parse(in.createStream());
    System.out.println(new Translator().translate(parsedReq));
  }

  static class Translator {

    public String translate(Object req) {
      if (req instanceof Object[] r) {
        if (r.length == 2 && r[0] instanceof String c) {
          var cmd = c.toUpperCase();
          if (cmd.equals("ECHO")) {
            if (r[1] instanceof String arg) {
              return arg;
            } else {
              return "-ERROR One argument must be given for ECHO";
            }
          }
        }
      }
      return "-ERROR Unknown command";
    }
  }

  static class RedisParser {

    private InputStream in;

    public Object parse(InputStream in) throws IOException {
      this.in = Objects.requireNonNull(in, "in can not be null");
      var c = in.read();
      if (c == -1) {
        return null;
      }

      switch ((char) c) {
        case '*' -> {
          // Read N elements from the stream
          // See: https://redis.io/docs/latest/develop/reference/protocol-spec/#arrays
          var it = readInt();
          Object[] l = new Object[it];
          for (int i = 0; i < it; i++) {
            l[i] = parse(in);
          }
          return l;
        }
        case '$' -> {
          var length = readInt();
          return readString(length);
        }
      }
      return null;
    }

    int readInt() throws IOException {
      return (int) readLong();
    }

    long readLong() throws IOException {
      long v = 0;

      var c = (char) read();
      final boolean negative = c == '-';
      if (!negative) {
        v = c - '0';
      }

      while (true) {
        c = (char) read();
        if (c == '\r') {
          int eol = read();
          if (eol != '\n') {
            throw new ParserException("Expected line feed after carriage return. Found: " + eol);
          }
          break;
        } else {
          v = v * 10 + (c - '0');
        }
      }

      return negative ? -v : v;
    }

    public String readString(int length) throws IOException {
      // Read the N characters form the stream
      // See: https://redis.io/docs/latest/develop/reference/protocol-spec/#bulk-strings
      byte[] bytes = new byte[length];
      var nob = in.read(bytes);
      if (nob != length) {
        throw new ParserException("Expected %d bytes to read, but %d bytes were available");
      }
      var eol = (char) read();
      if (eol == '\r') {
        eol = (char) read();
        if (eol != '\n') {
          throw new ParserException("Expected line feed after carriage return. Found: " + eol);
        }
      } else {
        throw new ParserException("Expected carriage return. Found: " + eol);
      }
      return new String(bytes);
    }

    private int read() throws IOException {
      final var n = in.read();
      if (n == -1) {
        throw new EOFException();
      }
      return n;
    }

    public static class ParserException extends RuntimeException {
      public ParserException(String message) {
        super(message);
      }
    }
  }
}
