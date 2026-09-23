package dev.saldatori.orders_service.client;

import dev.saldatori.orders_service.exception.EventNotFoundException;
import dev.saldatori.orders_service.exception.EventServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class EventClient {
    private static final Logger log = LoggerFactory.getLogger(EventClient.class);

    private final RestClient restClient;

    public EventClient(RestClient.Builder restClientBuilder, @Value("${events.service.url}") String eventsServiceUrl) {
        this.restClient = restClientBuilder.baseUrl(eventsServiceUrl).build();
    }

    public RemoteEvent getEvent(Long eventId) {
        try {
            return restClient.get()
                    .uri("/events/{id}", eventId)
                    .retrieve()
                    .body(RemoteEvent.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new EventNotFoundException(eventId);
        } catch (ResourceAccessException ex) {
            log.warn("events-service unreachable while fetching event {}", eventId, ex);
            throw new EventServiceUnavailableException(ex);
        }
    }
}
