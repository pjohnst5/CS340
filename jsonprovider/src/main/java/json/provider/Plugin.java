package json.provider;

import shared.plugin.ICommandDao;
import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;

public class Plugin implements IPersistenceProvider {

    @Override
    public void clear() {
        System.out.println("JSON Provider: Clear Called");
    }

    @Override
    public void endTransaction(boolean commit) {
        System.out.println("JSON Provider: endTransaction Called");
    }

    @Override
    public void startTransaction() {
        System.out.println("JSON Provider: startTransaction Called");
    }

    @Override
    public IUserDao getUserDao() {
        System.out.println("JSON Provider: getUserDao Called");
        return null;
    }

    @Override
    public IGameDao getGameDao() {
        System.out.println("JSON Provider: getGameDao Called");
        return null;
    }

    @Override
    public ICommandDao getCommandDao() {
        System.out.println("JSON Provider: getCommandDao Called");
        return null;
    }

    public static void main(String[] args){
        System.out.println("Testing, testing, 1 2 3, testing");
    }
}
