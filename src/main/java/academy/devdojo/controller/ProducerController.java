package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Producer> save(@RequestBody Producer producer, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);
        producer.setId(ThreadLocalRandom.current().nextLong(100_000));
        Producer.getProducers().add(producer);
        var responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", "My key");

        return ResponseEntity.status(HttpStatus.CREATED).body(producer);
    }


}
