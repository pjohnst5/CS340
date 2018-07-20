package shared.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.model.wrapper.GamesWrapper;

public class Serializer implements ISerializer {

    private static Gson _gson; // = new Gson();
    private static Serializer _instance = new Serializer();

    private Serializer(){
        GsonBuilder builder = new GsonBuilder();

        // Adding custom deserializers
        builder.registerTypeAdapter(ICommand.class, new GenericCommandSerializer());
        builder.registerTypeAdapter(GenericCommand.class, new GenericCommandSerializer());
        builder.registerTypeAdapter(GamesWrapper.class, new HashMapDeserializer());

        _gson = builder.create();
    }

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

    public static void serializeToWriter(Writer writer, Object object) {
        _gson.toJson(object, writer);
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
