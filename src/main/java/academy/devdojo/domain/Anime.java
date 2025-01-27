package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Anime {
    private Long id;
    private String name;

    private static List<Anime> animes = new ArrayList<>();

    static {
        var ninjakamui = new Anime(1L, "Ninja Kamui");
        var kaijuu = new Anime(2L, "Kaijuu-8gou");
        var kimetsuNoYaiba = new Anime(3L, "Kimetsu No Yaiba");

        animes.addAll(List.of(ninjakamui, kaijuu, kimetsuNoYaiba));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}
