package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller
public class VragenController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.application.name}")
    String appName;

    @Value("${spring.thymeleaf.prefix}")
    String rFilePath;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    //SQL query door Jesse
    @PostMapping("/vraag1")
    public String vraag1Submit(Model model) {
        ActeurInFilms result = jdbcTemplate.queryForObject("SELECT name, COUNT(name) AS aantal_films FROM actortitles INNER JOIN actorinfo ON actortitles.nconst = actorinfo.nconst GROUP BY name ORDER BY aantal_films DESC LIMIT 1;", new ActeurRowMapper());

        model.addAttribute("result", result);
        return "vraag1";
    }

    //SQL query door Jesse
    @PostMapping("/vraag2")
    public String vraag2Submit(Model model) {
        filmRating result = jdbcTemplate.queryForObject(
                "SELECT title, runtimes.tconst, minutes, type, rating FROM runtimes inner join ratings on ratings.tconst = runtimes.tconst INNER JOIN movies ON runtimes.tconst = movies.tconst WHERE runtimes.minutes IS NOT NULL AND type='movie' AND ratings.rating > 8.0 ORDER BY runtimes.minutes DESC LIMIT 1;", new FilmRowMapper());

        model.addAttribute("result", result);
        return "vraag2";
    }

    //SQL query door Jarno
    @PostMapping("/vraag3")
    public String vraag3Submit(Model model) {
        List<FilmsPerJaar> result = jdbcTemplate.query(
                "SELECT year, COUNT(year) AS hoeveelheid FROM locations INNER JOIN movies ON locations.tconst = movies.tconst WHERE country='USA' OR country = ' USA' AND movies.type = 'movie' GROUP BY year ORDER BY hoeveelheid DESC LIMIT 16;", new FilmsPerJaarRowMapper());

        model.addAttribute("result", result);
        return "vraag3";
    }

    //SQL query door Jarno
    @PostMapping("/vraag4")
    public String vraag4Submit(Model model) {
        AantalFilmsInLand result = jdbcTemplate.queryForObject(
                "SELECT country, COUNT(country) AS aantal_films FROM locations INNER JOIN movies ON locations.tconst = movies.tconst WHERE type='movie' GROUP BY country ORDER BY aantal_films DESC LIMIT 1;", new AantalFilmsRowMapper());

        model.addAttribute("result", result);
        return "vraag4";
    }

    //SQL query door Koen
    @PostMapping("/vraag5")
    public String vraag5Submit(Model model) {
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
                        "AND rating > 8\n" +
                        "Order by budget DESC\n" +
                        "LIMIT 1)Order by rating ASC;", new HoogteKostenRowMapper());

        model.addAttribute("result", result);
        return "vraag5";
    }

    //SQL query door Sybrand
    @PostMapping("/vraag6")
    public String vraag6Submit(Model model) {
        MeesteSlechteFilms result = jdbcTemplate.queryForObject(
                "SELECT actorinfo.name, COUNT(actorinfo.nconst) AS hoeveelheid_films\n" +
                        "FROM actortitles\n" +
                        "INNER JOIN ratings\n" +
                        "ON ratings.tconst = actortitles.tconst\n" +
                        "INNER JOIN actorinfo \n" +
                        "ON actorinfo.nconst = actortitles.nconst\n" +
                        "INNER JOIN runtimes \n" +
                        "ON actortitles.tconst = runtimes.tconst\n" +
                        "INNER JOIN movies\n" +
                        "ON movies.tconst = actortitles.tconst\n" +
                        "WHERE ratings.rating < 5 AND movies.type = 'movie'\n" +
                        "GROUP BY actorinfo.nconst\n" +
                        "ORDER BY COUNT(actorinfo.nconst) DESC\n" +
                        "LIMIT 1;", new MeesteSlechteFilmsRowMapper());

        model.addAttribute("result", result);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        return "vraag6";
    }

    //Rscript door Jesse en Koen
    @PostMapping("/vraag7")
    public String vraag7Submit(Model model) throws IOException {
        String file = "src/main/resources/scriptsR/budgetLengthVis.R";
        File filewatch = new File("src/main/resources/static/images/budgetLengthVis.png");
        if (filewatch.exists()) {filewatch.delete();}
        runRScript(file);
        while (!filewatch.exists()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "vraag7";
    }

    //Rscript door Koen
    @PostMapping("/vraag8")
    public String vraag8Submit(Model model) throws IOException {
        String file = "src/main/resources/scriptsR/digitVis.R";
        File filewatch = new File("src/main/resources/static/images/digitVis.png");
        if (filewatch.exists()) {filewatch.delete();}
        runRScript(file);
        while (!filewatch.exists()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "vraag8";
    }

    //Rscript door Sybrand
    @PostMapping("/vraag9")
    public String vraag9Submit(Model model) throws IOException {
        String file = "src/main/resources/scriptsR/genre.R";
        File filewatch = new File("src/main/resources/static/images/genreVis.png");
        if (filewatch.exists()) {filewatch.delete();}
        runRScript(file);

        while (!filewatch.exists()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "vraag9";
    }

    //SQL query door Koen
    @PostMapping("/vraag10")
    public String vraag10Submit(Model model) {
        Hypothese result = jdbcTemplate.queryForObject(
                "SELECT (SELECT AVG(rating)FROM ratings INNER JOIN languages ON ratings.tconst = languages.tconst WHERE language = 'Dutch') AS ratingNL,\n" +
                        "(SELECT AVG(rating) FROM ratings INNER JOIN languages ON ratings.tconst = languages.tconst WHERE NOT language = 'Dutch') AS ratingOther", new HypotheseRowMapper());

        model.addAttribute("result", result);
        return "vraag10";
    }

    //Rcaller werkend door Koen
    private static void runRScript(String file) throws IOException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine en = manager.getEngineByName("RCaller");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                System.out.println(line);
                en.eval(line);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
