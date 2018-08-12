package server.facade;

import java.util.List;

import server.exception.ServerException;
import server.helper.SnapshotHelper;
import server.model.ServerModel;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.exception.DatabaseException;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.model.GameAction;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.decks.DestDeck;
import shared.model.request.DestCardRequest;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;
import shared.model.wrapper.ThreeDestCardWrapper;

public class DestCardService {

    public static IResponse sendSetupResults(DestCardRequest request){

        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {

            // Add the discarded cards back to the deck
            List<DestCard> discardedCards = request.get_discardCards().getDestCards();
            for (DestCard card : discardedCards) {
                serverModel.addDestCardToDeck(request.get_gameID(), card);
            }

            //Get player
            Player player = serverModel.getPlayer(request.get_playerID());

            //Puts kept cards into player's hand
            List<DestCard> keptCards = request.get_keepCards().getDestCards();
            for (DestCard card : keptCards) {
                player.addDestCard(card);
            }

            // Makes command to update deck of clients
            String className = ConfigurationManager.get("client_facade_name");
            String methodName = ConfigurationManager.get("client_set_dest_deck_method");
            String[] paramTypes = { DestDeck.class.getCanonicalName() };
            Object[] paramValues = { serverModel.getGame(request.get_gameID()).getDestDeck() };
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            // Makes game action object
            GameAction action = new GameAction(player.getDisplayName(), " selected destination cards", request.get_gameID());

            //adds game action into server model
            serverModel.addGameAction(request.get_gameID(), action);

            //makes command to do same on client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes2 = { GameAction.class.getCanonicalName() };
            Object[] paramValues2 = { action };
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);

            //updates player in server model
            serverModel.updatePlayer(request.get_gameID(), player);

            //makes command to do same on client
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_update_player_method");
            String[] paramTypes3 = { Player.class.getCanonicalName() };
            Object[] paramValues3 = { player };
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);

            //increment the number of players completed setup
            serverModel.getGame(request.get_gameID()).playerCompletedSetup();

