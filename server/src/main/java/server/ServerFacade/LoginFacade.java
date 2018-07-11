package server.ServerFacade;

import java.util.UUID;

import server.ServerModel.ServerModel;
import server.exception.ServerException;
import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.Response.CommandResponse;
import shared.Response.IResponse;
import shared.User;
import shared.configuration.ConfigurationManager;
import sun.security.krb5.Config;

//Only ServerFacade should touch these
class LoginFacade {

    public static IResponse login(String username, String password)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            User user = serverModel.authenticate(username, password); //User has UUID inside

            String className = ConfigurationManager.getString("client_facede_name");
            String methodName = ConfigurationManager.getString("set_user");
            String[] paramTypes = new String[1];
                paramTypes[0] = User.class.getCanonicalName();
            Object[] paramValues = new Object[1];
                paramValues[0] = user;

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            response.addCommand(command);
            response.setSuccess(true);

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
