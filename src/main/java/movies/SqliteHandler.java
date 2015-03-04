package movies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.*;
import java.util.List;

public class SqliteHandler {
    private PreparedStatement prep;
    private Connection conn;
    private Statement stat;

    SqliteHandler(Boolean is_print) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:imdb.db");
        stat = conn.createStatement();
        if (!is_print) {
            stat.executeUpdate("drop table if exists imdb;");
            stat.executeUpdate("create table imdb (year, name, rating);");
            prep = conn.prepareStatement(
                    "insert into imdb values (?, ?, ?);");
        }

    }


    public void insert_values(List<String> data) throws SQLException {

        // fixed data type to avoid sql injection
        prep.setString(1, data.get(0));
        prep.setString(2, data.get(1));
        prep.setString(3, data.get(2));
        prep.addBatch();
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    public void print_db_values() throws SQLException {
        ResultSet resultSet = stat.executeQuery("select * from imdb order by rating DESC, name;");
        System.out.println("List of available movies");
        while (resultSet.next()) {
            System.out.println("-------------------------------" +
                            "\nyear: " + resultSet.getString("year") +
                            "\nname: " + resultSet.getString("name") +
                            "\nrating: " + resultSet.getString("rating")

            );

        }
        resultSet.close();
        close_connection();
    }

    public void close_connection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

