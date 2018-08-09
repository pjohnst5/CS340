package json.provider;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import shared.model.Game;

public class TestDriver {


    public static void main(String[] args) throws Exception {
        System.out.println("Testing, testing, 1 2 3, testing");

        Game game1 = new Game("Love Lady", 3);
        game1.setGameID(UUID.randomUUID().toString());
        game1.incrementCommandCountSinceSnapshot();
        game1.incrementCommandCountSinceSnapshot();
        game1.incrementCommandCountSinceSnapshot();
        game1.incrementCommandCountSinceSnapshot();

        Game game2 = new Game("Lovely Human", 3);
        game2.setGameID(UUID.randomUUID().toString());

        Plugin plugin = new Plugin();
        plugin.clear();
        plugin.getGameDao().addGame(game1);
        plugin.getGameDao().addGame(game2);

        List<Game> games = plugin.getGameDao().getGames();
        for (Game game : games){
            StringJoiner joiner = new StringJoiner(",", "[", "]")
                    .add(game.getGameName())
                    .add(Integer.toString(game.getCommandCountSinceSnapshot()))
                    .add(game.getGameID());
            System.out.println(joiner.toString());
        }
        System.out.println("Got the user Dao!");
    }
}
