package shared.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import shared.configuration.ConfigurationManager;
import shared.exception.DatabaseException;

public class PluginManager {
    private static IPersistenceProvider _pluginInstance;

    /**
     * Expects load the plugin into the classpath dynamically
     * @param pluginName name of the plugin to get (i.e. "sql_provider", "json_provider")
     */
    public static Class<?> loadPlugin(String pluginName){
        try {
            String pluginJarPath = ConfigurationManager.get("plugin_" + pluginName + "_jar_path");
            String pluginClassName = ConfigurationManager.get("plugin_" + pluginName + "_class_name");

            URL[] pathURL = new URL[]{ new URL(pluginJarPath) };
            URLClassLoader classLoader = new URLClassLoader(pathURL);
            return classLoader.loadClass(pluginClassName);

        } catch (MalformedURLException  | ClassNotFoundException e) {
            System.out.println("Failed to load plugin: " + pluginName);
            System.exit(1);
            return null;
        }
    }

    public static IPersistenceProvider loadAndSetPlugin(String pluginName){
        try {
            Class<?> clazz = loadPlugin(pluginName);

            Constructor<?> constructor = clazz.getConstructor();
            _pluginInstance = (IPersistenceProvider)constructor.newInstance();
            return _pluginInstance;

        } catch (NoSuchMethodException | NullPointerException |
                IllegalAccessException | InstantiationException | InvocationTargetException e) {

            System.out.println("Failed to load plugin: " + pluginName);
            System.exit(1);
            return null;
        }
    }

    public static IPersistenceProvider getPlugin() {
        return _pluginInstance;
    }

    public static void main(String[] args){
        PluginManager.loadPlugin("json_provider");
        try {
            PluginManager.getPlugin().clear();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

}
