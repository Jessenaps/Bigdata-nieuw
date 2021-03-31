package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.AantalFilmsInLand;
import com.example.Bigdatanieuw.data.ActeurInFilms;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AantalFilmsRowMapper implements RowMapper<AantalFilmsInLand> {

    @Override
    public AantalFilmsInLand mapRow(ResultSet rs, int rowNum) throws SQLException {

        AantalFilmsInLand customer = new AantalFilmsInLand(rs.getString("country"), rs.getLong("aantal_films"));

        return customer;

    }
}

