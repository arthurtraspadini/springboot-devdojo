package academy.devdojo.springboot2essentials.controller;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.service.AnimeService;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import java.util.List;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

  @Mock
  AnimeService animeServiceMock;
  @InjectMocks
  private AnimeController animeController;

  @BeforeEach
  void setUp () {
    PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
    BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
        .thenReturn(animePage);
  }

  @Test
  @DisplayName("List returns list of anime inside page object when successful")
  void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {

    String expectedName = AnimeCreator.createValidAnime().getName();
    Page<Anime> animePage = animeController.list(null).getBody();

    Assertions.assertThat(animePage).isNotNull();

    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);

    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

  }

}