package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import shared.command.GenericCommand;
import shared.model.response.CommandResponse;
import shared.serialization.Serializer;

public class ExecCommandHandler extends Handler {

    private boolean commandListRequest;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        GenericCommand command = (GenericCommand) Serializer.deserializeToObject(reader, GenericCommand.class);

        if (command.get_methodName().equals("getCommandList")){
            if (commandListRequest) {
                System.out.print(".");
            } else {
                commandListRequest = true;
                System.out.print(command.get_className() + "." + command.get_methodName());
            }
        } else {
            if (commandListRequest) System.out.println();
            commandListRequest = false;
            System.out.println(command.get_className() + "." + command.get_methodName());
        }

        CommandResponse result = (CommandResponse) command.execute();
        reader.close();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        String serializedObject = Serializer._serialize(result);
        sendResponse(serializedObject, exchange);
    }
}
