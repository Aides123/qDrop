package me.khalit.qDrop.implementation.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by KHaliT on 19.08.2016.
 */
public interface Database {

    ResultSet executeSelect(PreparedStatement stat);

    void executeUpdate(PreparedStatement stat);

    Connection getConnection();

    void createTables() throws SQLException;
}
