package academy.devdojo.domain;

import java.util.ArrayList;
import java.util.List;

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

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}
