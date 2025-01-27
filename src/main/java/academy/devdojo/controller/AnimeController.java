package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

    private final static AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAllAnimes(@RequestParam(required = false) String name) {
        log.debug("Request received to list all animes, param name  '{}'", name);

        var animes = Anime.getAnimes();
        var animgeGetResponseList = MAPPER.toAnimeGetResponse(animes);
        if(name == null) return ResponseEntity.ok().body(animgeGetResponseList);

        var response = animgeGetResponseList.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);

        var animeGetResponse =  Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .map(MAPPER::toAnimeGetResponse)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        return ResponseEntity.ok().body(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.debug("Request to save anime: {}", request);

        var anime = MAPPER.toAnime(request);
        Anime.getAnimes().add(anime);
        var response = MAPPER.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete Anime by id: {}", id);

        var animeToDelete = Anime.getAnimes().stream()
                .filter(Anime -> Anime.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        Anime.getAnimes().remove(animeToDelete);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update Anime", request);

        var animeToUpdate = Anime.getAnimes().stream()
                .filter(Anime -> Anime.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        var animeUpdated = MAPPER.toAnime(request);
        Anime.getAnimes().remove(animeToUpdate);
        Anime.getAnimes().add(animeUpdated);

        return ResponseEntity.noContent().build();
    }


}
