package shared.serialization;

import java.io.IOException;
import java.io.Reader;

public interface ISerializer {

    public String serialize(Object object);
    public Object deserialize(Reader reader, Class<?> classType) throws IOException;

}
