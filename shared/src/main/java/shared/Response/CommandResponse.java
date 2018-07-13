package shared.Response;

import shared.Command.ICommand;

public class CommandResponse implements IResponse {
    private ICommand[] _commands;
    private boolean _success = false;
    private String _errorMessage;

    public void setCommands(ICommand[] cmds)
    {
        _commands = cmds;
    }

    public void setSuccess(boolean s)
    {
        _success = s;
    }

    public void setErrorMessage(String s)
    {
        _errorMessage = s;
    }


    public ICommand[] getCommands()
    {
        return _commands;
    }

    public boolean getSuccess()
    {
        return _success;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }
}
