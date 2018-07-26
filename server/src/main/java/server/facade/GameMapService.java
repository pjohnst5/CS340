package server.facade;

import server.exception.ServerException;
import server.model.ServerModel;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.model.GameAction;
import shared.model.GameMap;
import shared.model.Player;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;
import shared.model.request.ClaimRouteRequest;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

public class GameMapService {

    public static IResponse claimRoute(ClaimRouteRequest request)
    {
        return null;
    }
}
