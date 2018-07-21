package client.facade;

import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.model.Message;
import shared.model.request.MessageRequest;

public class ChatService {

    public void sendMessage(AsyncServerTask.AsyncCaller caller, Message message){
        MessageRequest request = new MessageRequest(message.getGameID(), message.getDisplayName(), message);

        String[] paramTypes = {MessageRequest.class.getCanonicalName()};
        Object[] paramValues = {request};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_send_message_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }
}
