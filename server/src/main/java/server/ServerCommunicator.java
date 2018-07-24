package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import server.exception.ServerException;
import server.handler.DebugAddPlayerHandler;
import server.handler.DefaultHandler;
import server.handler.ExecCommandHandler;
import shared.configuration.ConfigurationManager;

public class ServerCommunicator {

    private int _port;
    private HttpServer _server;

    public ServerCommunicator() throws ServerException {
        this(ConfigurationManager.getInt("port"));
    }

    public ServerCommunicator(int port) throws ServerException{
        this(port, ConfigurationManager.getInt("max_connections"));
    }

    public ServerCommunicator(int port, int maxWaitingConnections) throws ServerException {
        this._port = port;

        try {

            _server = HttpServer.create(new InetSocketAddress(port), maxWaitingConnections);
            _server.setExecutor(null);

        } catch (IOException e) {
            throw new ServerException("Failed to initialize Server object", e);
        }

        setupContext(_server);
    }

    public int start(){
        _server.start();
        return _port;
    }

    public void stop() {
        _server.stop(0);
    }

    public void setupContext(HttpServer server) {
        server.createContext(ConfigurationManager.get("execute_command_endpoint"), new ExecCommandHandler());
        server.createContext(DebugAddPlayerHandler.END_POINT_HANDLE, new DebugAddPlayerHandler());
        server.createContext("/", new DefaultHandler());
    }
}
