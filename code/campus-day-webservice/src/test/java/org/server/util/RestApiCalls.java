package org.server.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

public class RestApiCalls {
//    private static final WebClient.Builder webClientBuilder = WebClient.builder();
//    private static final WebClient webClient = webClientBuilder.baseUrl("").build();

    public static String sendGetRequest (String url, String authHeaderVal) {
        WebClient.Builder webClientBuilder = WebClient.builder();
        WebClient webClient = webClientBuilder.baseUrl("").defaultHeader(HttpHeaders.AUTHORIZATION, authHeaderVal).build();
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public static String sendPutRequest(String url, Object body, String authHeaderVal) {
        WebClient.Builder webClientBuilder = WebClient.builder();
        WebClient webClient = webClientBuilder.baseUrl("").defaultHeader(HttpHeaders.AUTHORIZATION, authHeaderVal).build();
        return webClient.put() // HTTP method is PUT
//                .uri("/endpoint/{id}", id) // URL with path variable
                .uri(url) // URL without path variable
                .bodyValue(body) // Request body (your object to send)
                .retrieve() // Start the request and retrieve the response
                .bodyToMono(String.class)
                .block(); // Convert the response body to String (or other types)
    }

    public static String sendPostRequest (String url, Object body, String authHeaderVal) {
        WebClient.Builder webClientBuilder = WebClient.builder();
        WebClient webClient = webClientBuilder.baseUrl("").defaultHeader(HttpHeaders.AUTHORIZATION, authHeaderVal).build();
        return webClient.post() // HTTP method is PUT
                .uri(url) // URL without path variable
                .bodyValue(body) // Request body (your object to send)
                .retrieve() // Start the request and retrieve the response
                .bodyToMono(String.class)
                .block(); // Convert the response body to String (or other types)
    }

    public static String sendDeleteRequest (String url, String authHeaderVal) {
        WebClient.Builder webClientBuilder = WebClient.builder();
        WebClient webClient = webClientBuilder.baseUrl("").defaultHeader(HttpHeaders.AUTHORIZATION, authHeaderVal).build();
        return webClient.delete()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}