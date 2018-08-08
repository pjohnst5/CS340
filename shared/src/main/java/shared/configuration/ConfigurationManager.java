package shared.configuration;

import java.util.HashMap;

public class ConfigurationManager implements IConfigurationManager {


    private HashMap<String, Integer> _intProperties;
    private HashMap<String, String> _stringProperties;
    private static ConfigurationManager _instance;

    private ConfigurationManager(){
        _stringProperties = new HashMap<>();
        _intProperties = new HashMap<>();
    }

    private static ConfigurationManager instance(){
        if (_instance == null) {
            _instance = new ConfigurationManager();
            new DefaultConfiguration().load(_instance);
            new PluginConfiguration().load(_instance);

        }

        return _instance;
    }

    @Override
    public void add(String key, String value) {
        instance()._stringProperties.put(key, value);
    }

    @Override
    public void add(String key, int value) {
        instance()._intProperties.put(key, value);
    }

    public static void set(String key, String value){
        instance()._stringProperties.put(key, value);
    }

    public static void set(String key, int value){
        instance()._intProperties.put(key, value);
    }

    public static String get(String prop){

        return getString(prop);
    }

    /**
     * Find string property in the configuration settings.
     * @param prop The name of the target property.
     * @return Returns the property as a string if it exists. Otherwise, returns null.
     */
    public static String getString(String prop) {
        if (instance()._stringProperties.containsKey(prop)){
            return instance()._stringProperties.get(prop);
        } else if (instance()._intProperties.containsKey(prop)){
            return Integer.toString(instance()._intProperties.get(prop));
        }
        return null;
    }

    public static int getInt(String prop) {
        return instance()._intProperties.get(prop);
    }

}
