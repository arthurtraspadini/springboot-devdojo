package academy.devdojo.springboot2essentials.repository;

import academy.devdojo.springboot2essentials.domain.Anime;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for Anime repository")
@Log4j2
class AnimeRepositoryTest {

  @Autowired
  private AnimeRepository animeRepository;

  private Anime createAnime() {
    return Anime.builder().name("Hajime no Ippo").build();
  }

  @Test
  @DisplayName("Save persists anime when successful")
  void save_PersistAnime_WhenSuccessful() {
    Anime animeToBeSaved = createAnime();
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    Assertions.assertThat(savedAnime).isNotNull();
    Assertions.assertThat(savedAnime.getId()).isNotNull();
    Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());

  }

  @Test
  @DisplayName("Save updates anime when successful")
  void save_UpdateAnime_WhenSuccessful() {
    Anime animeToBeSaved = createAnime();
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    savedAnime.setName("Overlord");
    Anime animeUpdated = this.animeRepository.save(savedAnime);

    Assertions.assertThat(animeUpdated).isNotNull();
    Assertions.assertThat(animeUpdated.getId()).isNotNull();
    Assertions.assertThat(animeUpdated.getName()).isEqualTo(savedAnime.getName());

  }

  @Test
  @DisplayName("Delete removes anime when successful")
  void delete_RemovesAnime_WhenSuccessful() {
    Anime animeToBeSaved = createAnime();
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    this.animeRepository.delete(savedAnime);

    Optional<Anime> deletedAnime = this.animeRepository.findById(savedAnime.getId());

    Assertions.assertThat(deletedAnime).isEmpty();

  }

  @Test
  @DisplayName("Find by name returns list of anime when successful")
  void findByName_ReturnsListOfAnime_WhenSuccessful() {
    Anime animeToBeSaved = createAnime();
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    List<Anime> animes = this.animeRepository.findByName(savedAnime.getName());

    Assertions.assertThat(animes)
        .isNotEmpty()
        .contains(savedAnime);
  }

  @Test
  @DisplayName("Find by name returns empty list when no anime is found")
  void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

    List<Anime> animes = this.animeRepository.findByName("xaxa");

    Assertions.assertThat(animes).isEmpty();

  }

  @Test
  @DisplayName("Save throw ConstraintViolationException when anime name is empty")
  void save_ThrowsConstraintViolationException_whenNameIsEmpty() {

    Anime anime = new Anime();

//    Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//        .isInstanceOf(ConstraintViolationException.class);

    Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(()-> this.animeRepository.save(anime))
        .withMessageContaining("The anime name cannot be empty");
  }

}