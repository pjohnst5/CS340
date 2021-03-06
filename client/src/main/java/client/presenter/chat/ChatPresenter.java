package client.presenter.chat;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.chat.IGameChatView;
import shared.exception.InvalidGameException;
import shared.model.Message;
import shared.model.Player;

public class ChatPresenter implements IChatPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IGameChatView _chatView;
    private ClientModel _model;
    private Player _player;
    private ServicesFacade _facade;
    private int _oldSize;



    public ChatPresenter(IGameChatView chatView){
        _chatView = chatView;
        _model = ClientModel.getInstance();
        _facade = new ServicesFacade();

        if (_model.getCurrentGame() == null) {
            return;
        }
        try {
            _player = _model.getCurrentGame().getPlayer(_model.getUser().get_playerId());
        } catch (InvalidGameException e) {
            e.printStackTrace();
            System.exit(1);
        }

        _oldSize = 0;
        update();

    }

    @Override
    public void sendMessage(String content) {

        _chatView.disableInput();
        _chatView.disableSendButton();
        _chatView.clearInput();

        Message msg = new Message();
        msg.setMessage(content);
        msg.setPlayer(_player);

        _facade.sendMessage(this, msg);
        _chatView.enableInput();
    }

    @Override
    public String getClientDisplayName() {
        return _player.getDisplayName();
    }

    @Override
    public void pause() {
        _model.deleteObserver(this);
    }

    @Override
    public void resume() {
        _model.addObserver(this);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _chatView.showToast(exception.getMessage());
    }

    @Override
    public void onServerResponseComplete() {

    }

    private void update(){
        List<Message> messageList = _model.getCurrentGame().get_messages();

        if (_oldSize != messageList.size()){

            // Because the list is growing, it is actually a improves performance
            // to start at the end of messageList
            for (int i = messageList.size() - 1; i >= 0; i--){
                if (_chatView.addMessage(messageList.get(i))){
                    _oldSize++;

                    if (_oldSize == messageList.size()){
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        update();
    }
}
