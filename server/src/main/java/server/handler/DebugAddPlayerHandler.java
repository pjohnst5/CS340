package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.enumeration.PlayerColor;
import shared.exception.InvalidGameRequestException;
import shared.model.Game;
import shared.model.request.JoinRequest;
import shared.model.response.IResponse;
import shared.serialization.Serializer;

/**
 * For one-person testing, hit this handler to add a dummy player to your already created game.
 */
public class DebugAddPlayerHandler extends Handler {
    public static final String END_POINT_HANDLE = "/api/add";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("DebugAddPlayerHandler reached");

        String[] params = getPathParts(exchange);

        String gameName = params[3];
        String gameId = null;
        for (Game g : ServerModel.getInstance().getGames().values()) {
            if (g.getGameName().equals(gameName)) {
                gameId = g.getGameID();
                break;
            }
        }

        try {
            if (gameId == null) {
                throw new Exception("can't find game with that name");
            }
            JoinRequest request = new JoinRequest("dummy", "dummy",
                    PlayerColor.YELLOW, gameId);
            IResponse response = ServerFacade.joinGame(request);
            if (!response.getSuccess()) {
                throw new Exception(response.getErrorMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            sendResponse("failed: " + e.getMessage(), exchange);
            return;
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        sendResponse("success", exchange);
    }
}
