package com.dmdev.integration;

import com.dmdev.util.ConnectionManager;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author E.Parominsky 17/07/2023 17:38
 */
public class IntegrationTestBase {

    private static final String CLEAN_SQL = "DELETE FROM users;";
    private static final String CREATE_SQL = """
                    CREATE TABLE IF NOT EXISTS users
                    (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(64) NOT NULL,
                        birthday DATE NOT NULL,
                        email VARCHAR(64) NOT NULL UNIQUE,
                        password VARCHAR(64) NOT NULL,
                        role VARCHAR(32) NOT NULL,
                        gender VARCHAR(32) NOT NULL
                    );
            """;


    @BeforeAll
    static void prepareDatabase() throws SQLException {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();) {
            statement.execute(CREATE_SQL);
        }
    }

    @BeforeEach
    void cleanData() throws SQLException {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();) {
            statement.execute(CLEAN_SQL);
        }
    }

}
