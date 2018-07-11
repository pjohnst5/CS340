package shared.Response;

import java.util.UUID;

public class LoginResponse implements IResponse {

    private UUID _uuid;
    private boolean _success = false;
    private String _errorMessage;

    public void setUUID(UUID uuid)
    {
        _uuid = uuid;
    }

    public void setSuccess(boolean s)
    {
        _success = s;
    }

    public void setErrorMessage(String s)
    {
        _errorMessage = s;
    }


    public UUID getUUID()
    {
        return _uuid;
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
