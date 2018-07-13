package client.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pjohnst5icloud.tickettoride.R;

public class JoinGameDialog extends DialogFragment {

    private Spinner mColorPicker;

    public static final String EXTRA_PLAYER_COLOR = "client.server.JoinGameDialog.playerColor";

    public static JoinGameDialog newInstance() {
        JoinGameDialog dialog = new JoinGameDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        //View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_join_game, null);
//
//        //mColorPicker = v.findViewById(R.id.dialog_join_color);
//        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.player_colors, android.R.layout.simple_spinner_item);
//        mColorPicker.setAdapter(colorAdapter);
//        mColorPicker.setSelection(4);
//
//        return new AlertDialog.Builder(getActivity())
//                .setView(v)
//                .setTitle(R.string.join_game_dialog_title)
//                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        int color = mColorPicker.getSelectedItemPosition();
//
//                        sendResult(Activity.RESULT_OK, color);
//                    }
//                })
//                .create();
        return null;
    }

    private void sendResult(int resultCode,
                            int color) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLAYER_COLOR, color);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
