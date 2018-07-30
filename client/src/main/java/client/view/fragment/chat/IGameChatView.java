package client.view.fragment.chat;

import shared.model.Message;

public interface IGameChatView {

    public void clearInput();
    public void enableInput();
    public void disableInput();
    public void enableSendButton();
    public void disableSendButton();
    public void showToast(String message);
    public boolean addMessage(Message message);

}
