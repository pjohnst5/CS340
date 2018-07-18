package shared.model.request;


public abstract class IServiceRequest {
    private String _gameID;
    private String _playerID;

    public IServiceRequest(String gameID, String playerID){
        _gameID = gameID;
        _playerID = playerID;
    }

    String get_gameID()
    {
        return _gameID;
    }

    String get_playerID()
    {
        return _playerID;
    }

    void set_gameID(String gameID)
    {
        _gameID = gameID;
    }

    void set_playerID(String playeriD)
    {
        _playerID = playeriD;
    }
}
