package client.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

/**
 * Created by jtyler17 on 7/10/18.
 */

public class CreateGameDialog extends DialogFragment {
    private EditText mGameNameView;
    private NumberPicker mMaxPlayersPicker;
    private Spinner mColorPicker;
    private int mColor;

    public static final String EXTRA_GAME_NAME = "client.server.gameName";
    public static final String EXTRA_PLAYER_COLOR = "client.server.playerColor";
    public static final String EXTRA_MAX_PLAYERS = "client.server.maxPlayers";

    public static CreateGameDialog newInstance() {
        CreateGameDialog dialog = new CreateGameDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_create_game, null);

        mGameNameView = v.findViewById(R.id.dialog_game_name);
        mMaxPlayersPicker = v.findViewById(R.id.dialog_max_players);
        mMaxPlayersPicker.setMaxValue(5);
        mMaxPlayersPicker.setMinValue(2);

        mColorPicker = v.findViewById(R.id.dialog_color);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.player_colors, android.R.layout.simple_spinner_item);
        mColorPicker.setAdapter(colorAdapter);
        mColorPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mColor = position;
            }
        });
        mColorPicker.setSelection(0);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.create_game_dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String gameName = mGameNameView.getText().toString();
                        if (gameName.equals("")) {
                            Toast.makeText(getActivity(), "Game name can't be empty", Toast.LENGTH_SHORT).show();
                        }
                        int maxPlayers = mMaxPlayersPicker.getValue();
                        // FIXME: add color
                        sendResult(Activity.RESULT_OK, gameName, maxPlayers, mColor);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, String gameName, int maxPlayers, int color) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GAME_NAME, gameName);
        intent.putExtra(EXTRA_MAX_PLAYERS, maxPlayers);
        intent.putExtra(EXTRA_PLAYER_COLOR, color);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
