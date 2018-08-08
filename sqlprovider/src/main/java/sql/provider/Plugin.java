package sql.provider;

import shared.plugin.ICommandDao;
import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;

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
