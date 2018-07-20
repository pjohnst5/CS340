import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import client.model.ClientModel;
import client.server.communication.ClientCommunicator;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameListPoller;
import client.server.communication.poll.GameLobbyPoller;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.enumeration.PlayerColor;
import shared.exception.InvalidGameException;
import shared.model.Game;
import shared.model.GameMap;
import shared.model.Player;
import shared.model.User;
import shared.model.request.JoinRequest;
import shared.serialization.Serializer;



public class Main {

    private static void DallasTimerTest(){

        GameListPoller.instance().start();
        GameLobbyPoller.instance().start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameListPoller.instance().stop();
            }
        }, 8000);

    }


    private static class TestObj {
        public int a;
        public String b;

        @Override
        public String toString(){
            return "(" + Integer.toString(a) + "," + b + ")";
        }
    }

    private static void DallasSerializerTest(){
        try {

            TestObj testObj = new TestObj();
            testObj.a = 1;
            testObj.b = "HELLO WORLD";

            String result = Serializer._serialize(testObj);
            System.out.println(result);

            byte[] bytes = Serializer.serializeToBytes(testObj);
            System.out.println("Bytes successfully Converted");

            Reader is = new StringReader(result);
            TestObj _final = (TestObj)Serializer.deserializeToObject(is, TestObj.class);
            System.out.println(_final.toString());

        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("CRY!! :'(");
        }
    }

    public static void PaulServerTest(){
        System.out.println("yo");


        try {
            ServerProxy proxy = ServerProxy.instance();
            File initialFile = new File("client/src/main/assets/config.properties");
            InputStream targetStream = new FileInputStream(initialFile);
            ConfigurationManager.use(targetStream);
            ClientCommunicator communicator = ClientCommunicator.instance();

            Game game = new Game("test", 3);
            game.setGameID(UUID.randomUUID().toString());

            //sets client model user
            ClientModel.getInstance().setUser(new User("pjohnst5","password"));


            Player player1 = new Player("pjohnst5", "bob", PlayerColor.BLACK, game.getGameID(), UUID.randomUUID().toString());

            game.addPlayer(player1);

            String className = ConfigurationManager.getString("server_facade_name");
            String methodName = ConfigurationManager.getString("server_create_game_method");
            String[] paramTypes = {Game.class.getCanonicalName()};
            Object[] paramValues = {game};
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            proxy.sendCommand(command);

            // join command
            JoinRequest jr = new JoinRequest("carla","carla", PlayerColor.BLUE,game.getGameID());
            className = ConfigurationManager.getString("server_facade_name");
            methodName = ConfigurationManager.getString("server_join_game_method");
            String[] paramTypes2 = {JoinRequest.class.getCanonicalName()};
            Object [] paramValues2 = {jr};
            command = new GenericCommand(className, methodName, paramTypes2, paramValues2, null);

            proxy.sendCommand(command);

            //joincommand
            jr = new JoinRequest("jeff","jeff",PlayerColor.RED,game.getGameID());
            String className3 = ConfigurationManager.getString("server_facade_name");
            String methodName3 = ConfigurationManager.getString("server_join_game_method");
            String[] paramTypes3 = {JoinRequest.class.getCanonicalName()};
            Object[] paramValues3 = {jr};
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);

            proxy.sendCommand(command3);

            ClientModel model = ClientModel.getInstance();
            //working up till here

            String className4 = ConfigurationManager.getString("server_facade_name");
            String methodName4 = ConfigurationManager.getString("server_start_game_method");
            String[] paramTypes4 = {String.class.getCanonicalName(), String.class.getCanonicalName()};
            Object[] paramValues4 = {game.getGameID(), player1.getPlayerID()};
            ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);

            proxy.sendCommand(command4);

            System.out.println();



        } catch (Exception e) {
            System.out.println("bleh");
        }


    }

    public static void main(String[] args) {
        //DallasTimerTest();
        // DallasSerializerTest();
        PaulServerTest();
    }
}
