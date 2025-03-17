package biz.bitweise.jyard.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/** Playing around with the HTTP Client */
public class HttpClientApp {

  public static void main(String[] args) throws IOException, InterruptedException {

    final var martinFolwerFeed = "https://martinfowler.com/feed.atom";

    final HttpResponse<String> response;
    try (HttpClient client = HttpClient.newHttpClient()) {
      final HttpRequest request =
          HttpRequest.newBuilder().uri(URI.create(martinFolwerFeed)).build();
      response = client.send(request, BodyHandlers.ofString());
    }

    System.out.println(response.body());
  }
}
