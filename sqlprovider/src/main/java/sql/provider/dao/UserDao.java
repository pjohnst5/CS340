package sql.provider.dao;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.exception.DatabaseException;
import shared.model.User;
import shared.plugin.IUserDao;
import shared.serialization.Serializer;
import sql.provider.DatabaseManager;

public class UserDao implements IUserDao {

    @Override
    public void addUser(User user) throws DatabaseException {
        if (user == null) {
            throw new DatabaseException("Null reference to User parameter");
        }
        if (user.getUserName() == null) {
            throw new DatabaseException("User parameter has null username");
        }
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "INSERT INTO users VALUES(?, ?)";
        boolean commit = true;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, user.getUserName());
            String json = Serializer._serialize(user);
            pstmt.setString(2, json);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DatabaseException("Add user failed", e);
        } finally {
            db.closeConnection(commit);
        }
    }

    @Override
    public List<User> getUsers() throws DatabaseException {
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "SELECT * FROM users";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            ArrayList<User> users = new ArrayList<>();
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    String json = res.getString("jsonData");
                    User user = (User) Serializer._deserialize(new StringReader(json), User.class);
                    users.add(user);
                }
            } catch (IOException e) {
                throw new DatabaseException("Get users failed", e);
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException("Get users failed", e);
        } finally {
            db.closeConnection(false);
        }
    }
}
