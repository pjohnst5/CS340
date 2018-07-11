package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import shared.Command.GenericCommand;
import shared.Response.CommandResponse;
import shared.communication.serialization.Serializer;

public class ExecCommandHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        GenericCommand command = (GenericCommand) Serializer.deserializeToObject(reader, GenericCommand.class);

        Object result = command.execute();

        CommandResponse response = new CommandResponse();

        response.setErrorMessage("RAWR");
//        response.setErrorMessage(result.toString());
        response.setSuccess(true);


        reader.close();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        String serializedObject = Serializer._serialize(response);
        sendResponse(serializedObject, exchange);
    }
}
