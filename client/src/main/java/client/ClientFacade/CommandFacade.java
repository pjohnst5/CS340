package client.ClientFacade;

import client.ClientModel;
import shared.User;

public class CommandFacade {
    static ClientModel clientModel = ClientModel.getInstance();
    public static void setUser(User user){
        clientModel.setUser(user);
    }
}
