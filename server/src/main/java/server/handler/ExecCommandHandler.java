package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.GenericResponse;
import shared.communication.serialization.Serializer;

public class ExecCommandHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        GenericCommand command = (GenericCommand) Serializer.deserializeToObject(reader, GenericCommand.class);

        Object result = command.execute();

        GenericResponse response = new GenericResponse();

        response.setErrorMessage(result.toString());
        response.setSuccess(true);


        reader.close();

        String serializedObject = Serializer._serialize(response);
        sendResponse(serializedObject, exchange);
    }
}
