package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.AantalFilmsInLand;
import com.example.Bigdatanieuw.data.ActeurInFilms;
import com.example.Bigdatanieuw.data.HoogsteKosten;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HoogteKostenRowMapper implements RowMapper<HoogsteKosten> {

    @Override
    public HoogsteKosten mapRow(ResultSet rs, int rowNum) throws SQLException {

        HoogsteKosten customer = new HoogsteKosten(rs.getLong("tconst"), rs.getString("title"), rs.getLong("budget"), rs.getDouble("rating"), rs.getInt("votes"));

        return customer;

    }
}