            // Increment the number of players who have completed the setup
            String className4 = ConfigurationManager.get("client_facade_name");
            String methodName4 = ConfigurationManager.get("client_player_completed_setup");
            String[] paramTypes4 = {};
            Object[] paramValues4 = {};
            ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);

            //adds commands to server model
            serverModel.addCommand(request.get_gameID(), command);  //sets deck
            int commandIndex = serverModel.getCommands(request.get_gameID()).size() - 1;
            serverModel.addCommand(request.get_gameID(), command2); //adds game history entry
            int command2Index = serverModel.getCommands(request.get_gameID()).size() - 1;
            serverModel.addCommand(request.get_gameID(), command3); //updates player
            int command3Index = serverModel.getCommands(request.get_gameID()).size() - 1;
            serverModel.addCommand(request.get_gameID(), command4); //updates number of players completed setup
            int command4Index = serverModel.getCommands(request.get_gameID()).size() - 1;


            //------------------------------------Database stuff--------------------------------------------------//

            if (!serverModel.isInitializing()){
                //Adding Server command into database
                String[] paramTypesServer = { request.getClass().getCanonicalName() };
                Object[] paramValuesServer = { request };
                GenericCommand commandServer = new GenericCommand(
                        ConfigurationManager.get("server_facade_name"),
                        ConfigurationManager.get("server_send_setup_results"),
                        paramTypesServer,
                        paramValuesServer,
                        null
                );
                int commandServerIndex = serverModel.getGame(request.get_gameID()).getCommandCountSinceSnapshot();
                SnapshotHelper.addServerCommandToDatabase(request.get_gameID(), commandServer, commandServerIndex);

                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command, commandIndex);
                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command2, command2Index);
                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command3, command3Index);
                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command4, command4Index);
            }

            //gets new list of commands and sets it as response's commands
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));
            response.setSuccess(true);


        } catch (ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }

        return response;

    }

    public static IResponse getThreeDestCards(Player player){

        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {

            List<DestCard> cardsList;
            synchronized (serverModel){
                cardsList = serverModel
                .getGame(player.getGameID())
                .getDestDeck()
                .getThreeCards();
            }

            ThreeDestCardWrapper wrapper = new ThreeDestCardWrapper();
            wrapper.setCards(cardsList);

            String[] paramTypes = { ThreeDestCardWrapper.class.getCanonicalName(), String.class.getCanonicalName() };
            Object[] paramValues ={ wrapper, player.getPlayerID() };

            GenericCommand command = new GenericCommand(
                    ConfigurationManager.get("client_facade_name"),
                    ConfigurationManager.get("set_destination_option_cards"),
                    paramTypes,
                    paramValues,
                    null
            );

            serverModel.addCommand(player.getGameID(), command); //adds command to server
            int commandIndex = serverModel.getCommands(player.getGameID()).size() - 1;
//            List<ICommand> commandList = new ArrayList<>();
//            commandList.add(command);


            //------------------------------------Database stuff--------------------------------------------------//
            //Adds Server command
            if (!serverModel.isInitializing()){
                String[] paramTypesServer = { player.getClass().getCanonicalName() };
                Object[] paramValuesServer = { player };
                GenericCommand commandServer = new GenericCommand(
                        ConfigurationManager.get("server_facade_name"),
                        ConfigurationManager.get("server_request_three_dest_cards"),
                        paramTypesServer,
                        paramValuesServer,
                        null
                );
                int commandServerIndex = serverModel.getGame(player.getGameID()).getCommandCountSinceSnapshot();
                SnapshotHelper.addServerCommandToDatabase(player.getGameID(), commandServer, commandServerIndex);

                //adds client command
                SnapshotHelper.addClientCommandToDatabase(player.getGameID(), command, commandIndex);
            }

            response.setCommands(serverModel.getCommands(player.getGameID(), player.getPlayerID()));
            response.setSuccess(true);


        } catch (ServerException | DeckException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e){
            System.out.println(e.getMessage());
        }

        return response;
    }

    public static IResponse drawDestCards(DestCardRequest request)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();
        try {
            // Add the discarded cards back to the deck
            List<DestCard> discardedCards = request.get_discardCards().getDestCards();
            for (DestCard card : discardedCards) {
                serverModel.addDestCardToDeck(request.get_gameID(), card);
            }

            //Get player
            Player player = serverModel.getPlayer(request.get_playerID());

            //Puts kept cards into player's hand
            List<DestCard> keptCards = request.get_keepCards().getDestCards();
            for (DestCard card : keptCards) {
                player.addDestCard(card);
            }

            //update player in server (to keep map of Players and players in Games synced)
            serverModel.updatePlayer(player.getGameID(), player);

            //Make game action saying this person drew destination cards
            GameAction action = new GameAction(request.get_player().getDisplayName(), " selected destination cards", request.get_gameID());

            //add game action to server
            serverModel.addGameAction(request.get_gameID(), action);

            //change turns
            serverModel.getGame(request.get_gameID()).changeTurns();


            //Command to update dest deck for client
            String className = ConfigurationManager.get("client_facade_name");
            String methodName = ConfigurationManager.get("client_set_dest_deck_method");
            String[] paramTypes = { DestDeck.class.getCanonicalName() };
            Object[] paramValues = { serverModel.getGame(request.get_gameID()).getDestDeck() };
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
            serverModel.addCommand(request.get_gameID(), command);
            int commandIndex = serverModel.getCommands(request.get_gameID()).size() - 1;

            //Command to update player for the client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_update_player_method");
            String[] paramTypes2 = {Player.class.getCanonicalName()};
            Object[] paramValues2 = {serverModel.getPlayer(request.get_playerID())};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);
            serverModel.addCommand(request.get_gameID(), command2);
            int command2Index = serverModel.getCommands(request.get_gameID()).size() - 1;

            //Command to add game action for client
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes3 = {GameAction.class.getCanonicalName()};
            Object[] paramValues3 = {action};
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);
            serverModel.addCommand(request.get_gameID(), command3);
            int command3Index = serverModel.getCommands(request.get_gameID()).size() - 1;

            //Command to change turns for client
            String className4 = ConfigurationManager.getString("client_facade_name");
            String methodName4 = ConfigurationManager.getString("client_change_turns_method");
            String[] paramTypes4 = new String[0];
            Object[] paramValues4 = new Object[0];
            ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);
            serverModel.addCommand(request.get_gameID(), command4);
            int command4Index = serverModel.getCommands(request.get_gameID()).size() - 1;


            //------------------------------------Database stuff--------------------------------------------------//
            //Adds Server command
            if (!serverModel.isInitializing()){
                String[] paramTypesServer = { request.getClass().getCanonicalName() };
                Object[] paramValuesServer = { request };
                GenericCommand commandServer = new GenericCommand(
                        ConfigurationManager.get("server_facade_name"),
                        ConfigurationManager.get("server_draw_dest_cards_method"),
                        paramTypesServer,
                        paramValuesServer,
                        null
                );
                int commandServerIndex = serverModel.getGame(request.get_gameID()).getCommandCountSinceSnapshot();
                SnapshotHelper.addServerCommandToDatabase(request.get_gameID(), commandServer, commandServerIndex);

                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command, commandIndex);
                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command2, command2Index);
                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command3, command3Index);
                SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command4, command4Index);
            }

            //sets response's list of commands to be new commands for client
            response.setSuccess(true);
            response.setCommands(serverModel.getCommands(request.get_gameID(), request.get_playerID()));


        } catch (ServerException | InvalidGameException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }


}
