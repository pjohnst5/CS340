package client.server.task;

public class StartGameTask {
    private String _gameId;
    private String _playerId;

    public String get_gameId() {
        return _gameId;
    }
    public String get_playerId()
    {
        return _playerId;
    }

    public void set_gameId(String _gameId) {
        this._gameId = _gameId;
    }
    public void set_playerId(String _playerId)
    {
        this._playerId = _playerId;
    }
}
