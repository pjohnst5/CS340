package client.server.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;
import shared.serialization.Serializer;
import shared.configuration.ConfigurationManager;

public class ClientCommunicator {

    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static final int MAX_CONNECTION_TIME = 10000; // 10 seconds

    private final String EXEC_ENDPOINT;


    private ClientCommunicator() {
        EXEC_ENDPOINT = ConfigurationManager.get("execute_command_endpoint");
    }
    private static ClientCommunicator _instance;

    private String getHostUrl() {
        String SERVER_PORT = ConfigurationManager.get("port");
        String SERVER_HOST = ConfigurationManager.get("server_host");
        String SERVER_PROTOCOL = ConfigurationManager.get("protocol");
        String hostUrl = SERVER_PROTOCOL + "://" + SERVER_HOST + ":" + SERVER_PORT;
        return hostUrl;
    }

    public static ClientCommunicator instance() {

        if (_instance == null){
            _instance = new ClientCommunicator();
        }

        return _instance;
    }

    private IResponse getResponse(HttpURLConnection connection){
        CommandResponse result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                if (connection.getContentLength() == 0){
                    System.err.println("Response body was empty");
                } else if (connection.getContentLength() == -1){
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    result = (CommandResponse)Serializer._deserialize(reader, CommandResponse.class);
                    reader.close();
                }
            } else {
                System.err.println("Something went wrong");
                throw new IOException(String.format("http code %d", connection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public HttpURLConnection makeRequest(ICommand command) {
        return makeRequest(EXEC_ENDPOINT, command);
    }

    public HttpURLConnection makeRequest(String endpoint, ICommand command){

        Writer writer;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(getHostUrl() + endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);
            if (!Boolean.parseBoolean(ConfigurationManager.getString("debug_mode"))) {
                connection.setReadTimeout(MAX_CONNECTION_TIME);
                connection.setConnectTimeout(MAX_CONNECTION_TIME);
            }

            writer = new PrintWriter(connection.getOutputStream());
            Serializer.serializeToWriter(writer, command);
            writer.close();
            connection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static IResponse sendCommand(ICommand command){

        HttpURLConnection connection = instance().makeRequest(command);
        return instance().getResponse(connection);
    }

    public static void main(String[] args){

        String[] paramTypes = {
                String.class.getCanonicalName(),
                int.class.getCanonicalName(),
                int.class.getCanonicalName()
        };

        Object[] paramValues = { "Dallas", 4, 5 };

        GenericCommand command = new GenericCommand("server.ServerFacade.ServerFacade",
                                                    "TestCommand",
                                                    paramTypes,
                                                    paramValues,
                                                    null);

        sendCommand(command);
    }


}
