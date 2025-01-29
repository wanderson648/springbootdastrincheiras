package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodeRepositoryTest {

    @InjectMocks
    private AnimeHardCodeRepository repository;
    
    @Mock
    private AnimeData animeData;

    private final List<Anime> animeList = new ArrayList<>();
    
    @BeforeEach
    void init() {
        var ufotable = Anime.builder().id(1L).name("Ufotable").build();
        var witSudio = Anime.builder().id(2L).name("Wit Studio").build();
        var studioGibli = Anime.builder().id(3L).name("Studio Gibli").build();
        animeList.addAll(List.of(ufotable, witSudio, studioGibli));
    }


    @Test
    @DisplayName("findAll returns a list with all animes")
    @Order(1)
    void findAll_ReturnAllAnimes_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findById returns a anime with given id")
    @Order(2)
    void findById_ReturnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var expectedAnime = animeList.getFirst();
        var anime = repository.findById(expectedAnime.getId());

        Assertions.assertThat(anime).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnEmptyList_WhenNameIsNull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findByName(null);

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns list with found object when name exists")
    @Order(4)
    void findByName_ReturnsFoundAnimeInList_WhenNameIsFound() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var expectedAnime = animeList.getFirst();

        var animes = repository.findByName(expectedAnime.getName());

        Assertions.assertThat(animes).contains(expectedAnime);
    }

    @Test
    @DisplayName("save creates a anime")
    @Order(5)
    void save_CreatesAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animeToSave = Anime.builder().id(99L).name("MAPPA").build();
        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
        var animeSaveOptional = repository.findById(animeToSave.getId());
        Assertions.assertThat(animeSaveOptional).isPresent().contains(animeToSave);
    }

    @Test
    @DisplayName("delete removes a anime")
    @Order(6)
    void delete_RemoveAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var  animeToDelete = animeList.getFirst();
        repository.delete(animeToDelete);

        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update update a anime")
    @Order(7)
    void update_UpdateAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToUpdate = this.animeList.getFirst();
        animeToUpdate.setName("Aniplex");

        repository.update(animeToUpdate);

        Assertions.assertThat(this.animeList).contains(animeToUpdate);

        var animeUpdateOptional = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeUpdateOptional).isPresent();
        Assertions.assertThat(animeUpdateOptional.get().getName()).isEqualTo(animeToUpdate.getName());
    }
    
}