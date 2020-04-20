package com.webchat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBService {

    ResultSet runSelectQuery(String sql) throws SQLException;

    void runInsertOrUpdateQuery(String sql) throws SQLException;

}
