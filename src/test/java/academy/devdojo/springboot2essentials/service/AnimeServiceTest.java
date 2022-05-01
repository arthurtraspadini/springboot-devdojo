package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.exception.BadRequestException;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.util.AnimePutRequestBodyCreator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

  @Mock
  AnimeRepository animeRepositoryMock;
  @InjectMocks
  private AnimeService animeService;

  @BeforeEach
  void setUp() {
    PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
    BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(animePage);

    BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
        .thenReturn(AnimeCreator.createValidAnime());

    BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
  }

  @Test
  @DisplayName("listAll returns list of anime inside page object when successful")
  void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {

    String expectedName = AnimeCreator.createValidAnime().getName();
    Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));

    Assertions.assertThat(animePage).isNotNull();

    Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);

    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

  }

  @Test
  @DisplayName("listAllNonPageable returns list of animes when successful")
  void listAllNonPageable_ReturnsListOfAnimesWhenSuccessful() {

    String expectedName = AnimeCreator.createValidAnime().getName();

    List<Anime> animes = animeService.listAllNonPageable();

    Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

  }

  @Test
  @DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
  void findByIdOrThrowBadRequestException_ReturnsAnimeWhenSuccessful() {

    Long expectedId = AnimeCreator.createValidAnime().getId();

    Anime anime = animeService.findByIdOrThrowBadRequestException(1);

    Assertions.assertThat(anime).isNotNull();

    Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);

  }

  @Test
  @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException anime is not found")
  void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {

    BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

    Assertions.assertThatExceptionOfType(BadRequestException.class)
        .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1));


  }


  @Test
  @DisplayName("findByName returns list of anime when successful")
  void findByName_ReturnsListOfAnimesWhenSuccessful() {

    String expectedName = AnimeCreator.createValidAnime().getName();

    List<Anime> animes = animeService.findByName("anime");

    Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

  }

  @Test
  @DisplayName("findByName returns an empty list of anime when anime is not found")
  void findByName_ReturnsAnEmptyListOfAnimesWhenAnimeIsNotFound() {

    BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

    List<Anime> animes = animeService.findByName("anime");

    Assertions.assertThat(animes).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("save returns anime when successful")
  void save_ReturnsAnimeWhenSuccessful() {

    Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

    Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

  }

  @Test
  @DisplayName("replace update anime when successful")
  void replace_UpdateAnimeWhenSuccessful() {

    Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

  }

  @Test
  @DisplayName("delete removes anime when successful")
  void delete_RemovesAnimeWhenSuccessful() {

    Assertions.assertThatCode(() -> animeService.delete(1)).doesNotThrowAnyException();

  }


}