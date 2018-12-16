package com.example.jdbclearning;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public class ExampleRowSetListener implements RowSetListener {
    @Override
    public void rowSetChanged(RowSetEvent event) {
        System.out.println("Called rowSetChanged in ExampleRowSetListener");
    }

    @Override
    public void rowChanged(RowSetEvent event) {
        System.out.println("Called rowChanged in ExampleRowSetListener");
    }

    @Override
    public void cursorMoved(RowSetEvent event) {
        System.out.println("Called cursorMoved in ExampleRowSetListener");
    }
}
