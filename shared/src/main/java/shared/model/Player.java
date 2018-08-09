package shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;
import shared.exception.NotEnoughTrainCarsException;
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
    private TrainCars _trainCars;
    private int _index_of_commands; //i.e if this is -1, the player has received no commands, if 0 he has received and executed the first command
    private int _points;
    private int _cardsDrawnThisTurn;
    private boolean _gameOver;


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
        _trainCars = new TrainCars();
        _trainCards = new ArrayList<>();
        _destCards = new ArrayList<>();
        _cardsDrawnThisTurn = 0;
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
        _playerID = id;
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

    private void removeTrainCard(TrainCard card)
    {
        for (int i = 0; i < _trainCards.size(); i++){
            if (_trainCards.get(i).get_color() == card.get_color()){
                _trainCards.remove(i);
                return;
            }
        }
    }

    public void removeTrainCards(List<TrainCard> cards) {
        for (int i = 0; i < cards.size(); i++){
            removeTrainCard(cards.get(i));
        }
    }

    public TrainCars getTrainCars() {
        return _trainCars;
    }
    public int getNumTrainCards(){
        return _trainCards.size();
    }
    public List<TrainCard> getTrainCards() {
        return _trainCards;
    }

    public List<DestCard> getDestCards() {
        return _destCards;
    }

    public Map<TrainColor, Integer> countNumTrainCards() {
        Map<TrainColor, Integer> counts = new HashMap<>();
        for (TrainCard c : _trainCards) {
            TrainColor color = c.get_color();
            if (!counts.containsKey(color)) {
                counts.put(color, 0);
            }
            int prevCount = counts.get(color);
            prevCount++;
            counts.put(color, prevCount);
        }
        return counts;
    }

    public List<TrainCard> getCardsFromCounts(Map<TrainColor, Integer> counts) {
        // NOTE: method does not remove cards from player's hand; it just returns a list of pointers to card objects
        List<TrainCard> cards = new ArrayList<>();
        Map<TrainColor, Integer> cardCounts = new HashMap<>(counts);
        for (Map.Entry<TrainColor, Integer> colorCount : cardCounts.entrySet()) {
            Iterator<TrainCard> it = _trainCards.iterator();
            while (colorCount.getValue() > 0) {
                if (it.hasNext()) {
                    TrainCard curCard = it.next();
                    if (curCard.get_color() == colorCount.getKey()) {
                        cards.add(curCard);
                        int newVal = colorCount.getValue() - 1;
                        colorCount.setValue(newVal);
                    }
                } else {
                    // player doesn't have the train cards to match the request
                    return null;
                }
            }
        }
        return cards;
    }

    public void removeTrains(int num) throws NotEnoughTrainCarsException {
        _trainCars.removeCars(num);
    }

    public int get_cardsDrawnThisTurn() {
        return _cardsDrawnThisTurn;
    }

    public void incrementCardsDrawnThisTurn(){
        _cardsDrawnThisTurn++;
    }

    public void resetCardsDrawnThisTurn() {
        _cardsDrawnThisTurn = 0;
    }

    public void updateDestCards(List<DestCard> updated) {
        _destCards = updated;
    }

    public void gameOver() {
        // game state has been set to FINISHED; calculate points
        if (_gameOver) return;
        for (DestCard card : _destCards) {
            if (card.getCompleted()) {
                _points += card.get_worth();
            } else {
                _points -= card.get_worth();
            }
        }
        _gameOver = true; // set so we don't accidentally calculate twice
    }

    public int getNumCompletedDestCards() {
        int completed = 0;
        for (DestCard card : _destCards) {
            if (card.getCompleted()) {
                completed++;
            }
        }
        return completed;
    }
}
