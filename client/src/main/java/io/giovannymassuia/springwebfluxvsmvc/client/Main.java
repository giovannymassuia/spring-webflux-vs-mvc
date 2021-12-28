package io.giovannymassuia.springwebfluxvsmvc.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws URISyntaxException, ExecutionException, InterruptedException {
        CompletableFuture<Response> webflux = buildRequest(10, "http://localhost:8081", "webflux");
        CompletableFuture<Response> mvc = buildRequest(10, "http://localhost:8080", "mvc");

//        CompletableFuture.allOf(webflux, mvc).thenAccept(unused -> {
//            Response responseWebflux = webflux.join();
//            Response responseMvc = webflux.join();
//
//            System.out.println("responseWebflux: " + responseWebflux);
//            System.out.println("responseMvc: " + responseMvc);
//        }).get();

        Stream.of(webflux, mvc)
                .map(CompletableFuture::join)
                .forEach(System.out::println);
    }

    private static CompletableFuture<Response> buildRequest(int requestQty, String url, String type) throws URISyntaxException {
        LocalDateTime startTime = LocalDateTime.now();

        HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .followRedirects(Redirect.NORMAL)
                .build();

        List<CompletableFuture<String>> requests = IntStream
                .range(0, requestQty)
                .mapToObj(i -> {
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(new URI(url + "?requestId=" + type + "-" + i))
                                .GET()
                                .timeout(Duration.ofSeconds(60))
                                .build();

                        return client
                                .sendAsync(request, BodyHandlers.ofString())
                                .thenApply(HttpResponse::body);

                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return CompletableFuture.supplyAsync(() -> {
            List<String> responseMessages = requests.stream().map(CompletableFuture::join).collect(Collectors.toList());
            long resultCount = requests.stream().map(CompletableFuture::join).count();

            return new Response(url, Duration.between(startTime, LocalDateTime.now()), resultCount, responseMessages);
        });
    }

    private record Response(String source, Duration duration, long resultCount, List<String> messages) {

        @Override
        public String toString() {
            return """
                    Response {
                        source = %s,
                        duration = %s seconds,
                        resultCount = %s,
                        message [
                            %s
                        ]
                    }
                    """.formatted(source, duration.getSeconds(), resultCount, messages.stream().collect(Collectors.joining(",\n\t")));
        }
    }

}
