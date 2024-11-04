package com.thunderbase.tg.client.springbootstarter;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// todo - make as spring-boot-starter?
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationSenderClient {

    private final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private final ObjectMapper mapper;

    @Value("${notification.url}")
    private String errorNotificationUrl;

    @SneakyThrows
    public void sendError(NotificationError error) {
        var reqBody = mapper.writeValueAsString(error);

        var req = HttpRequest.newBuilder()
                .uri(URI.create(errorNotificationUrl))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .build();

        var resp = HTTP_CLIENT.sendAsync(req, BodyHandlers.ofString());

        resp.thenAccept(response ->
            log.error("Error notification sent successfully with error : {}", error)
        )
        .exceptionally(ex -> {
            log.error("Can't send error notification", ex);
            return null;
        });
    }

    public record NotificationError(String title, String msg, Object details) {

    }

}
