package server.facade;


import server.exception.ServerException;
import server.model.ServerModel;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.model.GameAction;
import shared.model.decks.TrainDeck;
import shared.model.request.TrainCardRequest;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

public class TrainCardService {

    public static IResponse updateTrainDeck(TrainCardRequest request)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            //sets train deck in server model
            serverModel.setTrainDeck(request.get_gameID(), request.getDeck());

            //makes command to do the same for clients
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_train_deck_method");
            String[] paramTypes = {TrainDeck.class.getCanonicalName()};
            Object[] paramValues = {request.getDeck()};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //Makes game action object
            GameAction action = new GameAction(serverModel.getPlayer(request.get_playerID()).getDisplayName(), " drew some train cards");

            //adds game action into server model
            serverModel.addGameAction(request.get_gameID(), action);

            //makes command to do same on client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes2 = {GameAction.class.getCanonicalName()};
            Object[] paramValues2 = {action};

            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);

            //changes turns on server
            serverModel.changeTurn(request.get_gameID());

            //makes command to change turns on client
            String className4 = ConfigurationManager.getString("client_facade_name");
            String methodName4 = ConfigurationManager.getString("client_change_turn_method");
            String[] paramTypes4 = null;
            Object[] paramValues4 = null;

            ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);

            //adds commands to server model
            serverModel.addCommand(request.get_gameID(), command);
            serverModel.addCommand(request.get_gameID(), command2);
            serverModel.addCommand(request.get_gameID(), command4);

            //gets new list of commands and sets it as response's commands
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));
            response.setSuccess(true);

        } catch (ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
