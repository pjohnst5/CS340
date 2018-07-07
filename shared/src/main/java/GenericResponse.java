import Command.GenericCommand;

public class GenericResponse {
    private GenericCommand[] _commands;
    private boolean _success = false;
    private String _errorMessage;

    public void setCommands(GenericCommand[] cmds)
    {
        _commands = cmds;
    }

    public void setSucess(boolean s)
    {
        _success = s;
    }

    public void setErrorMessage(String s)
    {
        _errorMessage = s;
    }

    public GenericCommand[] getCommands()
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
