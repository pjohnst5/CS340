package shared.Response;

public interface IResponse {

    boolean getSuccess();

    String getErrorMessage();


    void setSuccess(boolean b);

    void setErrorMessage(String s);



}
