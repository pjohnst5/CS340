package shared.model.wrapper;

import java.util.Map;

import shared.model.Game;

public class GamesWrapper {
    private Map<String, Game> _games;

    public void setGames(Map<String, Game> games) {
        this._games = games;
    }

    public Map<String, Game> getGames() {
        return _games;
    }
}
