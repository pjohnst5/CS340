package client.presenter.game_list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import client.facade.GameListService;
import client.model.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameListPoller;
import client.server.communication.poll.Poller;
import client.view.fragment.game_list.IGameListView;
import client.server.AsyncServerTask;
import shared.enumeration.PlayerColor;
import shared.exception.InvalidGameException;
import shared.exception.InvalidGameRequestException;
import shared.exception.MaxPlayersException;
import shared.exception.PlayerException;
import shared.model.Game;
import shared.model.Player;
import shared.model.User;
import shared.model.request.JoinRequest;

/**
 * The purpose of this class is to control facilitate the data-binding between the ClientModel
 * and the GameListView.
 *
 * @invariant ServerProxy is configured correctly and can properly communicate with the server
 * @invariant GameListPoller.instance() returns a valid instance of GameListPoller
 * @invariant ClientModel.getInstance() returns a valid ClientModel instance
 * @invariant _model is valid ClientModel object
 * @invariant _view != null
 */
public class GameListPresenter implements IGameListPresenter, Observer, AsyncServerTask.AsyncCaller {
    private IGameListView _view;
    private ClientModel _model;

    /**
     * Constructs a new instance of GameListPresenter to manage the provided view
     *
     * @param view IGameListView which the GameListPresenter is supposed to manage
     *
     * @pre view != null
     *
     * @post _view = view
     * @post poller is communicating with server
     */
    public GameListPresenter(IGameListView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);

        Poller _poller = GameListPoller.instance();
        ServerProxy.instance().usePoller(_poller);
    }


    /**
     * Method to be called when the observable ClientModel updates. Called when Game is either
     * created or joined
     *
     * @param observable the observable object
     * @param o an argument passed to the notifyObservers method
     *
     * @pre if _model.getCurrentGame() != null, then _model.containsObserver(this)
     * @pre if _model.getCurrentGame() == null, _model.getGames() != null
     *
     * @post if _model.getCurrentGame() != null, then GameListPoller is stopped
     * @post if _model.getCurrentGame() == null,
     *          then old(_view.getGameList().size()) > _view.getGameList().size()
     */
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ClientModel) {
            Game currentGame = _model.getCurrentGame();
            if (currentGame != null) {
                _model.deleteObserver(this);
                ServerProxy.instance().stopPoller();
                _view.joinGame();
            } else {
                Map<String, Game> gameMap = _model.getGames();
                List<Game> games = new ArrayList(gameMap.values());
                _view.updateGameList(games);
            }
        }
    }

    /**
     * Validates whether a create game request is valid. For a valid request, the gameName
     * and the displayName must not be empty.
     *
     * @param gameName Requested name of the game to be created
     * @param displayName Requested display name of the player
     *
     * @pre _view.showToast(message) functions
     *
     * @return True if the request is valid. False otherwise.
     *
     * @post Returns true if the request is valid. False otherwise.
     *
     */
    private boolean validateCreateGameRequest(String gameName, String displayName){

        if (gameName == null || gameName.equals("")) {
            _view.showToast("Room Name can't be empty");

        } else if (displayName == null || displayName.equals("")){
            _view.showToast("Display name can't be empty");

        } else {
            return true;

        }

        return false;

    }

    /**
     * Make request the GameListService to create new game
     *
     * @param gameName Display name for the room to be created
     * @param displayName Name for the player to be seen by other players in game room
     * @param color Color for the player to be seen by other players in the game room
     * @param maxPlayers Number of players required for the room to be filled
     *
     * @pre gameName.trim().length() > 0
     * @pre displayName.length() > 0
     * @pre color != null
     * @pre 2 <= maxPlayers <= 5
     *
     * @post GameListService creates new game on server
     */
    @Override
    public void createGame(String gameName, String displayName, PlayerColor color, int maxPlayers) {

        if (!validateCreateGameRequest(gameName, displayName)) {
            return;
        }

        Game game = null;
        Player player = null;
        User user = _model.getUser();
        String gameId = UUID.randomUUID().toString();

        try {

            player = new Player(user.getUserName(), displayName, color, gameId, user.getUUID().toString());
            game = new Game(gameName, maxPlayers);
            game.setGameID(gameId);
            game.addPlayer(player);

        } catch (PlayerException e) {
            e.printStackTrace();
            _view.showToast("Invalid player object");
        } catch (InvalidGameException | MaxPlayersException e) {
            e.printStackTrace();
            _view.showToast("Invalid game object");
        }

        GameListService.createGame(this, game);
    }

    /**
     * Makes request to the GameListService to join a game based on the gameId
     *
     * @param displayName Name for the player to be seen by other players in game room
     * @param color Color for the player to be seen by other players in the game room
     * @param gameId The Id of the game to be joined
     *
     * @pre displayName.trim()length() > 0
     * @pre color is not already taken by another user in the same game
     * @pre gameId is id of valid game on the server
     * @pre client not already in game
     *
     * @post _model.getCurrentGame != null
     */
    @Override
    public void joinGame(String displayName, PlayerColor color, String gameId) {
        try {

            String username = _model.getUser().getUserName();
            JoinRequest request = new JoinRequest(username, displayName, color, gameId);
            GameListService.joinGame(this, request);

        } catch (InvalidGameRequestException e) {

            e.printStackTrace();
            _view.showToast("Error joining game");

        }
    }

    /**
     * If the communicating with the server fails, this method displays to the user what went wrong
     *
     * @param exception The exception that occurred in the AsyncServerTask
     *
     * @pre An exception has occurred in relation to an AsyncServerTask
     *
     * @post Message is displayed to user describing what happened based on the exception received
     */
    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showToast(exception.getMessage());
    }
}
