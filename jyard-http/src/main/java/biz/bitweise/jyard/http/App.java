package biz.bitweise.jyard.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * Playing arround with the HTTP Client
 */
public class App {

  public static void main(String[] args) throws IOException, InterruptedException {

    final var martinFolwerFeed = "https://martinfowler.com/feed.atom";

    final HttpClient client = HttpClient.newHttpClient();
    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(martinFolwerFeed)).build();
    final HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

    System.out.println(response.body());
  }
}
