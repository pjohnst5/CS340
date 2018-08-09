package json.provider.file.management;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shared.exception.DatabaseException;
import shared.model.User;
import shared.serialization.Serializer;

public class UserFilesManager extends FileManager {

    private File usersDir;
    private static UserFilesManager _instance;

    private UserFilesManager() {
        createUsersDir(PRESERVE);
    }

    public static UserFilesManager instance(){
        if (_instance == null){
            _instance = new UserFilesManager();
        }
        return _instance;
    }

    public static void clear() {
        instance().createUsersDir(OVERWRITE);
    }

    private boolean createUsersDir(boolean overwrite){
        return createDir(usersDir, usersDirName, overwrite);
    }

    public static void addUser(String userId, String contents){
        String fileName = usersDirName + File.separator + userId;
        instance().createFile(fileName, contents, OVERWRITE);
    }

    public static void updateUser(String userId, String contents){
        String fileName = usersDirName + File.separator + userId;
        instance().createFile(fileName, contents, OVERWRITE);
    }

    public static List<User> getAllUsers() throws DatabaseException {

        File usersFolder = new File(usersDirName);
        List<User> users = new ArrayList<>();

        try {
            if (usersFolder.isDirectory()){
                String[] userIds = usersFolder.list();
                if (userIds == null) userIds = new String[]{};

                for (String id : userIds){
                    File userFile = new File(usersFolder, id);

                    FileReader reader = new FileReader(userFile);
                    User user = (User) Serializer._deserialize(reader, User.class);
                    reader.close();
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to deserialize game file");
            throw new DatabaseException("Failed to get all games", e);
        }

        return users;
    }

}
