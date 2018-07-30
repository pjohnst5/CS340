package shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shared.enumeration.GameState;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.MaxPlayersException;
import shared.model.decks.DestCard;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainCard;
import shared.model.decks.TrainDeck;

public class Game {
    private String _gameName;
    private String _gameID;
    private List<Player> _players;
    private int _maxPlayers;
    private GameState _state;
    private List<Message> _messages;
    private List<GameAction> _actions;
    private TrainDeck _trainDeck;
    private DestDeck _destDeck;
    private GameMap _map;
    private TurnManager _turnManager;
    private int _playersSetup;
    private String _playerToEndOn;
    private boolean _gameOver;

    private List<DestCard>_destOptionCards;


    public Game(String gameName, int maxPlayers) throws InvalidGameException {
        if (maxPlayers < 2 || maxPlayers > 5) {
            throw new InvalidGameException("Invalid number of players : " + maxPlayers);
        }
        if (gameName.isEmpty() || gameName == null) {
            throw new InvalidGameException("shared.model.Game must have a name");
        }

        _gameName = gameName;
        _gameID = new String();
        _players = new ArrayList<>();
        _maxPlayers = maxPlayers;
        _state = GameState.NOT_READY;
        _messages = new ArrayList<>();
        _actions = new ArrayList<>();
        _trainDeck = new TrainDeck();
        _destDeck = new DestDeck();
        _map = new GameMap();
        _destOptionCards = null;
        _playersSetup = 0;
        _playerToEndOn = new String();
        _gameOver = false;
    }

    public void setDestOptionCards(List<DestCard> cards){
        _destOptionCards = cards;
    }

    public List<DestCard> getDestOptionCards(){
        return _destOptionCards;
    }

    public boolean destOptionCardsEmpty(){
        return _destOptionCards == null;
    }

    public String getGameName() {
        return _gameName;
    }

    public String getGameID() {
        return _gameID;
    }

    public List<Player> getPlayers() {
        return _players;
    }

    public int playerCompletedSetup(){
        _playersSetup++;
        return _playersSetup;
    }

    public int getNumPlayersCompletedSetup(){
        return _playersSetup;
    }

