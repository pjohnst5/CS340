package shared.plugin;

import shared.exception.DatabaseException;

public interface IPersistenceProvider {

    void clear() throws DatabaseException;

    IUserDao getUserDao();
    IGameDao getGameDao();
    ICommandDao getCommandDao();

}
