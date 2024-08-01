package biz.bitweise.jyard.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.logging.Logger;

/**
 * WriteIntoFileApp about IO
 *
 * <p>This class writes into file using the nio with {@code ByteBuffer} and {@code FileChannel}.
 *
 * <ul>
 *   <li>Efficient Buffering: ByteBuffer acts as a buffer, reducing the number of system calls for
 *       writing data, making it more efficient, especially for large files.
 *   <li>Channel-based I/O: FileChannel provides a direct connection to the file, enabling faster
 *       and more direct data transfer.
 *   <li>Non-blocking Operations (Optional): FileChannel supports non-blocking operations, which can
 *       be useful in asynchronous or multithreaded scenarios.
 *   <li>Clear and Concise: This approach provides a clear and structured way to write to files
 *       using modern Java NIO features.
 * </ul>
 *
 * @see ByteBuffer
 * @see FileChannel
 */
public class WriteIntoFileApp {

  private static final Logger log = Logger.getLogger(WriteIntoFileApp.class.getCanonicalName());

  public static void main(String[] args) throws IOException {
    final Path file = Path.of("%s.txt".formatted(WriteIntoFileApp.class.getSimpleName()));
    log.info("I have a file %s".formatted(file.toAbsolutePath()));

    if (Files.notExists(file)) {
      log.info("The file does not exist. Initiating creation.");
      try {
        Files.createFile(
            file,
            PosixFilePermissions.asFileAttribute(
                Set.of(
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.OTHERS_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_READ)));
        log.info("The file has been created.");
      } catch (Exception e) {
        log.severe("An error occurred while creating the file.");
      }
    }

    try (final var fc =
        FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
      final var s = "X1".repeat(1000).getBytes(StandardCharsets.UTF_8);
      ByteBuffer bb = ByteBuffer.wrap(s);

      while (bb.hasRemaining()) {
        fc.write(bb);
      }
    }

    Files.deleteIfExists(file);
  }
}
