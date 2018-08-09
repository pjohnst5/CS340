package sql.provider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        return null; // FIXME: implement; need to return index
    }
}
