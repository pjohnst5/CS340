package client.view.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.pjohnst5icloud.tickettoride.R;

public class GameStatusDialog extends DialogFragment {

    public static GameStatusDialog newInstance() {
        return new GameStatusDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_game_status, null);

        Button closeDialogButon = v.findViewById(R.id.game_status_close_dialog);

        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .create();

        closeDialogButon.setOnClickListener(
                (view) -> ad.getButton(Dialog.BUTTON_NEGATIVE).performClick()
        );

        ad.show();
        ad.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

        ad.hide();

        return ad;

    }
}
