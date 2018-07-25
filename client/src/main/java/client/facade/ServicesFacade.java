package client.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import shared.enumeration.CityName;
import shared.enumeration.TrainColor;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.NotEnoughTrainCarsException;
import shared.exception.RouteClaimedAlreadyException;
import shared.model.City;
import shared.model.GameAction;
import shared.model.GameMap;
import shared.model.Message;
import shared.model.Player;
import shared.model.Route;
import shared.model.decks.DestCard;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainCard;
import shared.model.decks.TrainDeck;
import shared.model.request.LeaveGameRequest;
import shared.model.wrapper.ThreeDestCardWrapper;

public class ServicesFacade {
    public void claimRoute(AsyncServerTask.AsyncCaller caller, Route route, List<TrainCard> discardCards) throws DeckException, InvalidGameException, RouteClaimedAlreadyException {
        GameMapService gameMapService = new GameMapService();
        try{
            gameMapService.claimRoute(caller, route, discardCards);
        } catch (Exception e) {
            System.out.println("Ryan, please handle this exception");
        }
    }
    public void drawFaceUpCard(AsyncServerTask.AsyncCaller caller, TrainCard trainCard, Player player){
        TrainCardService trainCardService = new TrainCardService();
        trainCardService.drawFaceUpCard(caller, trainCard, player);
    }
    public void drawFaceDownCard(AsyncServerTask.AsyncCaller caller, Player player){
        TrainCardService trainCardService = new TrainCardService();
        trainCardService.drawFaceDownCard(caller, player);
    }
    public void sendMessage(AsyncServerTask.AsyncCaller caller, Message message){
        ChatService service = new ChatService();
        service.sendMessage(caller, message);
    }

    public void sendSetupResults(AsyncServerTask.AsyncCaller caller, ThreeDestCardWrapper keep, ThreeDestCardWrapper discard, Player player){
        new DestCardService().sendSetupResults(caller, keep, discard, player);
    }

    public void selectDestCard(AsyncServerTask.AsyncCaller caller, List<DestCard> destCards, Player player){
        DestCardService service = new DestCardService();
        service.selectDestCard(caller, destCards, player);
    }
    public void discardDestCard(AsyncServerTask.AsyncCaller caller, List<DestCard> destCards, Player player){
        DestCardService service = new DestCardService();
        service.discardDestCard(caller, destCards, player);
    }
    public void leaveGame(AsyncServerTask.AsyncCaller caller, LeaveGameRequest leaveGameRequest){
        GameStatusService service = new GameStatusService();
        service.leaveGame(caller, leaveGameRequest);
    }

    public void requestDestCards(AsyncServerTask.AsyncCaller caller, Player player){
        new DestCardService().requestDestCards(caller, player);
    }

    public void phase2PassoffScenarios() {
        //FIXME: delete after phase 2
        ClientModel clientModel = ClientModel.getInstance();
        Player user;
        try {
            // Scenarios for current user
            user = clientModel.getCurrentGame().getPlayer(clientModel.getUser().get_playerId());
            user.addPoints(10);
            user.addTrainCard(new TrainCard(TrainColor.BLUE));
            user.addDestCard(new DestCard(new City(CityName.NEW_ORLEANS), new City(CityName.LAS_VEGAS), 20));
            clientModel.updatePlayer(user);

            //Scenarios for all other players
            List<Player> otherPlayers = clientModel.getCurrentGame().getPlayers();
            for(int i = 0; i< otherPlayers.size(); i++) {
                Player player = otherPlayers.get(i);
                if(player != user) {
                    player.addTrainCard(new TrainCard(TrainColor.GRAY));
                    player.addTrainCard(new TrainCard(TrainColor.RED));
                    player.getTrainCars().removeCars(10 + i);
                    player.addDestCard(new DestCard(new City(CityName.NEW_ORLEANS), new City(CityName.LAS_VEGAS), 20));
                    clientModel.updatePlayer(player);

                    //add chat message from any player
                    Message message = new Message();
                    message.setPlayer(player);
                    message.setMessage("Hello from " + player.getDisplayName());
                    clientModel.addMessage(message);
//                    Thread.sleep(6000);
                }
            }

            //Scenarios for decks
            TrainDeck trainDeck = clientModel.getCurrentGame().getTrainDeck();
            trainDeck.phase2DrawFaceUp();
            trainDeck.phase2DrawFaceDown();
            clientModel.setTrainDeck(trainDeck);
//            Thread.sleep(6000);

            DestDeck destDeck = clientModel.getCurrentGame().getDestDeck();
            destDeck.getThreeCards();
            clientModel.setDestDeck(destDeck);

            //claiming a route
            GameMap gameMap = clientModel.getCurrentGame().getMap();
            HashMap<UUID, Route> routes = gameMap.get_routes();
            Set<UUID> keys = routes.keySet();
            UUID key = keys.iterator().next();
            Route route = routes.get(key);
            clientModel.claimRoute(route, user);

            //change turns
            clientModel.changeTurns();

        } catch (InvalidGameException | NotEnoughTrainCarsException | DeckException e) {
            e.printStackTrace();
        }
    }
}
