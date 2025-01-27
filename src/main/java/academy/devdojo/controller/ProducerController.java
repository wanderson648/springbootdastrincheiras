package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

    @GetMapping
    public ResponseEntity<List<Producer>> listAllAnimes(@RequestParam(required = false) String name) {
        var producers = Producer.getProducers();
        if (name == null) return ResponseEntity.ok().body(producers);

        return ResponseEntity.ok().body(producers.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producer> findById(@PathVariable Long id) {

        return ResponseEntity.ok().body(Producer.getProducers().stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var producer = Producer.builder()
                .id(ThreadLocalRandom.current().nextLong(100_000))
                .name(producerPostRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Producer.getProducers().add(producer);

        var producerResponse = ProducerGetResponse.builder()
                .id(producer.getId())
                .name(producer.getName())
                .createdAt(producer.getCreatedAt()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(producerResponse);
    }


}
