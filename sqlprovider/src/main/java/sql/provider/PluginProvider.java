package sql.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shared.exception.DatabaseException;
import shared.exception.InvalidUserException;
import shared.model.User;
import shared.plugin.ICommandDao;
import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;
import sql.provider.dao.UserDao;

public class Plugin implements IPersistenceProvider {

    public static void main(String[] args) {
        // run some tests
        DatabaseManager db = new DatabaseManager(); // makes sure the database gets initialized
        SQLProvider provider = new SQLProvider();
        try {
            provider.clear();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return;
        }
        User user;
        try {
            user = new User("henry", "blue");
        } catch (InvalidUserException e) {
            e.printStackTrace();
            return;
        }
        UserDao dao = new UserDao();
        try {
            dao.addUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() throws DatabaseException {
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String[] sqlStrings = {"DROP TABLE IF EXISTS `users`",
                "DROP TABLE IF EXISTS `games`",
                "DROP TABLE IF EXISTS `commands`"};
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
    public void endTransaction(boolean commit) {

    }

    @Override
    public void startTransaction() {

    }

    @Override
    public IUserDao getUserDao() {
        return null;
    }

    @Override
    public IGameDao getGameDao() {
        return null;
    }

    @Override
    public ICommandDao getCommandDao() {
        return null;
    }
}
