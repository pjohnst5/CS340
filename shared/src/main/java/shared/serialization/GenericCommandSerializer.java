package shared.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import shared.Command.GenericCommand;

public class GenericCommandSerializer implements JsonSerializer<GenericCommand>, JsonDeserializer<GenericCommand> {

    private final String CLASS_NAME = "_className";
    private final String METHOD_NAME = "_methodName";
    private final String PARAM_TYPES = "_paramTypes";
    private final String PARAM_VALUES = "_paramValues";
    private final String CALL_ON = "_callOn";

    @Override
    public JsonElement serialize(GenericCommand src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(CLASS_NAME, src.get_className());
        jsonObject.addProperty(METHOD_NAME, src.get_methodName());

        final JsonArray paramTypes = new JsonArray();
        for (final String paramType : src.get_paramTypes()) {
            final JsonPrimitive jsonAuthor = new JsonPrimitive(paramType);
            paramTypes.add(jsonAuthor);
        }
        jsonObject.add(PARAM_TYPES, paramTypes);

        final JsonElement paramValues = context.serialize(src.get_paramValues());
        jsonObject.add(PARAM_VALUES, paramValues);

        if (src.get_callOn() != null) {
            final JsonElement caller = context.serialize(src.get_callOn());
            jsonObject.add(CALL_ON, caller);
        }

        return jsonObject;
    }

    @Override
    public GenericCommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        // Deserialize Simple objects
        String className = jsonObject.get(CLASS_NAME).getAsString();
        String methodName = jsonObject.get(METHOD_NAME).getAsString();

        // Deserialize String Array of Param Types
        JsonArray jsonParamTypes = jsonObject.get(PARAM_TYPES).getAsJsonArray();
        Type type = new TypeToken<String[]>() {}.getType();
        String[] paramTypes = context.deserialize(jsonParamTypes, type);

        // Deserialize Objects
        JsonArray jsonParamValues = jsonObject.get(PARAM_VALUES).getAsJsonArray();
        Object[] paramValues = new Object[jsonParamValues.size()];

        for (int i = 0; i < jsonParamValues.size(); i++){
            JsonObject paramValueObj = jsonParamValues.get(i).getAsJsonObject();
            try {
                Class<?> objType = Class.forName(paramTypes[i]);
                paramValues[i] = context.deserialize(paramValueObj, objType);

            } catch (ClassNotFoundException e){
                // TODO: Figure out what to do if this breaks
                System.out.println("This thing broke");
                e.printStackTrace();
            }
        }

        // Deserialize Caller Object
        Object callOn = null;
        try {
            context.deserialize(jsonObject.get(CALL_ON).getAsJsonObject(), Object.class);
        } catch (Exception e){ /* leave the callOn as null because it is allowed */}

        GenericCommand command = new GenericCommand(className, methodName, paramTypes, paramValues,callOn);

        return command;
    }
}
