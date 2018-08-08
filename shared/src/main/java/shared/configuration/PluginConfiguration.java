package shared.configuration;

import java.io.File;

public class PluginConfiguration implements IConfiguration {

    private static final String PROTOCOL = "file://";


    @Override
    public void load(IConfigurationManager manager) {

        // Delta Update Interval
        manager.add("delta_update_interval", 10);

        // SQL Plugin
        manager.add("plugin_sql_provider_class_name", "sql.provider.Plugin");
        manager.add("plugin_sql_provider_jar_path", PROTOCOL +
                System.getProperty("user.dir") +
                File.separator + "plugins" +
                File.separator + "sqlprovider.jar");

        // JSON Plugin
        manager.add("plugin_json_provider_class_name", "json.provider.Plugin");
        manager.add("plugin_json_provider_jar_path", PROTOCOL +
                System.getProperty("user.dir") +
                File.separator + "plugins" +
                File.separator + "jsonprovider.jar");

    }
}
