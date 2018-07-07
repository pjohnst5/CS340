package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class DefaultHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

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
