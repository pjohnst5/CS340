package shared;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GenericFactory {

    private static HashMap<Class<?>, Class<?>> objectMapping = new HashMap<>();

    private GenericFactory(){}

    public static <F,T> void register(Class<F> from, Class<T> to){
        objectMapping.put(from, to);
    }


    public static <F,T> T instantiate(Class<F> from, Object... params){

        Class<?> klass = objectMapping.get(from);
        Constructor<?>[] constructors = klass.getConstructors();

        for (Constructor<?> constructor : constructors){
            Class<?>[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != params.length) continue;

            try {
                return (T)constructor.newInstance(params);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                continue;
            }
        }

        return null;

    }

}
