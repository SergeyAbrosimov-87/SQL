package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SqlHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SqlHelper() {
    }

    //подключение к СУБД
    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
        QUERY_RUNNER.execute(connection, "DELETE FROM card_transactions");
        QUERY_RUNNER.execute(connection, "DELETE FROM cards");
        QUERY_RUNNER.execute(connection, "DELETE FROM users");
    }

    @SneakyThrows
    public static String getVerifaicationCode() {
        var requestSQL = "SELECT code FROM auth_codes ORDER BY created DESC";
        var conn = getConn();
        return QUERY_RUNNER.query(conn, requestSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanAuthCode() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
    }
}