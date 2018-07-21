package shared.model;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    private List<String> _playerOrder;
    private String _currentPlayer;

    public TurnManager(List<Player> players)
    {
        _playerOrder = new ArrayList<>();
        for (int i = 0; i < players.size(); i++)
        {
            _playerOrder.add(players.get(i).getPlayerID());
        }
        _currentPlayer = _playerOrder.get(0);
    }

    public String getCurrentPlayer()
    {
        return _currentPlayer;
    }

    public void changeTurns()
    {
        int index = _playerOrder.indexOf(_currentPlayer);

        //If it's the last person
        if (index == (_playerOrder.size() - 1))
        {
            _currentPlayer = _playerOrder.get(0);
        } else {
            _currentPlayer = _playerOrder.get(index + 1);
        }
    }

}
