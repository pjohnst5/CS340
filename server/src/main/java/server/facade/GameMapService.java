package server.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.helper.DestCardHelper;
import server.model.ServerModel;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.enumeration.ClaimedRoutePointSystem;
import shared.model.GameAction;
import shared.model.Player;
import shared.model.Route;
import shared.model.decks.DestCard;
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

            //claims route for game
            serverModel.getGame(request.get_gameID()).claimRoute(request.get_route(), request.get_player());

            //this claims route that got passed in as a param. We'll eventually send this back to the client
            request.get_route().claimRoute(request.get_playerID(),request.get_player().getColor());

            //put cards back into the train deck
            serverModel.getGame(request.get_gameID()).getTrainDeck().discardTrainCards(request.get_discarded());

            //gets player from server
            Player player = serverModel.getPlayer(request.get_playerID());

            //takes train cards out of player's hand who just claimed the route (client already did this but server didn't yet)
            player.removeTrainCards(request.get_discarded());

            //takes train cars away from player who just claimed the route
            player.removeTrains(request.get_route().get_pathLength());

            //updates player points
            player.addPoints(new ClaimedRoutePointSystem().getPoints(request.get_route().get_pathLength()));

            //updates player dest ticket completion status
            Map<UUID, Route> claimedByPlayer = serverModel.getGame(request.get_gameID()).getMap().getRoutesClaimedByPlayer(request.get_playerID());
            DestCardHelper helper = new DestCardHelper(claimedByPlayer, request.get_player().getDestCards());
            List<DestCard> updatedDestCards = helper.updateDestCards();
            player.updateDestCards(updatedDestCards);

            //update player in server (to keep map of Players and players in Games synced)
            serverModel.updatePlayer(player.getGameID(), player);

            //Makes game action object saying this player claimed the route
            GameAction action = new GameAction(request.get_player().getDisplayName(), " claimed the route " + request.get_route().toString());

            //adds game action into server model
            serverModel.addGameAction(request.get_gameID(), action);

            //changes turns for the game (This will handle any logic for changing to last round or ending the game)
            serverModel.getGame(request.get_gameID()).changeTurns();



            //command to claim route for client
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_claim_route_method");
            String[] paramTypes = { Route.class.getCanonicalName()};
            Object[] paramValues = { request.get_route() };
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
            serverModel.addCommand(request.get_gameID(), command);

            //command to update train deck for client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_set_train_deck_method");
            String[] paramTypes2 = {TrainDeck.class.getCanonicalName()};
            Object[] paramValues2 = {serverModel.getGame(request.get_gameID()).getTrainDeck()};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);
            serverModel.addCommand(request.get_gameID(), command2);

            //command to update the player for client
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_update_player_method");
            String[] paramTypes3 = {Player.class.getCanonicalName()};
            Object[] paramValues3 = {serverModel.getPlayer(request.get_playerID())};
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);
            serverModel.addCommand(request.get_gameID(), command3);

            //command to add game action claim route for client
            String className4 = ConfigurationManager.getString("client_facade_name");
            String methodName4 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes4 = {GameAction.class.getCanonicalName()};
            Object[] paramValues4 = {action};
            ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);
            serverModel.addCommand(request.get_gameID(), command4);

            //command to change turns for client
            String className5 = ConfigurationManager.getString("client_facade_name");
            String methodName5 = ConfigurationManager.getString("client_change_turns_method");
            String[] paramTypes5 = new String[0];
            Object[] paramValues5 = new Object[0];
            ICommand command5 = new GenericCommand(className5, methodName5, paramTypes5, paramValues5, null);
            serverModel.addCommand(request.get_gameID(), command5);

            //sets updated list of commands as response's list
            response.setSuccess(true);
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));

        } catch(Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
