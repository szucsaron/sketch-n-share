package com.codecool.sketch.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class DatabaseAbstractDao implements AutoCloseable {
    protected final Connection connection;

    public DatabaseAbstractDao(Connection connection) {
        this.connection = connection;
    }

    void executeInsert(PreparedStatement statement) throws SQLException {
        int insertCount = statement.executeUpdate();
        if (insertCount != 1) {
            connection.rollback();
            throw new SQLException("More than one row inserted, only one was expected. Transaction rolled back");
        }
    }

    int fetchGeneratedId(PreparedStatement statement) throws SQLException {
        int id;
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                connection.rollback();
                throw new SQLException("Expected 1 result");
            }
        }
        connection.commit();
        return id;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
