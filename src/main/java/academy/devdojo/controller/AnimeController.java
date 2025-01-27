package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    @GetMapping
    public ResponseEntity<List<Anime>> listAllAnimes(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        if(name == null) return ResponseEntity.ok().body(animes);

        return ResponseEntity.ok().body(animes.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {

        return ResponseEntity.ok().body(Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(100_000));
        Anime.getAnimes().add(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(anime);
    }


}
