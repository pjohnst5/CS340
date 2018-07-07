import com.google.gson.Gson;
import java.io.Reader;

import sun.net.www.content.text.Generic;


public class Serializer {

    public static String serialize(Object obj)
    {
        Gson gson = new Gson();
        String serializedObj = gson.toJson(obj);
        return serializedObj;
    }

    public static GenericCommand deserializeCommand(Reader reader)
    {
        Gson gson = new Gson();
        GenericCommand cmd = gson.fromJson(reader, GenericCommand.class);
        return cmd;
    }

}
