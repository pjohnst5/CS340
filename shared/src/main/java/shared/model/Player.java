package shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shared.enumeration.PlayerColor;
import shared.exception.PlayerException;
import shared.model.decks.DestCard;
import shared.model.decks.TrainCard;

public class Player {
    private String _userName;
    private String _playerID;
    private String _displayName;
    private String _gameID;
    private PlayerColor _color;
    private List<TrainCard> _trainCards;
    private List<DestCard> _destCards;
    private int _index_of_commands; //i.e if this is -1, the player has recieved no commands, if 0 he has received and executed the first command
    private int _points;


    public Player(String userName, String displayName, PlayerColor color, String gameId, String playerId) throws PlayerException {
        if (userName == null ||
                userName.isEmpty() ||
                displayName == null ||
                displayName.isEmpty() ||
                color == null ||
                gameId == null ||
                gameId.isEmpty() ||
                playerId == null ||
                playerId.isEmpty()) {
            throw new PlayerException("Invalid shared.model.Player parameters");
        }

        _userName = userName;
        _displayName = displayName;
        _gameID = gameId;
        _playerID = playerId;
        _color = color;
        _index_of_commands = -1;
        _points = 0;
        _trainCards = new ArrayList<>();
        _destCards = new ArrayList<>();
    }

    public String getUserName() {
        return _userName;
    }

    public String getDisplayName() {
        return _displayName;
    }

    public String getGameID() {
        return _gameID;
    }

    public PlayerColor getColor() {
        return _color;
    }

    public int getPoints() {
        return _points;
    }

    public String getPlayerID() {
        return _playerID;
    }

    public int getIndex() {
        return _index_of_commands;
    }


    public void addPoints(int add) {
        _points += add;
    }

    public void setGameID(String id) throws PlayerException {
        if (id == null || id.isEmpty()) {
            throw new PlayerException("Player gameID cannot be null or empty");
        }

        _gameID = id;
    }

    public void setPlayerID(String id) throws PlayerException {
        if (id == null || id.isEmpty()) {
            throw new PlayerException("Player ID cannot be null or empty");
        }
    }

    public void setIndex(int i) {
        _index_of_commands = i;
    }

    public void addTrainCard(TrainCard card) {
        _trainCards.add(card);
    }

    public void addDestCard(DestCard card) {
        _destCards.add(card);
    }

    public void removeTrainCard(TrainCard card)
    {
        _trainCards.remove(card);
    }
}
