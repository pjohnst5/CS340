package shared.plugin;

import java.util.List;

import shared.exception.DatabaseException;
import shared.model.User;

public interface IUserDao {

    void addUser(User user) throws DatabaseException;

    void updateUser(User user) throws DatabaseException;

    List<User> getUsers() throws DatabaseException;
}
