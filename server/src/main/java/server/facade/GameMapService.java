package server.facade;

import server.exception.ServerException;
import server.model.ServerModel;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.model.GameAction;
import shared.model.GameMap;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;
import shared.model.request.ClaimRouteRequest;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

public class GameMapService {

    public static IResponse claimRoute(ClaimRouteRequest request)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            //sets game map in server model
            serverModel.setGameMap(request.get_gameID(), request.get_map());

            //makes command to do same on client
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_map_method");
            String[] paramTypes = {GameMap.class.getCanonicalName()};
            Object[] paramValues = {request.get_map()};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //sets TrainDeck in server model
            serverModel.setTrainDeck(request.get_gameID(), request.get_deck());

            //makes command to do the same on client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_set_train_deck_method");
            String[] paramTypes2 = {TrainDeck.class.getCanonicalName()};
            Object[] paramValues2 = {request.get_deck()};

            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);

            //Makes game action object
            GameAction action = new GameAction(serverModel.getPlayer(request.get_playerID()).getDisplayName(), " claimed a route");

            //adds game action into server model
            serverModel.addGameAction(request.get_gameID(), action);

            //makes command to do same on client
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes3 = {GameAction.class.getCanonicalName()};
            Object[] paramValues3 = {action};

            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);


            //add commands to list in server model
            serverModel.addCommand(request.get_gameID(), command);
            serverModel.addCommand(request.get_gameID(), command2);
            serverModel.addCommand(request.get_gameID(), command3);

            //gets list of new commands for client
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));
            response.setSuccess(true);

        } catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
