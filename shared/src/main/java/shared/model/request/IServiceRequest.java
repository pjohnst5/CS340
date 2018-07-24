package shared.model.request;


import shared.model.Player;

public abstract class IServiceRequest {
    private Player _player;

    public IServiceRequest(Player player){
        _player = player;
    }

    public String get_gameID()
    {
        return _player.getGameID();
    }

    public String get_playerID()
    {
        return _player.getPlayerID();
    }

    public Player get_player() { return _player; }
}
