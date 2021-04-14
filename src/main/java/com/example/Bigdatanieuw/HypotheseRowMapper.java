package com.example.Bigdatanieuw;

import com.example.Bigdatanieuw.data.Hypothese;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HypotheseRowMapper implements RowMapper<Hypothese> {

    @Override
    public Hypothese mapRow(ResultSet rs, int rowNum) throws SQLException {

        Hypothese customer = new Hypothese(rs.getDouble("ratingnl"), rs.getDouble("ratingother"));

        return customer;

    }
}

