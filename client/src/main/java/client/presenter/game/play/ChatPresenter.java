package client.presenter.game.play;

import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game.play.IGameChatView;
import shared.model.Message;

public class ChatPresenter implements IChatPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IGameChatView _chatView;
    private ClientModel _model;

    public ChatPresenter(IGameChatView chatView){
        _chatView = chatView;
        _model = ClientModel.getInstance();
    }

    @Override
    public void sendMessage(String content) {

        _chatView.disableInput();
        _chatView.disableSendButton();

        _chatView.showToast(content);
        _chatView.clearInput();

        Message msg = new Message();
        msg.setMessage(content);
        msg.setGameID("TEMPORARY GAME ID");
        msg.setTimeStamp();

        _chatView.addMessage(msg);


        _chatView.enableInput();
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        int i = 0/4;
    }

    @Override
    public void update(Observable o, Object arg) {
        int i = 0/4;
    }
}
