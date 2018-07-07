import java.io.Reader;
import java.io.StringReader;
import java.util.Timer;
import java.util.TimerTask;

import client.server.communication.GameListPoller;
import client.server.communication.GameLobbyPoller;
import shared.communication.serialization.Serializer;



public class hello {

    private static void DallasTimerTest(){

        GameListPoller.start();
        GameLobbyPoller.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameListPoller.stop();
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

    public static void main(String[] args) {
        System.out.println("Hello from Ryan");

        // DallasTimerTest();
        DallasSerializerTest();
    }
}
