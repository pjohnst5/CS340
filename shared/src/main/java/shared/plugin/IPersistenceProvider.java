package shared.plugin;

public interface IPersistenceProvider {

    void clear();
    void endTransaction(boolean commit);
    void startTransaction();

    IUserDao getUserDao();
    IGameDao getGameDao();
    ICommandDao getCommandDao();

}
