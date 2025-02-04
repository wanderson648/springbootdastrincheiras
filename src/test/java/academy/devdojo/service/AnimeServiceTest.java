package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeHardCodeRepository repository;

    private List<Anime> animesList = new ArrayList<>();

    @BeforeEach
    void init() {
        var ufotable = Anime.builder().id(1L).name("Ufotable").build();
        var witSudio = Anime.builder().id(2L).name("Wit Studio").build();
        var studioGibli = Anime.builder().id(3L).name("Studio Gibli").build();
        animesList.addAll(List.of(ufotable, witSudio, studioGibli));
    }


    @Test
    @DisplayName("findAll returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animesList);

        var animes = service.findAll(null);
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findAll returns a list with found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundAnimes_WhenNameIsFound() {
        var anime = animesList.getFirst();
        var expectedAnimesFound = Collections.singletonList(anime);

        BDDMockito.when(repository.findByName(anime.getName())).thenReturn(expectedAnimesFound);

        var animesFound = service.findAll(anime.getName());
        Assertions.assertThat(animesFound).isNotNull().hasSameElementsAs(expectedAnimesFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not found";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var animes = service.findAll(name);
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when anime is not found")
    @Order(5)
    void findById_ThrowsReponseException_WhenAnimeIsNotFound() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNorFound(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenNameSuccessful() {
        var animeToSave = Anime.builder().id(99L).name("MAPPA").build();

        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        var animeProducer = service.save(animeToSave);

        Assertions.assertThat(animeProducer).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a anime")
    @Order(9)
    void update_UpdatesAnime_WhenNameSuccessful() {
        var animeToUpdate = animesList.getFirst();
        animeToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.doNothing().when(repository).update(animeToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToUpdate = animesList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }

}