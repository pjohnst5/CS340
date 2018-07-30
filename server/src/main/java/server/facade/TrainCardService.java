package server.facade;


import com.sun.security.ntlm.Server;

import server.exception.ServerException;
import server.model.ServerModel;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.enumeration.TrainColor;
import shared.model.GameAction;
import shared.model.Player;
import shared.model.decks.TrainCard;
import shared.model.decks.TrainDeck;
import shared.model.request.FaceDownRequest;
import shared.model.request.FaceUpRequest;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

public class TrainCardService {

    public static IResponse drawFaceUp(FaceUpRequest request)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            //If the card they want is a locomotive and they already drew a card throw an exception
            if (request.get_faceUpCard().get_color() == TrainColor.LOCOMOTIVE && serverModel.getPlayer(request.get_playerID()).get_cardsDrawnThisTurn() == 1) {
                throw new ServerException("Can't draw a locomotive card after already drawing a card");
            }

            //draws face up card from game's train deck
            serverModel.getGame(request.get_gameID()).getTrainDeck().drawFaceUpCard(request.get_faceUpCard());

            //gets the player
            Player player = serverModel.getPlayer(request.get_playerID());

            //puts the newly drawn face up card into the player's hand
            player.addTrainCard(request.get_faceUpCard());

            //make game action to say the player drew a face up card
            GameAction action = new GameAction(request.get_player().getDisplayName(), " drew a face-up train card");

            //add game action into server
            serverModel.addGameAction(request.get_gameID(), action);

            //increment count of cards player has drawn this turn
            player.incrementCardsDrawnThisTurn();

            //update player in server (to keep map of Players and players in Games synced)
            serverModel.updatePlayer(player.getGameID(), player);

            //if card was locomotive or limit was reached, change turns
            if (request.get_faceUpCard().get_color() == TrainColor.LOCOMOTIVE || player.get_cardsDrawnThisTurn() == 2){
                serverModel.getGame(request.get_gameID()).changeTurns();
            }


            //command to update train deck for client
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_train_deck_method");
            String[] paramTypes = {TrainDeck.class.getCanonicalName()};
            Object[] paramValues = {serverModel.getGame(request.get_gameID()).getTrainDeck()};
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
            serverModel.addCommand(request.get_gameID(), command);

            //command to update player for client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_update_player_method");
            String[] paramTypes2 = {Player.class.getCanonicalName()};
            Object[] paramValues2 = {serverModel.getPlayer(request.get_playerID())};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);
            serverModel.addCommand(request.get_gameID(), command2);

            //command to add game action saying the player chose a face up train card
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes3 = {GameAction.class.getCanonicalName()};
            Object[] paramValues3 = {action};
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);
            serverModel.addCommand(request.get_gameID(), command3);

            //if the the card was locomotive or limit was reached, command to change turns for client
            if (request.get_faceUpCard().get_color() == TrainColor.LOCOMOTIVE || player.get_cardsDrawnThisTurn() == 2){
                String className4 = ConfigurationManager.getString("client_facade_name");
                String methodName4 = ConfigurationManager.getString("client_change_turns_method");
                String[] paramTypes4 = new String[0];
                Object[] paramValues4 = new Object[0];
                ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);
                serverModel.addCommand(request.get_gameID(), command4);
            }

            //sets response's list of commands to be new commands for client
            response.setSuccess(true);
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    public static IResponse drawFaceDown(FaceDownRequest request)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();
        try {
            //draw face down card from train deck
            TrainCard faceDownCard = serverModel.getGame(request.get_gameID()).getTrainDeck().drawFaceDownCard();

            //get player
            Player player = serverModel.getPlayer(request.get_playerID());

            //put newly drawn train card into player's hand
            player.addTrainCard(faceDownCard);

            //increment cards this turn count
            player.incrementCardsDrawnThisTurn();

            //update player in server (to keep map of Players and players in Games synced)
            serverModel.updatePlayer(player.getGameID(), player);

            //make game action saying the player drew a face down card
            GameAction action = new GameAction(request.get_player().getDisplayName(), " drew a face-down train card");

            //add game action to server
            serverModel.addGameAction(request.get_gameID(), action);

            //if limit has been reached for this player, change turns (This will handle any logic for changing to last round or ending the game)
            if (player.get_cardsDrawnThisTurn() == 2){
                serverModel.getGame(request.get_gameID()).changeTurns();
            }

            //command to update train deck for client
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_train_deck_method");
            String[] paramTypes = {TrainDeck.class.getCanonicalName()};
            Object[] paramValues = {serverModel.getGame(request.get_gameID()).getTrainDeck()};
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
            serverModel.addCommand(request.get_gameID(), command);

            //command to update player for client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_update_player_method");
            String[] paramTypes2 = {Player.class.getCanonicalName()};
            Object[] paramValues2 = {serverModel.getPlayer(request.get_playerID())};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);
            serverModel.addCommand(request.get_gameID(), command2);

            //command to add game action for client
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes3 = {GameAction.class.getCanonicalName()};
            Object[] paramValues3 = {action};
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);
            serverModel.addCommand(request.get_gameID(), command3);

            //if limit has been reached, command to change turns for client
            if (player.get_cardsDrawnThisTurn() == 2){
                String className4 = ConfigurationManager.getString("client_facade_name");
                String methodName4 = ConfigurationManager.getString("client_change_turns_method");
                String[] paramTypes4 = new String[0];
                Object[] paramValues4 = new Object[0];
                ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);
                serverModel.addCommand(request.get_gameID(), command4);
            }

            //sets response's list of commands to be new commands for client
            response.setSuccess(true);
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));
        } catch(Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

}
