package client.view.dialog;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.pjohnst5icloud.tickettoride.R;

import shared.configuration.ConfigurationManager;

/**
 * Created by jtyler17 on 7/31/18.
 */

public class ServerSettingsDialog extends DialogFragment {

    private EditText _hostView;
    private EditText _portView;
    private Switch _debugSwitch;

    public static ServerSettingsDialog newInstance() {
        return new ServerSettingsDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_server_settings, null);

        _hostView = v.findViewById(R.id.server_settings_host);
        _portView = v.findViewById(R.id.server_settings_port);
        _debugSwitch = v.findViewById(R.id.server_settings_debug_switch);

        String host = ConfigurationManager.getString("server_host");
        int port = ConfigurationManager.getInt("port");
        boolean debugMode = Boolean.parseBoolean(ConfigurationManager.getString("debug_mode"));
        _hostView.setText(host);
        _portView.setText(Integer.toString(port));
        _debugSwitch.setChecked(debugMode);



        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .setPositiveButton(R.string.ok, ((dialogInterface, i) -> {
                    ConfigurationManager.set("server_host", _hostView.getText().toString());
                    ConfigurationManager.set("port", Integer.parseInt(_portView.getText().toString()));
                    ConfigurationManager.set("debug_mode", Boolean.toString(_debugSwitch.isChecked()));
                }))
                .create();

        return ad;
    }
}
