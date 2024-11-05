package com.thunderbase.tg.client.springbootstarter;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class TgBotNotifierClient {

    private final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /** Request body wrapper for current user request.
     * <p>
     * This class use {@link org.springframework.web.context.annotation.RequestScope  @RequestScope}
     * and inject request body in {@link RequestBodyInjectorControllerAdvice  RequestBodyInjectorAdvice} class.
     * </p> */
    @Getter
    private final RequestBodyHolder requestBodyHolder;

    private final ObjectMapper mapper;

    @Value("${tg-bot-notifier-server.url}")
    private String errorNotificationUrl;

    @SneakyThrows
    public void send(TgBotNotification notification) {
        var reqBody = mapper.writeValueAsString(notification);

        var req = HttpRequest.newBuilder()
                .uri(URI.create(errorNotificationUrl))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .build();

        HTTP_CLIENT.sendAsync(req, BodyHandlers.ofString())
                .thenAccept(response ->
                        log.error("Notification sent successfully with : {}", notification)
                )
                .exceptionally(ex -> {
                    log.error("Can't send notification", ex);
                    return null;
                });
    }

}