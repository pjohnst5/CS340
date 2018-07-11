package server.ServerFacade;

import java.util.UUID;

import server.ServerModel.ServerModel;
import server.exception.ServerException;
import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.Response.CommandResponse;
import shared.Response.IResponse;
import shared.User;

//Only ServerFacade should touch these
class LoginFacade {

    public static IResponse login(String username, String password)
    {
        IResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            User user = serverModel.authenticate(username, password); //User has UUID inside
            //ICommand command = new GenericCommand();


        } catch(ServerException e)
        {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    public static IResponse register(String username, String password)
    {
        return null;
    }
}
