package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

  public static AnimePutRequestBody createAnimePutRequestBody() {
    return AnimePutRequestBody
        .builder()
        .id(AnimeCreator.createAnimeToBeSaved().getId())
        .name(AnimeCreator.createValidUpdatedAnime().getName())
        .build();
  }

}
