package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.ActeurInFilms;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActeurRowMapper implements RowMapper<ActeurInFilms> {

    @Override
    public ActeurInFilms mapRow(ResultSet rs, int rowNum) throws SQLException {

        ActeurInFilms antwoord = new ActeurInFilms(rs.getString("name"), rs.getLong("aantal_films"));

        return antwoord;
    }
}

