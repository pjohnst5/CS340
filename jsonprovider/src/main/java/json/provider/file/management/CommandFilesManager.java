package json.provider.file.management;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.serialization.Serializer;

public class CommandFilesManager extends FileManager {

    private File commandsDir;
    private static CommandFilesManager _instance;

    private CommandFilesManager() {
        createServerCommandsDir(PRESERVE);
        createClientCommandsDir(PRESERVE);
    }

    public static CommandFilesManager instance(){
        if (_instance == null){
            _instance = new CommandFilesManager();
        }
        return _instance;
    }

    public static void clear() {
        instance().createServerCommandsDir(OVERWRITE);
        instance().createClientCommandsDir(OVERWRITE);
    }

    private boolean createServerCommandsDir(boolean overwrite){
        return createDir(commandsDir, serverCommandsDirName, overwrite);
    }

    private boolean createClientCommandsDir(boolean overwrite){
        return createDir(commandsDir, clientCommandsDirName, overwrite);
    }

    public static void addServerCommand(String gameId, int index, String contents){
        addCommand(gameId, index, contents, serverCommandsDirName);
    }

    public static void addClientCommand(String gameId, int index, String contents){
        addCommand(gameId, index, contents, clientCommandsDirName);
    }

    private static void addCommand(String gameId, int index, String contents, String commandsDirName){
        String fileName = commandsDirName +
                File.separator + gameId +
                File.separator + index;
        instance().createFile(fileName, contents, OVERWRITE);
    }

    public static List<ICommand> getAllServerCommands(String gameId) throws DatabaseException{
        String commandDirPath = serverCommandsDirName + File.separator + gameId;
        return getAllCommands(gameId, commandDirPath);
    }

    public static List<ICommand> getAllClientCommands(String gameId) throws DatabaseException{
        String commandDirPath = clientCommandsDirName + File.separator + gameId;
        return getAllCommands(gameId, commandDirPath);
    }

    private static List<ICommand> getAllCommands(String gameId, String commandDirPath) throws DatabaseException {
        File commandsFolder = new File(commandDirPath);
        List<ICommand> commands = new ArrayList<>();

        try {

            if (commandsFolder.isDirectory()){
                String[] indices = commandsFolder.list();
                Arrays.sort(indices);
                if (indices == null) indices = new String[]{};

                for (String index : indices) {
                    File cmdFile = new File(commandsFolder, index);

                    FileReader reader = new FileReader(cmdFile);
                    ICommand command = (ICommand) Serializer._deserialize(reader, ICommand.class);
                    reader.close();
                    commands.add(command);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.err.println("Failed to deserialize game file");
            throw new DatabaseException("Failed to get all games", e);
        }

        return commands;
    }

}
