package sql.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import shared.exception.DatabaseException;
import shared.exception.InvalidUserException;
import shared.model.User;
import shared.plugin.ICommandDao;
import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;
import sql.provider.dao.CommandDao;
import sql.provider.dao.GameDao;
import sql.provider.dao.UserDao;

public class Plugin implements IPersistenceProvider {

    public static void main(String[] args) {
        // run some tests
        new DatabaseManager(); // makes sure the database gets initialized

        // clear the database
        IPersistenceProvider provider = new Plugin();
        try {
            provider.clear();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return;
        }

        // create a dummy user object
        User user;
        User user2;
        try {
            user = new User("henry", "blue");
            user2 = new User("blade", "peanutbutter");
        } catch (InvalidUserException e) {
            e.printStackTrace();
            return;
        }

        // add the user to the database
        IUserDao dao = provider.getUserDao();
        try {
            dao.addUser(user);
            dao.addUser(user2);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return;
        }

        // extract the user(s) from the database
        List<User> extractedUsers;
        try {
            extractedUsers = dao.getUsers();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return;
        }
        for (User u : extractedUsers) {
            System.out.println("Extracted user: " + u.getUserName());
        }

        // clear the database again
        try {
            provider.clear();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void clear() throws DatabaseException {
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String[] sqlStrings = {"DELETE FROM `users`",
                "DELETE FROM `games`",
                "DELETE FROM `serverCommands`",
                "DELETE FROM `clientCommands`",};
        for (String sqlString : sqlStrings) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                db.closeConnection(false);
                throw new DatabaseException("Clear database failed", e);
            }
        }
        db.closeConnection(true);
    }

    @Override
    public IUserDao getUserDao() {
        return new UserDao();
    }

    @Override
    public IGameDao getGameDao() {
        return new GameDao();
    }

    @Override
    public ICommandDao getCommandDao() {
        return new CommandDao();
    }
}
