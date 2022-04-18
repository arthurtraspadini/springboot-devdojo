package academy.devdojo.springboot2essentials.service;
import academy.devdojo.springboot2essentials.domain.Anime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {

    public List<Anime> listAll (){
        return List.of(new Anime(1L, "Boku No Hero"), new Anime(2L, "Bersek"));
    }
}
