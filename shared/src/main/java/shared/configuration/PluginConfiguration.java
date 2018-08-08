package shared.configuration;

import java.io.File;

public class PluginConfiguration implements IConfiguration {

    @Override
    public void load(IConfigurationManager manager) {

        // SQL Plugin
        manager.add("plugin_sql_jar_path", "plugins" + File.pathSeparator + "sqlprovider.jar");
        manager.add("plugin_sql_class_name", "sql.provider.Plugin");

        // JSON Plugin
        manager.add("plugin_json_jar_path", "plugins" + File.pathSeparator + "jsonprovider.jar");
        manager.add("plugin_json_class_name", "json.provider.Plugin");

    }
}
