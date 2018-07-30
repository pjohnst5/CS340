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
import shared.model.Message;
import shared.model.Player;
import shared.model.User;
import shared.model.decks.DestDeck;
import shared.model.request.DestCardRequest;
import shared.model.request.JoinRequest;
import shared.model.request.MessageRequest;
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
            ClientModel model = ClientModel.getInstance();
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

            //create game
            String className = ConfigurationManager.getString("server_facade_name");
            String methodName = ConfigurationManager.getString("server_create_game_method");
            String[] paramTypes = {Game.class.getCanonicalName()};
            Object[] paramValues = {game};
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
            proxy.sendCommand(command);

            //sending message
            Message message = new Message();
            //message.setGameID(game.getGameID());
            //message.setDisplayName(player1.getDisplayName());
            message.setMessage("hi yall");

            MessageRequest mr = new MessageRequest(message);

            String className5 = ConfigurationManager.getString("server_facade_name");
            String methodName5 = ConfigurationManager.getString("server_send_message_method");
            String[] paramTypes5 = {MessageRequest.class.getCanonicalName()};
            Object[] paramValues5 = {mr};
            ICommand command5 = new GenericCommand(className5, methodName5, paramTypes5, paramValues5, null);
            proxy.sendCommand(command5);

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

            //start game
            String className4 = ConfigurationManager.getString("server_facade_name");
            String methodName4 = ConfigurationManager.getString("server_start_game_method");
            String[] paramTypes4 = {String.class.getCanonicalName(), String.class.getCanonicalName()};
            Object[] paramValues4 = {game.getGameID(), player1.getPlayerID()};
            ICommand command4 = new GenericCommand(className4, methodName4, paramTypes4, paramValues4, null);
            proxy.sendCommand(command4);

            //working up till here
            DestDeck deck = new DestDeck();
            DestCardRequest dr = new DestCardRequest(deck,player1);
            String className6 = ConfigurationManager.getString("server_facade_name");
            String methodName6 = ConfigurationManager.getString("server_update_dest_deck");
            String[] paramTypes6 = {DestCardRequest.class.getCanonicalName()};
            Object[] paramValues6 = {dr};
            ICommand command6 = new GenericCommand(className6, methodName6, paramTypes6, paramValues6, null);
            proxy.sendCommand(command6);



            System.out.println();



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static void main(String[] args) {
        //DallasTimerTest();
        // DallasSerializerTest();
        PaulServerTest();
    }
}
