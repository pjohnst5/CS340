package shared.model;

import java.util.Map;

public class GamesWrapper {
    private Map<String, Game> _games;

    public void setGames(Map<String, Game> games) {
        this._games = games;
    }

    public Map<String, Game> getGames() {
        return _games;
    }
}
