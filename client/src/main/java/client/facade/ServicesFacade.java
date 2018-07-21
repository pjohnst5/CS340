package client.facade;

import java.util.List;

import client.server.AsyncServerTask;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.RouteClaimedAlreadyException;
import shared.model.Message;
import shared.model.Route;
import shared.model.decks.DestCard;
import shared.model.decks.TrainCard;
import shared.model.request.LeaveGameRequest;

public class ServicesFacade {
    public void claimRoute(AsyncServerTask.AsyncCaller caller, Route route, List<TrainCard> discardCards) throws DeckException, InvalidGameException, RouteClaimedAlreadyException {
        GameMapService gameMapService = new GameMapService();
        try{
            gameMapService.claimRoute(caller, route, discardCards);
        } catch (Exception e) {
            System.out.println("Ryan, please handle this exception");
        }
    }
    public void drawFaceUpCard(AsyncServerTask.AsyncCaller caller, TrainCard trainCard){
        TrainCardService trainCardService = new TrainCardService();
        trainCardService.drawFaceUpCard(caller, trainCard);
    }
    public void drawFaceDownCard(AsyncServerTask.AsyncCaller caller){
        TrainCardService trainCardService = new TrainCardService();
        trainCardService.drawFaceDownCard(caller);
    }
    public void sendMessage(AsyncServerTask.AsyncCaller caller, Message message){
        ChatService service = new ChatService();
        service.sendMessage(caller, message);
    }
    public void selectDestCard(AsyncServerTask.AsyncCaller caller, DestCard destCard){
        DestCardService service = new DestCardService();
        service.selectDestCard(caller, destCard);
    }
    public void discardDestCard(AsyncServerTask.AsyncCaller caller, DestCard destCard){
        DestCardService service = new DestCardService();
        service.discardDestCard(caller, destCard);
    }
    public void leaveGame(AsyncServerTask.AsyncCaller caller, LeaveGameRequest leaveGameRequest){
        GameStatusService service = new GameStatusService();
        service.leaveGame(caller, leaveGameRequest);
    }
}
