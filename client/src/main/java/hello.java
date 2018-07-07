import java.util.Timer;
import java.util.TimerTask;

import client.server.communication.GameListPoller;
import client.server.communication.GameLobbyPoller;


public class hello {

    public static void DallasTest(){
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

    public static void main(String[] args) {
        System.out.println("Hello from Ryan");

        DallasTest();

    }
}
