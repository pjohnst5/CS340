package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import shared.command.GenericCommand;
import shared.model.response.CommandResponse;
import shared.serialization.Serializer;

public class ExecCommandHandler extends Handler {

    private static final int MAX_CHAR_PER_LINE = 125;

    private String prev;
    private int charWrapCount;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        GenericCommand command = (GenericCommand) Serializer.deserializeToObject(reader, GenericCommand.class);

        String current = command.get_className() + "." + command.get_methodName();

        if (current.equals(prev)){
            charWrapCount++;
            if (charWrapCount >= MAX_CHAR_PER_LINE){
                charWrapCount = 0;
                System.out.println();
            }
            System.out.print(".");
        } else {
            System.out.print("\n" + current);
            charWrapCount = current.length()-1;
        }

        prev = current;

        CommandResponse result = (CommandResponse) command.execute();
        reader.close();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        String serializedObject = Serializer._serialize(result);
        sendResponse(serializedObject, exchange);
    }
}
