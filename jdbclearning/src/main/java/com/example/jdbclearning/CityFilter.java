package com.example.jdbclearning;

import javax.sql.RowSet;
import javax.sql.rowset.Predicate;
import java.sql.SQLException;

public class CityFilter implements Predicate {

    private String[] cities;
    private String colName = null;
    private int colNumber = -1;

    public CityFilter(String[] cities, String colName) {
        this.cities = cities;
        this.colName = colName;
    }

    public CityFilter(String[] cities, int colNumber) {
        this.cities = cities;
        this.colNumber = colNumber;
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {
        if (columnName.equalsIgnoreCase(colName)) {
            String columnValue = (String) value;
            for (String city : cities) {
                if (city.equalsIgnoreCase(columnValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean evaluate(Object value, int column) throws SQLException {
        if (column == colNumber) {
            String columnValue = (String) value;
            for (String city : cities) {
                if (city.equalsIgnoreCase(columnValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean evaluate(RowSet rs) {
        if (rs == null) {
            return false;
        }
        try {
            String cityName = null;
            if (colNumber > 0) {
                cityName = rs.getString(colNumber);
            } else if (colName != null) {
                cityName = rs.getString(colName);
            }
            for (String city : cities) {
                if (city.equalsIgnoreCase(cityName)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return false;
        }
    }
}

