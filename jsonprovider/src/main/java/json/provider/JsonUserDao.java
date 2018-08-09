package json.provider;

import java.util.List;

import shared.exception.DatabaseException;
import shared.model.User;
import shared.plugin.IUserDao;

public class JsonUserDao implements IUserDao {
    @Override
    public void addUser(User user) throws DatabaseException {

    }

    @Override
    public List<User> getUsers() throws DatabaseException {
        return null;
    }
}
