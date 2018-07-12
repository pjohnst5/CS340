package shared.Command;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GenericCommand implements ICommand {

    private String _className;
    private String _methodName;
    private String[] _paramTypes;
    private Object[] _paramValues;
    private Object _callOn;

    private final String _serializableClassName;

    public String getSerializableClassName() {
        return _serializableClassName;
    }

    private static final Map<String,Class> primitivesMap = new HashMap<String,Class>();
    static {
        primitivesMap.put("int", int.class );
        primitivesMap.put("long", long.class );
        primitivesMap.put("double", double.class );
        primitivesMap.put("float", float.class );
        primitivesMap.put("boolean", boolean.class );
        primitivesMap.put("char", char.class );
        primitivesMap.put("byte", byte.class );
        primitivesMap.put("short", short.class );
    }

    public GenericCommand(String className, String methodName, String[] paramTypes, Object[] paramValues, Object callOn) {
        _serializableClassName = getClass().getName();
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

                    if (primitivesMap.containsKey(_paramTypes[i])){
                        Class<?> paramType = primitivesMap.get(_paramTypes[i]);
                        paramTypes[i] = paramType;
                        if (_paramTypes[i].equals("int")){
                            _paramValues[i] = ((Number)_paramValues[i]).intValue();
                        }
                        // _paramValues[i] = paramType.cast(_paramValues[i]);
                    } else {
                        paramTypes[i] = Class.forName(_paramTypes[i]);
                    }
                }
            }

            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, paramTypes);
            obj = method.invoke(_callOn, _paramValues);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class type not found: " + e.getCause());
        }
        catch (Exception e) {
            System.out.println("Error in execute: " + e.getMessage());
        }
        return obj;
    }
}
