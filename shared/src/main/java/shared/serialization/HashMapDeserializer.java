package shared.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import shared.model.Game;
import shared.model.wrapper.GamesWrapper;

public class HashMapDeserializer implements JsonDeserializer<GamesWrapper> {
    @Override
    public GamesWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String GAMES = "_games";
        JsonObject jsonObject = json.getAsJsonObject();
        Type typeOfMap = new TypeToken<HashMap<String, Game>>() {}.getType();
        HashMap<String, Game> gamesMap = context.deserialize(jsonObject.get(GAMES), typeOfMap);

        GamesWrapper wrapper = new GamesWrapper();
        wrapper.setGames(gamesMap);
        return wrapper;
    }
}
