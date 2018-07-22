package client.presenter.game.play;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game.play.IGameChatView;
import shared.enumeration.PlayerColor;
import shared.exception.InvalidGameException;
import shared.model.Message;
import shared.model.Player;

public class ChatPresenter implements IChatPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IGameChatView _chatView;
    private ClientModel _model;
    private Player _player;
    private ServicesFacade _facade;
    private List<Message> _messages;
    int oldSize;



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
            System.out.println("Game is not initilaized, something went wrong");
            int i = 4 / 0;
        }

        _messages = _model.getCurrentGame().get_messages();
        oldSize = 0;
        _model.addObserver(this);

    }

    @Override
    public void sendMessage(String content) {

        // TODO: UPDATE send to the server here

        _chatView.disableInput();
        _chatView.disableSendButton();

        _chatView.showToast(content);
        _chatView.clearInput();


        Message msg = new Message();
        msg.setMessage(content);
        msg.setPlayer(_player);
        msg.setTimeStamp();

       _facade.sendMessage(this, msg);


        _chatView.enableInput();
    }

    @Override
    public String getClientDisplayName() {
        return _player.getDisplayName();
    }

    @Override
    public void destroy() {
        // TODO: Detach from client model
        int i = 0/4;
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        int i = 0/4;
    }

    @Override
    public void update(Observable o, Object arg) {
        List<Message> messageList = _model.getCurrentGame().get_messages();

        if (oldSize != messageList.size()){
            for (int i = 0; i < messageList.size(); i++){
                if (_chatView.addMessage(messageList.get(i))){
                    oldSize++;

                    if (oldSize == messageList.size()){
                        return;
                    }
                }
            }
        }

//        while (oldSize < messageList.size()) {
//            Message currentMessage = messageList.get(oldSize);
//
//
//            _chatView.addMessage(messageList.get(oldSize));
//            oldSize++;
//        }
//        if (oldSize != messageList.size()){
//
//            oldSize = messageList.size();
//            _chatView.setMessages(messageList);
//        }
    }
}
