package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.MeesteSlechteFilms;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeesteSlechteFilmsRowMapper implements RowMapper<MeesteSlechteFilms> {

    @Override
    public MeesteSlechteFilms mapRow(ResultSet rs, int rowNum) throws SQLException {

        MeesteSlechteFilms customer = new MeesteSlechteFilms(rs.getString("name"), rs.getInt("hoeveelheid_films"));

        return customer;

    }
}
