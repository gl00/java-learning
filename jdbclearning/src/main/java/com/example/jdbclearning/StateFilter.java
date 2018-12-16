package com.example.jdbclearning;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;
import java.sql.SQLException;

public class StateFilter implements Predicate {

    private int low;
    private int high;
    private String colName = null;
    private int colNumber = -1;

    public StateFilter(int low, int high, String colName) {
        this.low = low;
        this.high = high;
        this.colName = colName;
    }

    public StateFilter(int low, int high, int colNumber) {
        this.low = low;
        this.high = high;
        this.colNumber = colNumber;
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {
        if (columnName.equalsIgnoreCase(colName)) {
            int columnValue = (Integer) value;
            return columnValue >= low && columnValue <= high;
        }
        return true;
    }

    @Override
    public boolean evaluate(Object value, int column) throws SQLException {
        if (column == colNumber) {
            int columnValue = (Integer) value;
            return (columnValue >= this.low) && (columnValue <= this.high);
        }
        return true;
    }

    @Override
    public boolean evaluate(RowSet rs) {
        if (rs == null) {
            return false;
        }
        CachedRowSet crs = (CachedRowSet) rs;
        try {
            int columnValue = -1;
            if (colNumber > 0) {
                columnValue = crs.getInt(colNumber);
            } else if (colName != null) {
                columnValue = crs.getInt(colName);
            } else {
                return false;
            }

            return columnValue >= low && columnValue <= high;
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return false;
        }
        // 注意：这里不能关闭传入的 RowSet
        // 过滤器用于遍历 FilterRowSet 对象，如果在遍历的中途关闭被遍历的对象，会导致空指针异常
    }
}
