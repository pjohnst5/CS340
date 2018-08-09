package shared.plugin;

import shared.exception.DatabaseException;

public interface IPersistenceProvider {

    void clear() throws DatabaseException;
    void endTransaction(boolean commit);
    void startTransaction();
    void setDeltaUpdateInterval(int interval);

    IUserDao getUserDao();
    IGameDao getGameDao();
    ICommandDao getCommandDao();

}
