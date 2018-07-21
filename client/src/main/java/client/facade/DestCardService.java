package client.facade;

import java.util.List;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.exception.DeckException;
import shared.model.Game;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;
import shared.model.request.DestCardRequest;

public class DestCardService {
    private ClientModel _client_instance = ClientModel.getInstance();
    private Game _game = _client_instance.getCurrentGame();
    public void selectDestCard(AsyncServerTask.AsyncCaller caller, List<DestCard> destCards, Player player){
        DestDeck deck = _game.getDestDeck();

        for(int i = 0; i < destCards.size(); i++){
            player.addDestCard(destCards.get(i));
        }

        DestCardRequest request = new DestCardRequest(deck, player);

        String[] paramTypes = {DestCardRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_update_dest_deck"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }

    public void discardDestCard(AsyncServerTask.AsyncCaller caller, List<DestCard> destCards, Player player){
        DestDeck deck = _game.getDestDeck();

        for(int i = 0; i < destCards.size(); i++){
            try {
                deck.putDestCardBack(destCards.get(i));
            } catch (DeckException e) {
                e.printStackTrace();
            }
        }

        DestCardRequest request = new DestCardRequest(deck, player);

        String[] paramTypes = {DestCardRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_update_dest_deck"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }
}
