package academy.devdojo.controller;

import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeMapper mapper;
    private final AnimeService service;


    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAllAnimes(@RequestParam(required = false) String name) {
        log.debug("Request received to list all animes, param name  '{}'", name);

        var animes = service.findAll(name);
        var animesGetResponse = mapper.toAnimeGetResponse(animes);

        return ResponseEntity.ok().body(animesGetResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);

        var anime = service.findByIdOrThrowNorFound(id);

        var animeGetResponse = mapper.toAnimeGetResponse(anime);

        return ResponseEntity.ok().body(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.debug("Request to save anime: {}", request);

        var anime = mapper.toAnime(request);
        var animeSaved = service.save(anime);

        var animeGetResponse = mapper.toAnimePostResponse(animeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(animeGetResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete Anime by id: {}", id);

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update Anime", request);

        var animeToUpdate = mapper.toAnime(request);
        service.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }


}
