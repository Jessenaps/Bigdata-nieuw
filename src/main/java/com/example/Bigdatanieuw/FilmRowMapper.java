package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.filmRating;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmRowMapper implements RowMapper<filmRating> {

    @Override
    public filmRating mapRow(ResultSet rs, int rowNum) throws SQLException {

        filmRating antwoord = new filmRating(rs.getString("title"), rs.getLong("tconst"), rs.getLong("minutes"),rs.getString("type"), rs.getDouble("rating"));

        return antwoord;

    }
}
