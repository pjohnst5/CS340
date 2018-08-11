package shared.configuration;

import java.io.File;

public class PluginConfiguration implements IConfiguration {

    private static final String PROTOCOL = "file:///";


    @Override
    public void load(IConfigurationManager manager) {

        String workingDir = PROTOCOL +
                System.getProperty("user.dir") +
                File.separator + "plugins" +
                File.separator;

        // Trim extra '/' if you are unix
        workingDir = workingDir.replaceFirst(PROTOCOL + "/", PROTOCOL);

        // Delta Update Interval
        manager.add("delta_update_interval", 10);

        // SQL Plugin
        manager.add("plugin_sql_provider_class_name", "sql.provider.Plugin");
        manager.add("plugin_sql_provider_jar_path", workingDir + "sqlprovider.jar");

        // SQLite Plugin
        manager.add("plugin_sqlite_class_name", "org.sqlite.JDBC");
        manager.add("plugin_sqlite_jar_path", workingDir + "sqlite-jdbc-3.23.1.jar");

        // JSON Plugin
        manager.add("plugin_json_provider_class_name", "json.provider.Plugin");
        manager.add("plugin_json_provider_jar_path", workingDir + "jsonprovider.jar");

    }
}
