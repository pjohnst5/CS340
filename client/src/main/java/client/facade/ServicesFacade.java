package client.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game_status.IGameStatusView;
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

    public void phase2PassoffScenarios(IGameStatusView view) {
        //FIXME: delete after phase 2
        ClientModel clientModel = ClientModel.getInstance();
        Player user;
        try {
            // Scenarios for current user
            user = clientModel.getCurrentGame().getPlayer(clientModel.getUser().get_playerId());
            user.addPoints(10);
            view.showToast("Current user got 10 points");
            user.addTrainCard(new TrainCard(TrainColor.BLUE));
            view.showToast("Current user got a blue train card");
            user.addDestCard(new DestCard(new City(CityName.NEW_ORLEANS), new City(CityName.LAS_VEGAS), 20));
            view.showToast("Current user got a new destination card");
            clientModel.updatePlayer(user);
            view.showToast("Current user updated");

            //Scenarios for all other players
            List<Player> otherPlayers = clientModel.getCurrentGame().getPlayers();
            for(int i = 0; i< otherPlayers.size(); i++) {
                Player player = otherPlayers.get(i);
                if(player != user) {
                    player.addTrainCard(new TrainCard(TrainColor.GRAY));
                    view.showToast(player.getDisplayName() + " got a gray train card");
                    player.addTrainCard(new TrainCard(TrainColor.RED));
                    view.showToast(player.getDisplayName() + " got a Red train card");
                    int num = 10 + i;
                    player.getTrainCars().removeCars(num);
                    view.showToast(player.getDisplayName() + " lost " + num + " train cars");
                    player.addDestCard(new DestCard(new City(CityName.NEW_ORLEANS), new City(CityName.LAS_VEGAS), 20));
                    view.showToast(player.getDisplayName() + " got a destination card");
                    clientModel.updatePlayer(player);
                    view.showToast(player.getDisplayName() + " updated");

                    //add chat message from any player
                    Message message = new Message();
                    message.setPlayer(player);
                    message.setMessage("Hello from " + player.getDisplayName());
                    clientModel.addMessage(message);
                    view.showToast(player.getDisplayName() + " sent a new message");
                }
            }

            //Scenarios for decks
            TrainDeck trainDeck = clientModel.getCurrentGame().getTrainDeck();
            trainDeck.phase2DrawFaceUp();
            view.showToast("Draw from face up deck");
            trainDeck.phase2DrawFaceDown();
            view.showToast("Draw from face down deck");
            clientModel.setTrainDeck(trainDeck);
            view.showToast("Train deck updated");


            DestDeck destDeck = clientModel.getCurrentGame().getDestDeck();
            destDeck.getThreeCards();
            view.showToast("Draw 3 from destination card deck");
            clientModel.setDestDeck(destDeck);
            view.showToast("Destination card deck updated");

            //claiming a route
            GameMap gameMap = clientModel.getCurrentGame().getMap();
            HashMap<UUID, Route> routes = gameMap.get_routes();
            Set<UUID> keys = routes.keySet();
            UUID key = keys.iterator().next();
            Route route = routes.get(key);
            clientModel.claimRoute(route, user);
            try {
                user.getTrainCars().removeCars(route.get_pathLength());
            } catch (NotEnoughTrainCarsException e) {
                e.printStackTrace();
            }
            view.showToast("New route claimed by user");

            //change turns
            clientModel.changeTurns();
            view.showToast("Change turns");

        } catch (InvalidGameException | NotEnoughTrainCarsException | DeckException e) {
            e.printStackTrace();
        }
    }
     private void updateUser(Player user, IGameStatusView view, ClientModel clientModel){

     }

}
