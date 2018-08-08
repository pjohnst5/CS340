package server.helper;

import server.exception.ServerException;
import server.model.ServerModel;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.exception.DatabaseException;
import shared.model.Game;
import shared.plugin.PluginManager;

public class SnapshotHelper {

    public static void addCommandToDatabase(String gameID, ICommand command, int indexOfCommand) throws ServerException, DatabaseException {
        ServerModel serverModel = ServerModel.getInstance();
        Game game = serverModel.getGame(gameID);

        //Adds command into database
        PluginManager.getPlugin().getCommandDao().storeCommand(gameID, indexOfCommand, command);

        //Increment CommandCountSinceSnapshot for this game
        game.incrementCommandCountSinceSnapshot();

        //checks to see if the number of commands since the last snapshot == N
        if (game.getCommandCountSinceSnapshot() == ConfigurationManager.getInt("delta_update_interval")){
            //Takes new snapshot
            PluginManager.getPlugin().getGameDao().updateGame(game);

            //Resets CommandCountSinceSnapshot for this game
            game.resetCommandCountSinceSnapshot();
        }
    }
}
