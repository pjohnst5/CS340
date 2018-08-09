package json.provider.dao;

import java.util.List;

import json.provider.file.management.UserFilesManager;
import shared.exception.DatabaseException;
import shared.model.User;
import shared.plugin.IUserDao;
import shared.serialization.Serializer;

public class JsonUserDao implements IUserDao {
    @Override
    public void addUser(User user) throws DatabaseException {

        String contents = Serializer._serialize(user);
        String userId = user.getUUID().toString();
        UserFilesManager.addUser(userId, contents);

    }

    @Override
    public List<User> getUsers() throws DatabaseException {
        return UserFilesManager.getAllUsers();
    }
}
