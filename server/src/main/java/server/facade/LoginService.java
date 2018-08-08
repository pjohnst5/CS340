package server.facade;

import server.model.ServerModel;
import server.exception.ServerException;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;
import shared.model.User;
import shared.configuration.ConfigurationManager;
import shared.plugin.PluginManager;

//Only ServerFacade should touch these
class LoginService {

    public static IResponse login(String username, String password)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            User user = serverModel.authenticate(username, password); //User has UUID inside

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_user_method");
            String[] paramTypes = {User.class.getCanonicalName()};
            Object[] paramValues = {user};

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
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            User user = serverModel.register(username, password);

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_user_method");
            String[] paramTypes = {User.class.getCanonicalName()};
            Object[] paramValues = {user};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            response.addCommand(command);
            response.setSuccess(true);

            //------------------------------------Database stuff--------------------------------------------------//
            PluginManager.getPlugin().getUserDao().addUser(user);

        } catch (ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Add user failed in database");
        }

        return response;
    }
}
