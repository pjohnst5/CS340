package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ExecCommandHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO: Finish implementing this, need to add ICommand Dependency
        //ICommand Command
        String path = getPath(exchange);

        String message = "{\n" +
                "  success: true,\n" +
                "  data: 'Command Server API'\n" +
                "}";

        byte[] result = message.getBytes();

        switch (path) {
            case "/":
            default:
                System.out.println("Endpoint Accessed: /");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                break;
        }

        sendResponse(message, exchange);
    }
}
