package client.facade;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.exception.DeckException;
import shared.model.Game;
import shared.model.Player;
import shared.model.decks.TrainCard;
import shared.model.decks.TrainDeck;
import shared.model.request.TrainCardRequest;

public class TrainCardService {
    private ClientModel _client_instance = ClientModel.getInstance();
    private Game _game = _client_instance.getCurrentGame();

    public void drawFaceUpCard(AsyncServerTask.AsyncCaller caller, TrainCard trainCard, Player player){
        TrainDeck deck = _game.getTrainDeck();

        try {
            deck.drawFaceUpCard(trainCard);
            player.addTrainCard(trainCard);
        } catch (DeckException e) {
            e.printStackTrace();
        }


        TrainCardRequest request = new TrainCardRequest(deck, player);

        String[] paramTypes = {TrainCardRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_update_train_deck_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }

    public void drawFaceDownCard(AsyncServerTask.AsyncCaller caller, Player player){
        TrainDeck deck = _game.getTrainDeck();

        try{
            TrainCard trainCard = deck.drawFaceDownCard();
            player.addTrainCard(trainCard);
        } catch (DeckException e) {
            e.printStackTrace();
        }

        TrainCardRequest request = new TrainCardRequest(deck, player);

        String[] paramTypes = {TrainCardRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_update_train_deck_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }
}
