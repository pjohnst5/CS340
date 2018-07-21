package shared.model.request;

import shared.model.Message;

public class MessageRequest extends IServiceRequest {

    private Message _message;

    public MessageRequest(Message message)
    {
        super(message.getPlayer());
        _message = message;
    }

    public Message get_message()
    {
        return _message;
    }
}
