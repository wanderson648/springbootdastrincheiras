package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodeRepository repository;

    public List<Anime> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Anime findByIdOrThrowNorFound(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Anime save(Anime Anime) {
        return repository.save(Anime);
    }

    public void delete(Long id) {
        var Anime = findByIdOrThrowNorFound(id);
        repository.delete(Anime);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate.getId());
        repository.update(animeToUpdate);
    }

    public void assertAnimeExists(Long id) {
        findByIdOrThrowNorFound(id);
    }

}
