package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class VragenController {
    private static final Logger log = LoggerFactory.getLogger(BigdataNieuwApplication.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @PostMapping("/vraag1")
    public String vraag1Submit(Model model) {
        log.info("we gaan shit ophalen");
        ActeurInFilms result = jdbcTemplate.queryForObject(
                "SELECT name, COUNT(name) AS aantal_films FROM actortitles GROUP BY name ORDER BY aantal_films DESC LIMIT 1;", new ActeurRowMapper());
        log.info("klaar");

        model.addAttribute("result", result);
        return "vraag1";
    }

    @PostMapping("/vraag2")
    public String vraag2Submit(Model model) {
        log.info("we gaan shit ophalen");
        filmRating result = jdbcTemplate.queryForObject(
                "SELECT runtimes.title, runtimes.tconst, runtimes.minutes, runtimes.type, ratings.rating FROM runtimes inner join ratings on ratings.tconst = runtimes.tconst WHERE runtimes.minutes IS NOT NULL AND type='movie' AND ratings.rating > 8.0 ORDER BY runtimes.minutes DESC LIMIT 1;", new FilmRowMapper());
        log.info("klaar");

        model.addAttribute("result", result);
        return "vraag2";
    }

    @PostMapping("/vraag3")
    public String vraag3Submit(Model model) {
        log.info("we gaan shit ophalen");
        List<FilmsPerJaar> result = jdbcTemplate.query(
                "SELECT year, COUNT(year) AS hoeveelheid FROM locations WHERE country='USA' AND year BETWEEN '2000' AND '2015' GROUP BY year ORDER BY hoeveelheid ASC LIMIT 16;", new FilmsPerJaarRowMapper());
        log.info("klaar");

        model.addAttribute("result", result);
        return "vraag3";
    }

    @PostMapping("/vraag4")
    public String vraag4Submit(Model model) {
        log.info("we gaan shit ophalen");
        AantalFilmsInLand result = jdbcTemplate.queryForObject(
                "SELECT country, COUNT(country) AS aantal_films FROM locations WHERE type='movie' GROUP BY country ORDER BY aantal_films DESC LIMIT 1;", new AantalFilmsRowMapper());
        log.info("klaar");

        model.addAttribute("result", result);
        return "vraag4";
    }

    @PostMapping("/vraag5")
    public String vraag5Submit(Model model) {
        log.info("we gaan shit ophalen");
        HoogsteKosten result = jdbcTemplate.queryForObject(
                "SELECT movies.tconst, title, budget, rating, votes\n" +
                        "FROM movies\n" +
                        "JOIN business on movies.tconst = business.tconst\n" +
                        "JOIN ratings on movies.tconst = ratings.tconst\n" +
                        "WHERE business.tconst IN(\n" +
                        "SELECT business.tconst \n" +
                        "From movies\n" +
                        "Join business on movies.tconst = business.tconst\n" +
                        "JOIN ratings on movies.tconst = ratings.tconst\n" +
                        "where type = 'movie' \n" +
                        "Order by budget DESC\n" +
                        "LIMIT 1)Order by rating ASC;", new HoogteKostenRowMapper());
        log.info("klaar");

        model.addAttribute("result", result);
        return "vraag5";
    }
}
