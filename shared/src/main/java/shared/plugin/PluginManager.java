package shared.plugin;

public class PluginManager {
    private static IPersistenceProvider _pluginInstance;

    public static void loadPlugin(String pluginName){

    }

    public static IPersistenceProvider getPlugin() {
        return _pluginInstance;
    }


}
