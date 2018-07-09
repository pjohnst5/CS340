package client.server.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.GenericResponse;
import shared.communication.serialization.Serializer;
import shared.configuration.ConfigurationManager;

public class ClientCommunicator {

    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";

    private static final String SERVER_HOST = ConfigurationManager.get("server_host");
    private static final String SERVER_PORT = ConfigurationManager.get("port");
    private static final String SERVER_PROTOCOL = ConfigurationManager.get("protocol");
    private static final String HOST_URL = SERVER_PROTOCOL + "://" + SERVER_HOST + ":" + SERVER_PORT;

    private static final String EXEC_ENDPOINT = ConfigurationManager.get("execute_command_endpoint");


    private ClientCommunicator() {}
    private static ClientCommunicator _instance = new ClientCommunicator();

    public static ClientCommunicator instance() {
        return _instance;
    }

    private GenericResponse getResponse(HttpURLConnection connection){
        GenericResponse result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                if (connection.getContentLength() == 0){
                    System.err.println("Response body was empty");
                } else if (connection.getContentLength() == -1){
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    result = (GenericResponse)Serializer._deserialize(reader, GenericResponse.class);
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

        HttpURLConnection connection = null;
        Writer writer = null;

        try {
            URL url = new URL(HOST_URL + endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.setDoOutput(true);

            writer = new PrintWriter(connection.getOutputStream());
            Serializer.serializeToWriter(writer, command);
            writer.close();
            connection.connect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static GenericResponse sendCommand(ICommand command){

        HttpURLConnection connection = _instance.makeRequest(command);
        return _instance.getResponse(connection);
    }

    public static void main(String[] args){

        String[] paramTypes = {
                String.class.getCanonicalName(),
                int.class.getCanonicalName(),
                int.class.getCanonicalName()
        };

        Object[] paramValues = { "Dallas", 4, 5 };

        GenericCommand command = new GenericCommand("server.CommandManager",
                                                    "TestCommand",
                                                    paramTypes,
                                                    paramValues,
                                                    null);

        sendCommand(command);

        System.out.println("WHAT");
    }


}
