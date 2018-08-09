package sql.provider.dao;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.exception.DatabaseException;
import shared.model.Game;
import shared.plugin.IGameDao;
import shared.serialization.Serializer;
import sql.provider.DatabaseManager;

public class GameDao implements IGameDao {
    @Override
    public void addGame(Game game) throws DatabaseException {
        if (game == null) {
            throw new DatabaseException("Null reference to Game parameter");
        }
        if (game.getGameID() == null) {
            throw new DatabaseException("Game parameter has null gameId");
        }
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "INSERT INTO games VALUES(?, ?, ?)";
        boolean commit = true;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, game.getGameID());
            pstmt.setInt(2, 0);
            String json = Serializer._serialize(game);
            pstmt.setString(3, json);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DatabaseException("Add game failed", e);
        } finally {
            db.closeConnection(commit);
        }
    }

    @Override
    public void updateGame(Game game) throws DatabaseException {
        if (game == null) {
            throw new DatabaseException("Null reference to Game parameter");
        }
        if (game.getGameID() == null) {
            throw new DatabaseException("Game parameter has null gameId");
        }
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "UPDATE games SET indexOfCompletedCommands = " +
                "indexOfCompletedCommands + ?, jsonData = ? WHERE gameId = ?";
        boolean commit = true;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            int index = game.getCommandCountSinceSnapshot();
            pstmt.setInt(1, index);
            String json = Serializer._serialize(game);
            pstmt.setString(2, json);
            pstmt.setString(3, game.getGameID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DatabaseException("Update game failed", e);
        } finally {
            db.closeConnection(commit);
        }
    }

    @Override
    public List<Game> getGames() throws DatabaseException {
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "SELECT jsonData FROM games";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            ArrayList<Game> games = new ArrayList<>();
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    String json = res.getString("jsonData");
                    Game game = (Game) Serializer._deserialize(new StringReader(json), Game.class);
                    games.add(game);
                }
            } catch (IOException e) {
                throw new DatabaseException("Get games failed", e);
            }
            return games;
        } catch (SQLException e) {
            throw new DatabaseException("Get games failed", e);
        } finally {
            db.closeConnection(false);
        }
    }

    @Override
    public int getIndexOfCompletedCommands(String gameId) throws DatabaseException {
        if (gameId == null) {
            throw new DatabaseException("Null reference to String parameter");
        }
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "SELECT indexOfCompletedCommands FROM games WHERE gameId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, gameId);
            int returnVal = 0;
            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    returnVal = res.getInt("indexOfCompletedCommands");
                }
            }
            return returnVal;
        } catch (SQLException e) {
            throw new DatabaseException("Get indexOfCompletedCommands failed", e);
        } finally {
            db.closeConnection(false);
        }
    }
}
