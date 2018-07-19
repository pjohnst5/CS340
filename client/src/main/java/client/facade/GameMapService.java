package client.facade;

import java.util.List;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.model.Game;
import shared.model.GameMap;
import shared.model.Player;
import shared.model.Route;
import shared.model.User;
import shared.model.decks.TrainCard;
import shared.model.request.ClaimRouteRequest;

public class GameMapService {
    public void claimRoute(AsyncServerTask.AsyncCaller caller, Route route, List<TrainCard> discardedCards) throws DeckException, InvalidGameException {
        ClientModel client_instance = ClientModel.getInstance();

        Game game = client_instance.getCurrentGame();
        GameMap map = game.getMap();
        User user = client_instance.getUser();
        String playerId = user.get_playerId();
        Player player = game.getPlayer(playerId);

        if(map.isRouteClaimed(route.getId())){
            game.getTrainDeck().discardCards(discardedCards);
        }

        ClaimRouteRequest request = new ClaimRouteRequest(game.getGameID(), playerId, map, game.getTrainDeck(), player);

        String[] paramTypes = {ClaimRouteRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_claim_route_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }
}
