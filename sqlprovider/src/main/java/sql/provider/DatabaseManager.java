package sql.provider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

import shared.exception.DatabaseException;

public class DatabaseManager {
    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            InitDatabase();
        }
        catch (ClassNotFoundException | DatabaseException e) {
            System.out.println("Unable to initialize Database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Connection conn;

    private static void InitDatabase() throws DatabaseException {
        DatabaseManager db = new DatabaseManager();
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }

    public Connection openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:" + "plugins" + File.separator + "sqlresources" + File.separator + "ticketToRide.db";
            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
        return conn;
    }
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }
    private void createTables() throws DatabaseException {
        // we're just going to load the statements in from a text file
        Path in = Paths.get("plugins" + File.separator + "sqlresources" + File.separator + "dbCreate.txt");
//        InputStream in = DatabaseManager.class.getClassLoader()
//                .getResourceAsStream("sqlresources/dbCreate.txt");
        StringBuilder sb;
        try (Scanner sc = new Scanner(in)) {
            sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                sb.append("\n");
            }
        } catch (IOException e) {
            throw new DatabaseException("unable to load create tables file", e);
        }

        String[] tableStmts = sb.toString().split(";");
        // there will be a blank entry at the end; we want to trim it off
        tableStmts = Arrays.copyOf(tableStmts, tableStmts.length - 1);

        for (String tableStmt : tableStmts) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(tableStmt);
            } catch (SQLException e) {
                throw new DatabaseException("Create tables failed for table: " + tableStmt, e);
            }
        }
    }
}
