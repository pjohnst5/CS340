package client.facade;

import java.util.List;

import client.server.AsyncServerTask;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.RouteClaimedAlreadyException;
import shared.model.Message;
import shared.model.Player;
import shared.model.Route;
import shared.model.decks.DestCard;
import shared.model.decks.TrainCard;
import shared.model.request.DestCardRequest;
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
        trainCardService.drawFaceUpCard(caller, player, trainCard);
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

    public void selectDestCard(AsyncServerTask.AsyncCaller caller, DestCardRequest request){
        DestCardService service = new DestCardService();
        service.selectDestCards(caller, request);
    }

    public void requestDestCards(AsyncServerTask.AsyncCaller caller, Player player){
        new DestCardService().drawThreeDestCards(caller, player);
    }

    public void leaveGame(AsyncServerTask.AsyncCaller caller, LeaveGameRequest leaveGameRequest){
        GameStatusService service = new GameStatusService();
        service.leaveGame(caller, leaveGameRequest);
    }
}
