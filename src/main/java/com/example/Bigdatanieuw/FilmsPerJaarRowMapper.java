package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.FilmsPerJaar;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmsPerJaarRowMapper implements RowMapper<FilmsPerJaar> {

    @Override
    public FilmsPerJaar mapRow(ResultSet rs, int rowNum) throws SQLException {

        FilmsPerJaar customer = new FilmsPerJaar(rs.getInt("year"), rs.getLong("hoeveelheid"));

        return customer;

    }
}
