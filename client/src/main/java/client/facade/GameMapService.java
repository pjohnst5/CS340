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
    private ClientModel client_instance = ClientModel.getInstance();

    private Game game = client_instance.getCurrentGame();
    private GameMap map = game.getMap();
    private User user = client_instance.getUser();
    private String playerId = user.get_playerId();
    private Player player = game.getPlayer(playerId);

    public GameMapService() throws InvalidGameException {
    }

    public void claimRoute(AsyncServerTask.AsyncCaller caller, Route route, List<TrainCard> discardedCards) throws UnableToClaimRouteException {
        if(canBeClaimed(route, discardedCards)){
            //Send the request to the server
            ClaimRouteRequest request = new ClaimRouteRequest(route, discardedCards, player);

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
            throw new UnableToClaimRouteException("There was an error when trying to claim the route");
        }
    }

    public boolean canBeClaimed(Route route, List<TrainCard> discardedCards) {
        //check if the route isn't already claimed
        //check if the route length is the same as the size of discarded cards
        //check if that the cards are of the right color to claim route
        return !isRouteClaimed(route) && areLengthsTheSame(route, discardedCards) && areColorsAreCorrect(route, discardedCards);
    }

    public boolean isRouteClaimed(Route route){
        return map.isRouteClaimed(route.getId());
    }

    public boolean areLengthsTheSame(Route route, List<TrainCard> discardedCards){
        return route.get_pathLength() == discardedCards.size();
    }

    public boolean areColorsAreCorrect(Route route, List<TrainCard> cards){
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
