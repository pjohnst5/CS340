package json.provider.file.management;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.xml.crypto.Data;

import shared.exception.DatabaseException;
import shared.model.Game;
import shared.serialization.Serializer;

public class GameFilesManager extends FileManager {

    private File gamesDir;
    private static GameFilesManager _instance;

    private GameFilesManager(){
        createGamesDir(PRESERVE);
    }

    public static void clear(){
        instance().createGamesDir(OVERWRITE);
    }

    public static GameFilesManager instance(){
        if (_instance == null)
            _instance = new GameFilesManager();
        return _instance;
    }

    private boolean createGamesDir(boolean overwrite){
        return createDir(gamesDir, gamesDirName, overwrite);
    }

    public static boolean addGameDir(String gameId){
        return addGameDir(gameId, PRESERVE);
    }

    public static boolean addGameDir(String gameId, boolean overwrite){
        File gameFile = new File(gamesDirName + File.separator + gameId);

        if (gameFile.exists() && gameFile.isDirectory() && !overwrite) return true;
        if (overwrite) instance().deleteDir(gameFile);
        return gameFile.mkdirs();
    }

    public static void addGameFile(String gameId, String contents){
        addGameFile(gameId, contents, PRESERVE);
    }

    public static void addGameFile(String gameId, String contents, boolean overwrite){
        String fileName = gamesDirName + File.separator + gameId + File.separator + gameFileName;
        instance().createFile(fileName, contents, overwrite);
    }

    public static void addCmdIndex(String gameId, int index) {
        addCmdIndex(gameId, index, PRESERVE);
    }

    public static void addCmdIndex(String gameId, int index, boolean overwrite) {
        int oldIndex;
        try {
             oldIndex = getCmdIndex(gameId);
        } catch (DatabaseException e) {
            // file hasn't been initialized yet
            oldIndex = 0;
        }
        String fileName = gamesDirName + File.separator + gameId + File.separator + commandIndexFileName;
        String contents = Integer.toString((index+ oldIndex));
        instance().createFile(fileName, contents, overwrite);
    }

    public static int getCmdIndex(String gameId) throws DatabaseException {
        try {
            int i;
            String fileName = gamesDirName +
                    File.separator + gameId +
                    File.separator + commandIndexFileName;

            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            StringJoiner contents = new StringJoiner("");

            while ((i=reader.read()) != -1)
                contents.add(Character.toString((char)i));

            return Integer.parseInt(contents.toString().trim());

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not get command list index");
            throw new DatabaseException("Could not get command list index", e);
        }

    }

    public static List<Game> getAllGames() throws DatabaseException {
        File gamesFolder = new File(gamesDirName);
        List<Game> games = new ArrayList<>();

        try {

            if (gamesFolder.isDirectory()) {
                String[] gameIds = gamesFolder.list();
                if (gameIds == null) gameIds = new String[]{};

                for (String id : gameIds) {
                    String gameFilePath = id + File.separator + gameFileName;
                    File gameFile = new File(gamesFolder, gameFilePath);

                    FileReader reader = new FileReader(gameFile);
                    Game game = (Game)Serializer._deserialize(reader, Game.class);
                    reader.close();
                    games.add(game);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to deserialize game file");
            throw new DatabaseException("Failed to get all games", e);
        }

        return games;
    }

}
