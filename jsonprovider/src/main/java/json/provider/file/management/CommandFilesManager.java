package json.provider.file.management;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.serialization.Serializer;

public class CommandFilesManager extends FileManager {

    private File commandsDir;
    private static CommandFilesManager _instance;

    private CommandFilesManager() {
        createCommandsDir(PRESERVE);
    }

    public static CommandFilesManager instance(){
        if (_instance == null){
            _instance = new CommandFilesManager();
        }
        return _instance;
    }

    public static void clear() {
        instance().createCommandsDir(OVERWRITE);
    }

    private boolean createCommandsDir(boolean overwrite){
        return createDir(commandsDir, commandsDirName, overwrite);
    }

    public static void addCommand(String gameId, int index, String contents){
        String fileName = commandsDirName +
                File.separator + gameId +
                File.separator + index;
        instance().createFile(fileName, contents, OVERWRITE);
    }

    public static List<ICommand> getAllCommands(String gameId) throws DatabaseException {
        String commandDirPath = commandsDirName + File.separator + gameId;
        File commandsFolder = new File(commandDirPath);
        List<ICommand> commands = new ArrayList<>();

        try {

            if (commandsFolder.isDirectory()){
                String[] indices = commandsFolder.list();
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
