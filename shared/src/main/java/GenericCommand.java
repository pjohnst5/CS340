import java.lang.reflect.Method;

public class GenericCommand implements ICommand {

    private String _className;
    private String _methodName;
    private String[] _paramTypes;
    private Object[] _paramValues;
    private Object _callOn;

    public GenericCommand(String className, String methodName, String[] paramTypes, Object[] paramValues, Object callOn) {
        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes;
        _paramValues = paramValues;
        _callOn = callOn;
    }

    @Override
    public Object execute()
    {
        Class<?>[] paramTypes = null;
        Object obj = new Object();

        try {
            if (_paramTypes != null)
            {
                paramTypes = new Class<?>[_paramTypes.length];
                for (int i = 0; i < _paramTypes.length; i++)
                {
                    paramTypes[i] = Class.forName(_paramTypes[i]);
                }
            }

            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, paramTypes);
            obj = method.invoke(_callOn, _paramValues);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class type not found");
        }
        catch (Exception e) {
            System.out.println("Error in execute: " + e.getMessage());
        }
        return obj;
    }
}
