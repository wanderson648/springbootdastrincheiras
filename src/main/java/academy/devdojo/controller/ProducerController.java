package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

    private final static ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> listAllProducers(@RequestParam(required = false) String name) {
        log.debug("Request received to list all Producers, param name  '{}'", name);

        var producers = Producer.getProducers();
        var producergeGetResponseList = MAPPER.toProducerGetResponse(producers);
        if(name == null) return ResponseEntity.ok().body(producergeGetResponseList);

        var response = producergeGetResponseList.stream()
                .filter(Producer -> Producer.getName().equalsIgnoreCase(name)).toList();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find Producer by id: {}", id);

        var ProducerGetResponse =  Producer.getProducers().stream()
                .filter(Producer -> Producer.getId().equals(id))
                .findFirst()
                .map(MAPPER::toProducerGetResponse)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        return ResponseEntity.ok().body(ProducerGetResponse);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var producer = MAPPER.toProducer(producerPostRequest);
        var response = MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);

       var producerToDelete = Producer.getProducers().stream()
                .filter(Producer -> Producer.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

       Producer.getProducers().remove(producerToDelete);

       return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update Producer", request);

        var producerToRemove = Producer.getProducers().stream()
                .filter(Producer -> Producer.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        var producerUpdated = MAPPER.toProducer(request, producerToRemove.getCreatedAt());

        Producer.getProducers().remove(producerToRemove);
        Producer.getProducers().add(producerUpdated);

        return ResponseEntity.noContent().build();
    }

}
