package client.facade;

import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.request.DestCardRequest;
import shared.model.wrapper.ThreeDestCardWrapper;

public class DestCardService {
    public void sendSetupResults(AsyncServerTask.AsyncCaller caller,
                                 ThreeDestCardWrapper keep,
                                 ThreeDestCardWrapper discard,
                                 Player player){

        DestCardRequest request = new DestCardRequest(player, keep, discard);

        for (DestCard card : keep.getDestCards()){
            player.addDestCard(card);
        }

        String[] paramTypes = { request.getClass().getCanonicalName() };
        Object[] paramValues = { request };

        GenericCommand command = new GenericCommand(
                ConfigurationManager.get("server_facade_name"),
                ConfigurationManager.get("server_send_setup_results"),
                paramTypes,
                paramValues,
                null
        );

        new AsyncServerTask(caller).execute(command);

    }

    public void selectDestCards(AsyncServerTask.AsyncCaller caller, DestCardRequest request){
        String[] paramTypes = {DestCardRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_draw_dest_cards_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }

    /**
     * Request 3 destination cards from the server to begin the game
     * @param caller The caller of this method
     */
    public void drawThreeDestCards(AsyncServerTask.AsyncCaller caller, Player player){

        String[] paramTypes = { player.getClass().getCanonicalName() };
        Object[] paramValues = { player };

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_request_three_dest_cards"),
                paramTypes,
                paramValues,
                null
        );

        new AsyncServerTask(caller).execute(command);
    }
}
