package json.provider.file.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileManager {

    /**
     *
     * plugins/
     * plugins/json_db/
     *
     * plugins/json_db/users/
     * plugins/json_db/users/<user_id>
     *
     * plugins/json_db/games/
     * plugins/json_db/games/<game_id>/
     * plugins/json_db/games/<game_id>/game
     * plugins/json_db/games/<game_id>/command_index
     *
     * plugins/json_db/commands/
     * plugins/json_db/commands/<game_id>/<index>
     */

    public static final boolean OVERWRITE = true;
    public static final boolean PRESERVE = false;

    private static final String pluginsDirName = "plugins";
    private static final String json_dbDirName = pluginsDirName + File.separator + "json_db";
    public static final String usersDirName = json_dbDirName + File.separator + "users";
    public static final String gamesDirName = json_dbDirName + File.separator + "games";
    public static final String clientCommandsDirName = json_dbDirName + File.separator + "client_commands";
    public static final String serverCommandsDirName = json_dbDirName + File.separator + "server_commands";

    public static final String commandIndexFileName = "command_index";
    public static final String gameFileName = "game";


    boolean createDir(File file, String dirName, boolean overwrite){
        file = new File(dirName);
        if (file.exists() && file.isDirectory() && !overwrite) return true;
        if (overwrite) deleteDir(file);
        return file.mkdirs();

    }

    void createFile(String filepath, String contents, boolean overwrite){
        try {

            File file = new File(filepath);

            if (file.exists() && file.isFile()) {
                if (!overwrite) {
                    return;
                } else {
                    file.delete();
                }
            }

            file.getParentFile().mkdirs();
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create file: " + filepath);
        }
    }

    protected boolean deleteDir(File dir) {
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

}
