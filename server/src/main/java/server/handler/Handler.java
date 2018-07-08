package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * This class unifies the handler objects that are in the project
 */
public abstract class Handler implements HttpHandler{

    protected String getPath(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        return uri.getPath();
    }

    protected String[] getPathParts(HttpExchange exchange) {
        return getPathParts(getPath(exchange));
    }

    protected String[] getPathParts(String path){
        return path.split("/");
    }

    protected void sendResponse(String message, HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

}
