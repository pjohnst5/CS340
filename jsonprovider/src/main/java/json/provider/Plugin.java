package json.provider;

import json.provider.dao.JsonCommandDao;
import json.provider.dao.JsonGameDao;
import json.provider.dao.JsonUserDao;
import json.provider.file.management.CommandFilesManager;
import json.provider.file.management.GameFilesManager;
import json.provider.file.management.UserFilesManager;
import shared.GenericFactory;
import shared.plugin.ICommandDao;
import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;

public class Plugin implements IPersistenceProvider {

    private IUserDao _userDao;
    private IGameDao _gameDao;
    private ICommandDao _commandDao;

    public Plugin(){
        GenericFactory.register(IUserDao.class, JsonUserDao.class);
        GenericFactory.register(IGameDao.class, JsonGameDao.class);
        GenericFactory.register(ICommandDao.class, JsonCommandDao.class);
    }

    @Override
    public void clear() {
        GameFilesManager.clear();
        UserFilesManager.clear();
        CommandFilesManager.clear();
    }

    @Override
    public void endTransaction(boolean commit) {
        System.err.println("JSON Provider: endTransaction not yet implemented");
    }

    @Override
    public void startTransaction() {
        System.err.println("JSON Provider: startTransaction not yet implemented");
    }

    @Override
    public void setDeltaUpdateInterval(int interval) {
        System.err.println("JSON Provider: setDeltaUpdateInterval not yet implemented ");
    }

    @Override
    public IUserDao getUserDao() {

        if (_userDao == null){
            _userDao = GenericFactory.instantiate(IUserDao.class);
        }

        return _userDao;

    }

    @Override
    public IGameDao getGameDao() {

       if (_gameDao == null){
           _gameDao = GenericFactory.instantiate(IGameDao.class);
       }

       return _gameDao;

    }

    @Override
    public ICommandDao getCommandDao() {

        if (_commandDao == null){
            _commandDao = GenericFactory.instantiate(ICommandDao.class);
        }

        return _commandDao;

    }
}
