package client.facade;

import java.util.List;

import client.server.AsyncServerTask;
import shared.model.Message;
import shared.model.Route;
import shared.model.decks.DestCard;
import shared.model.decks.TrainCard;

public class ServicesFacade {
    public void claimRoute(AsyncServerTask.AsyncCaller caller, Route route, List<TrainCard> discardCards){
        GameMapService gameMapService = new GameMapService();
        try{
            gameMapService.claimRoute(caller, route, discardCards);
        } catch (Exception e)
        {
            System.out.println("Ryan, please handle this exception");
        }
    }
    public void drawFaceUpCard(AsyncServerTask.AsyncCaller caller, TrainCard trainCard){}
    public void drawFaceDownCard(AsyncServerTask.AsyncCaller caller){}
    public void sendMessage(AsyncServerTask.AsyncCaller caller, Message message){}
    public void selectDestCard(AsyncServerTask.AsyncCaller caller, DestCard destCard){}
    public void discardDestCard(AsyncServerTask.AsyncCaller caller, DestCard destCard){}
    public void leaveGame(AsyncServerTask.AsyncCaller caller){}
}
