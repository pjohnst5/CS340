package shared.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final String CONFIG_PATH = "config.properties";

    private static ConfigurationManager _instance = new ConfigurationManager();
    private Properties _prop;

    private boolean catchError(Exception e, String msg){
        System.out.println(msg);
        e.printStackTrace();
        return true;
    }

    private ConfigurationManager(){
        boolean SysExitFlag = false;

        _prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(CONFIG_PATH);
            _prop.load(input);

        } catch (FileNotFoundException e) {
            SysExitFlag = catchError(e, "Absent configurations file");

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
    }

    public static String get(String prop) {
        return getString(prop);
    }

    public static String getString(String prop) {
        return _instance._prop.getProperty(prop);
    }

    public static int getInt(String prop){
        String propResult = _instance._prop.getProperty(prop);
        int parseResult = Integer.parseInt(propResult);
        return parseResult;
    }
}
