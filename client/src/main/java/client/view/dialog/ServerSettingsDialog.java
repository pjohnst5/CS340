package client.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.pjohnst5icloud.tickettoride.R;

import shared.configuration.ConfigurationManager;

/**
 * Created by jtyler17 on 7/31/18.
 */

public class ServerSettingsDialog extends DialogFragment {

    private EditText _hostView;
    private EditText _portView;

    public static ServerSettingsDialog newInstance() {
        return new ServerSettingsDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_server_settings, null);

        _hostView = v.findViewById(R.id.server_settings_host);
        _portView = v.findViewById(R.id.server_settings_port);

        String host = ConfigurationManager.getString("server_host");
        int port = ConfigurationManager.getInt("port");
        _hostView.setText(host);
        _portView.setText(Integer.toString(port));

        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .setPositiveButton(R.string.ok, ((dialogInterface, i) -> {
                    ConfigurationManager.set("server_host", _hostView.getText().toString());
                    ConfigurationManager.set("port", Integer.parseInt(_portView.getText().toString()));
                }))
                .create();

        return ad;
    }
}
