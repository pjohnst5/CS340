package shared.model.request;


public abstract class IServiceRequest {
    private String _gameID;
    private String _playerID;

    public IServiceRequest(String gameID, String playerID){
        _gameID = gameID;
        _playerID = playerID;
    }

    public String get_gameID()
    {
        return _gameID;
    }

    public String get_playerID()
    {
        return _playerID;
    }
}
