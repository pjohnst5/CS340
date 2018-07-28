package client.facade;

import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.model.Player;
import shared.model.decks.TrainCard;
import shared.model.request.FaceDownRequest;
import shared.model.request.FaceUpRequest;

public class TrainCardService {
    public void drawFaceUpCard(AsyncServerTask.AsyncCaller caller, Player player, TrainCard faceUpCard){
        FaceUpRequest request = new FaceUpRequest(player, faceUpCard);

        String[] paramTypes = {FaceUpRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_draw_face_up_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }

    public void drawFaceDownCard(AsyncServerTask.AsyncCaller caller, Player player){
        FaceDownRequest request = new FaceDownRequest(player);

        String[] paramTypes = {FaceDownRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_draw_face_down_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }
}
