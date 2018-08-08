package shared.plugin;

import shared.exception.DatabaseException;
import shared.model.User;

public interface IUserDao {

    void RegisterUser(User user) throws DatabaseException;
}
