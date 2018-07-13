package shared.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final String CONFIG_PATH = "client" +
            File.separator + "src" +
            File.separator + "main" +
            File.separator + "assets" +
            File.separator + "config.properties";

    private Properties _prop;
    private static ConfigurationManager _instance = new ConfigurationManager();
    private static ConfigurationManager instance() {
        if (!initialized)
            _instance.init();
        return _instance;
    }

    private static boolean initialized;

    private boolean catchError(Exception e, String msg){
        System.out.println(msg);
        e.printStackTrace();
        return true;
    }

    private ConfigurationManager(){
        initialized = false;
    }

    public static void use(InputStream is){
        if (!initialized) {
            _instance.init(is);
            initialized = true;
        }
    }

    private void init(){

        if (initialized) return;

        InputStream input = null;

        try {
            input = new FileInputStream(CONFIG_PATH);
            init(input);

        } catch (FileNotFoundException e) {
            catchError(e, "Absent configurations file");

            if (input != null){
                try {
                    input.close();
                } catch (IOException ex){
                    catchError(ex, "Could not close file.");
                }

            }

            System.exit(1);

        }
    }

    private void init(InputStream input){

        if (initialized) return;

        boolean SysExitFlag = false;

        _prop = new Properties();

        try {
            _prop.load(input);

        } catch (IOException e) {
            SysExitFlag = catchError(e, "Exception encountered processing the file");

        } finally {

            try {
                if (input != null)
                    input.close();

            } catch (IOException e) {
                SysExitFlag = catchError(e, "Exception encountered closing input stream");

            }

            if (SysExitFlag)
                System.exit(1);

        }
        initialized = true;

        System.out.println("Configurations File Loaded");
    }

    public static String get(String prop) {
        return getString(prop);
    }

    public static String getString(String prop) {
        return _instance._prop.getProperty(prop);
    }

    public static int getInt(String prop){
        String propResult = instance()._prop.getProperty(prop);
        int parseResult = Integer.parseInt(propResult);
        return parseResult;
    }
}
