package json.provider;

import java.io.File;

import shared.GenericFactory;
import shared.plugin.ICommandDao;
import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;

public class Plugin implements IPersistenceProvider {

    private static final String DB_ROOT_DIR_NAME = "json_db";

    private File root;
    private IUserDao _userDao;
    private IGameDao _gameDao;
    private ICommandDao _commandDao;

    public Plugin(){
        root = new File(DB_ROOT_DIR_NAME);

        if (root.exists() && root.isDirectory()) return;

        boolean success = root.mkdir();
        if (success) {
            System.out.println("Root DB Created");
        }

        GenericFactory.register(IUserDao.class, JsonUserDao.class);
        GenericFactory.register(IGameDao.class, JsonGameDao.class);
        GenericFactory.register(ICommandDao.class, JsonCommandDao.class);
    }

    @Override
    public void clear() {
        deleteDir(root);
        root.mkdir();
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

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void main(String[] args){
        System.out.println("Testing, testing, 1 2 3, testing");
        Plugin plugin = new Plugin();
        plugin.clear();
        plugin.getUserDao();
        System.out.println("Got the user Dao!");
    }
}
