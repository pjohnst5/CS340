package sql.provider;

import sdk.plugin.ICommandDao;
import sdk.plugin.IGameDao;
import sdk.plugin.IPersistenceProvider;
import sdk.plugin.IUserDao;

public class Plugin implements IPersistenceProvider {

    @Override
    public void clear() {

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
