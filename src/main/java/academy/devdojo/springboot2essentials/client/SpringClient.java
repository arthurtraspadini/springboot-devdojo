package academy.devdojo.springboot2essentials.client;

import academy.devdojo.springboot2essentials.domain.Anime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {

  public static void main(String[] args) {

    ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/7", Anime.class);
    log.info(entity);

    Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/7", Anime.class);
    log.info(object);

    Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
    log.info(Arrays.toString(animes));

    //@formatter:off
    ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {});
    //@formatter:on
    log.info(exchange);

//    Anime kingdom = Anime.builder().name("kingdom").build();
//    Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes",
//        kingdom,
//        Anime.class);
//    log.info(kingdomSaved);

    Anime kingdomHeart =  Anime.builder().name("kingdom hearts").build();
    ResponseEntity<Anime> kingdomHeartsSaved = new RestTemplate().exchange("http://localhost:8080/animes",
        HttpMethod.POST,
        new HttpEntity<>(kingdomHeart, createJsonHeader()),
        Anime.class);
    log.info(kingdomHeartsSaved);

    Anime animeToBeUpdated = kingdomHeartsSaved.getBody();
    animeToBeUpdated.setName("pokemon");

    ResponseEntity<Void> kingdomHearsUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
        HttpMethod.PUT,
        new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
        Void.class);
    log.info(kingdomHearsUpdated);

    ResponseEntity<Void> kingdomHearsToBeDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
        HttpMethod.DELETE,
        null,
        Void.class,
        animeToBeUpdated.getId());
    log.info(kingdomHearsToBeDeleted);


  }

  private static HttpHeaders createJsonHeader() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return httpHeaders;
  }


}
