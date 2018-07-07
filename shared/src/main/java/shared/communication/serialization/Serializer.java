package shared.communication.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Reader;

public class Serializer implements ISerializer {

    private static Gson _gson = new Gson();
    private static Serializer _instance = new Serializer();

    private Serializer(){}

    public static String _serialize(Object object) {
        return _instance.serialize(object);
    }

    public static Object _deserialize(Reader reader, Class<?> classType) throws IOException {
        return _instance.deserialize(reader, classType);
    }

    public static byte[] serializeToBytes(Object object) {
        return _serialize(object).getBytes();
    }

    public static Object deserializeToObject(Reader reader, Class<?> classType) throws IOException {
        return _deserialize(reader, classType);
    }


    @Override
    public String serialize(Object object) {
        return _gson.toJson(object);
    }

    @Override
    public Object deserialize(Reader reader, Class<?> classType) throws IOException {

        try {

            Object results = null;
            results = _gson.fromJson(reader, classType);
            return results;

        } catch (JsonSyntaxException | JsonIOException e) {

            e.printStackTrace();
            String message = "Exception encountered while deserializing object";
            throw new IOException(message, e);

        }
    }
}