    /**
     * Checks if there is a player with displayName in the current game object.
     * @param displayName the display name of the player to be validated
     * @return Returns true id there is a player with the specified display name in
     *         the current game object. Returns false otherwise.
     */
    public boolean playerDisplayNameExists(String displayName){
        for (Player p : _players) {
            if (p.getDisplayName().equals(displayName)){
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves the player in the current game object associated with the provided display name.
     * @param displayName the display name of the target player
     * @return Returns the player in the current game object associated with the provided display
     *         name if it exists. Returns null otherwise.
     * @throws InvalidGameException
     */
    public Player getPlayerByDisplayName(String displayName) {
        for (Player p : _players) {
            if (p.getDisplayName().equals(displayName)){
                return p;
            }
        }

        return null;
    }

    public Player getPlayer(String playerId) throws InvalidGameException {
        for (Player p : _players) {
            if (p.getPlayerID().equals(playerId)) {
                return p;
            }
        }
        throw new InvalidGameException("Can't find player with that ID in the game");
    }

    public int getMaxPlayers() {
        return _maxPlayers;
    }

    public GameState get_state() {
        return _state;
    }

    //This should only be done internally
//    public void set_state(GameState state){
//        _state = state;
//    }


    //returns the number of players after adding the player, otherwise throws an exception
    public int addPlayer(Player p) throws MaxPlayersException {
        if (_players.size() >= _maxPlayers) {
            throw new MaxPlayersException(_maxPlayers);
        }

        for (int i = 0; i < _players.size(); i++) {
            if (_players.get(i).getPlayerID().equals(p.getPlayerID())) {
                return _players.size();
            }
        }

        _players.add(p);

        if (_players.size() == _maxPlayers) {
            _state = GameState.READY;
        }
        return _players.size();
    }

    //returns the number of players after removing the player
    public int removePlayer(String playerId) throws InvalidGameException {
        for (int i = 0; i < _players.size(); i++) {
            if (_players.get(i).getPlayerID().equals(playerId)) {
                _players.remove(i);
                return _players.size();
            }
        }
        throw new InvalidGameException("Couldn't find a player with that ID in this game");
    }

    public void updatePlayer(Player player) throws InvalidGameException {
        for (int i = 0; i < _players.size(); i++) {
            if (_players.get(i).getPlayerID().equals(player.getPlayerID())) {
                _players.remove(i);
                _players.add(i, player);
                return;
            }
        }
        throw new InvalidGameException("no player with that id to update");
    }

    public void setGameID(String s) throws InvalidGameException {
        if (s == null || s.isEmpty()) {
            throw new InvalidGameException("GameID cannot be null");
        }

        _gameID = s;
    }

    public List<Message> get_messages() {
        return _messages;
    }

    public void addMessage(Message message) {
        this._messages.add(message);
    }

    public void addGameAction(GameAction action) {
        _actions.add(action);
    }

    public List<GameAction> getGameActions(){
        return this._actions;
    }

    public void start() throws InvalidGameException {
        if (_state != GameState.READY) {
            throw new InvalidGameException("Game not ready, can't start");
        }
        _state = GameState.SETUP;

        //Sets up player Order
        _turnManager = new TurnManager(this._players);

        //Deals 4 cards to each player
        try {
            for (int i = 0; i < _players.size(); i++) {
                for (int j = 0; j < 4; j++){
                    _players.get(i).addTrainCard(_trainDeck.drawFaceDownCard());
                }
            }
        } catch (DeckException e) {
            System.out.println("Failed to deal out cards at beginning of game");
        }

    }

    public void setupComplete() {

        if (_state == GameState.SETUP)
            _state = GameState.STARTED;
    }

    public void setMap(GameMap map) {
        _map = map;
    }

    public void setTrainDeck(TrainDeck deck) {
        _trainDeck = deck;
    }

    public void setDestDeck(DestDeck deck) {
        _destDeck = deck;
    }

    public TrainDeck getTrainDeck() {
        return _trainDeck;
    }

    public DestDeck getDestDeck() {
        return _destDeck;
    }

    public GameMap getMap() {
        return _map;
    }

    public String playerTurn() throws InvalidGameException
    {
        if (_turnManager == null)
        {
            throw new InvalidGameException("Game hasn't started yet");
        }
        return _turnManager.getCurrentPlayer();
    }

    public void changeTurns() throws InvalidGameException
    {
        if (_turnManager == null)
        {
            throw new InvalidGameException("Game hasn't started yet");
        }

        int indexOfPersonWhosTurnIsEnding = -1;

        for (int i = 0; i < _players.size(); i++){
            if (_players.get(i).getPlayerID().equals(_turnManager.getCurrentPlayer())) {
                indexOfPersonWhosTurnIsEnding = i;
                break;
            }
        }

        //Case where someone triggers last round by having 2 or less train cards
        if (_state != GameState.LAST_ROUND && _players.get(indexOfPersonWhosTurnIsEnding).getTrainCars().isFinalRound()) {
            _state = GameState.LAST_ROUND;
            _playerToEndOn = _players.get(indexOfPersonWhosTurnIsEnding).getPlayerID();
        } else if (_state == GameState.LAST_ROUND && _turnManager.getCurrentPlayer().equals(_playerToEndOn)) { //case where the person who triggered the last round just finished their turn
            _state = GameState.FINISHED;
            return;
        }


        _turnManager.changeTurns();
        for (int i = 0; i < _players.size(); i++){
            _players.get(i).resetCardsDrawnThisTurn();
        }

    }

    public int getNumClaimedRoutes(String playerId) {
        int count = 0;
        for (Route r : _map.get_routes().values()) {
            if (r.isClaimed()) {
                if (r.get_claimedBy().equals(playerId)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void claimRoute(Route route, Player player) throws InvalidGameException {
        if (_map.isRouteClaimed(route.getId())) {
            throw new InvalidGameException("Can't claim route, it's already claimed");
        }
        _map.claimRoute(route.getId(), player.getPlayerID(), player.getColor());
    }

    public boolean getGameOver() {
        return _gameOver;
    }

}
