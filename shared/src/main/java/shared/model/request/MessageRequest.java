package shared.model.request;

import shared.model.Message;

public class MessageRequest extends IServiceRequest {

    private Message _message;

    public MessageRequest(String gameID, String playerID, Message message)
    {
        super(gameID,playerID);
        _message = message;
    }

    public Message get_message()
    {
        return _message;
    }
}
