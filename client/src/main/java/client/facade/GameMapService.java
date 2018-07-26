package client.facade;

import java.util.List;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.enumeration.ClaimedRoutePointSystem;
import shared.enumeration.TrainColor;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.NotEnoughTrainCarsException;
import shared.exception.RouteClaimedAlreadyException;
import shared.exception.UnableToClaimRouteException;
import shared.model.Game;
import shared.model.GameMap;
import shared.model.Player;
import shared.model.Route;
import shared.model.User;
import shared.model.decks.TrainCard;
import shared.model.request.ClaimRouteRequest;

public class GameMapService {
    public void claimRoute(AsyncServerTask.AsyncCaller caller, Route route, List<TrainCard> discardedCards) throws DeckException, InvalidGameException, RouteClaimedAlreadyException, UnableToClaimRouteException {
        ClientModel client_instance = ClientModel.getInstance();

        Game game = client_instance.getCurrentGame();
        GameMap map = game.getMap();
        User user = client_instance.getUser();
        String playerId = user.get_playerId();
        Player player = game.getPlayer(playerId);

        if(!map.isRouteClaimed(route.getId())){                             //ensure the route isn't already claimed
            if (route.get_pathLength() == discardedCards.size()) {          //ensure the route length is the same as the size of discarded cards
                if(areColorsAreCorrect(route, discardedCards)) {            //ensure that the cards are of the right color to claim route
                    //Send the request to the server
                    ClaimRouteRequest request = new ClaimRouteRequest(map, game.getTrainDeck(), player);

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
                else {
                    throw new UnableToClaimRouteException("Incorrect colors used to claim route");
                }
            }
            else {
                throw new UnableToClaimRouteException("Not enough cards to claim the route");
            }
        }
        else {
            throw new RouteClaimedAlreadyException();
        }
    }

    private boolean areColorsAreCorrect(Route route, List<TrainCard> cards){
        TrainColor routeColor = route.get_color();

        if(routeColor != TrainColor.GRAY){
            for(int i = 0; i < cards.size(); i++){
                TrainColor cardColor = cards.get(i).get_color();
                if(cardColor != TrainColor.LOCOMOTIVE && cardColor != routeColor) {
                    return false;
                }
            }
        }
        else {
            TrainColor tempColor = null;
            for(int i = 0; i < cards.size(); i++) {
                TrainColor cardColor = cards.get(i).get_color();
                if (cardColor != TrainColor.LOCOMOTIVE) {
                    if (tempColor == null) {
                        tempColor = cardColor;
                    } else if(cardColor != tempColor) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
