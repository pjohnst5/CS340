package client.presenter.game.play;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game.play.IGameChatView;
import shared.enumeration.PlayerColor;
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

        // TODO: UPDATE send to the server here

        _chatView.disableInput();
        _chatView.disableSendButton();

        _chatView.showToast(content);
        _chatView.clearInput();

        Message msg = new Message();
        msg.setMessage(content);
        //msg.setDisplayName(_model.);
        //msg.setGameID(_model.getCurrentGame().getGameID());
        msg.setTimeStamp();

        new ServicesFacade().sendMessage(this, msg);

        _chatView.addMessage(msg);


        _chatView.enableInput();
    }

    @Override
    public PlayerColor getPlayerColor(String displayName) {
        // TODO: Do the actual logic to get the player color
        // For Now, Generate a random color
        int colorIndex = displayName.length() % 5 + 1;

        switch (colorIndex){
            case 1: return PlayerColor.BLACK;
            case 2: return PlayerColor.BLUE;
            case 3: return PlayerColor.GREEN;
            case 4: return PlayerColor.RED;
            case 5: return PlayerColor.YELLOW;
        }
        return null;
    }

    @Override
    public String getClientDisplayName() {
        // TODO: Implement this to retrieve the display name from the client model
        // For now, generate a username

        Random random = new Random();
        int i = random.nextInt(100);

        if (i % 4 == 2){
            return "Gerald";
        }

        return "";
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
        int i = 0/4;
    }
}
