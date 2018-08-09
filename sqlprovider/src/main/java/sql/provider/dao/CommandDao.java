package sql.provider.dao;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.plugin.ICommandDao;
import shared.serialization.Serializer;
import sql.provider.DatabaseManager;

public class CommandDao implements ICommandDao {
    @Override
    public void storeCommand(String gameID, int index, ICommand command) throws DatabaseException {
        if (gameID == null) {
            throw new DatabaseException("Null reference for gameID parameter");
        }
        if (index <= 0) {
            throw new DatabaseException("Invalid command index parameter");
        }
        if (command == null) {
            throw new DatabaseException("Null reference for command parameter");
        }
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "INSERT INTO commands VALUES(?, ?, ?)";
        boolean commit = true;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, gameID);
            pstmt.setInt(2, index);
            String json = Serializer._serialize(command);
            pstmt.setString(3, json);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DatabaseException("Add command failed", e);
        } finally {
            db.closeConnection(commit);
        }
    }

    @Override
    public List<ICommand> getCommands(String gameID) throws DatabaseException {
        if (gameID == null) {
            throw new DatabaseException("Null reference for gameID parameter");
        }
        DatabaseManager db = new DatabaseManager();
        Connection conn = db.openConnection();
        String sqlString = "SELECT jsonData FROM commands WHERE gameId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, gameID);
            ArrayList<ICommand> commands = new ArrayList<>();
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    String json = res.getString("jsonData");
                    ICommand command = (ICommand) Serializer._deserialize(new StringReader(json),
                            ICommand.class);
                    commands.add(command);
                }
            } catch (IOException e) {
                throw new DatabaseException("Get commands failed", e);
            }
            return commands;
        } catch (SQLException e) {
            throw new DatabaseException("Get commands failed", e);
        } finally {
            db.closeConnection(false);
        }
    }
}
