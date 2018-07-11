package shared.CustomExceptions;

public class ServerProxyException extends Exception {

    public ServerProxyException() {
        super();
    }

    public ServerProxyException(String s) {
        super(s);
    }

    public ServerProxyException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
