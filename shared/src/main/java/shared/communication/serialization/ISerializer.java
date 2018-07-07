package shared.communication.serialization;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

public interface ISerializer {

    public String serialize(Object object);
    public Object deserialize(Reader reader, Class<?> classType) throws IOException;

}
