public class GenericResponse {
    private GenericCommand[] commands;
    private boolean success;
    private String errorMessage;

    public void setCommands(GenericCommand[] cmds)
    {
        commands = cmds;
    }

    public void setSucess(boolean s)
    {
        success = s;
    }

    public void setErrorMessage(String s)
    {
        errorMessage = s;
    }

    public GenericCommand[] getCommands()
    {
        return commands;
    }

    public boolean getSuccess()
    {
        return success;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
