package client.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.pjohnst5icloud.tickettoride.R;

public class ClaimRouteDialog extends DialogFragment {

    public static CreateGameDialog newInstance() {

        return new CreateGameDialog();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_game_status, null);


        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                })
                .setPositiveButton()
                .create();


        return ad;
    }
}
