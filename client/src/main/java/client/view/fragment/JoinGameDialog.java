package client.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.pjohnst5icloud.tickettoride.R;

import client.model.ClientModel;
import shared.model.Game;

public class JoinGameDialog extends DialogFragment {

    private Button mBlackTrainButton;
    private Button mBlueTrainButton;
    private Button mGreenTrainButton;
    private Button mRedTrainButton;
    private Button mYellowTrainButton;

    private int mActiveIndex;
    private Button[] mButtons;

    private Game _currentGame;

    public static final String EXTRA_PLAYER_COLOR = "client.server.JoinGameDialog.playerColor";

    public static JoinGameDialog newInstance() {
        JoinGameDialog dialog = new JoinGameDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_join_game, null);

        _currentGame = ClientModel.getInstance().getCurrentGame();

        mBlackTrainButton = v.findViewById(R.id.join_game_train_color_black_button);
        mBlueTrainButton = v.findViewById(R.id.join_game_train_color_blue_button);
        mGreenTrainButton = v.findViewById(R.id.join_game_train_color_green_button);
        mRedTrainButton = v.findViewById(R.id.join_game_train_color_red_button);
        mYellowTrainButton = v.findViewById(R.id.join_game_train_color_yellow_button);

        mBlackTrainButton.setSelected(true);
        mActiveIndex = 0;

        mButtons = new Button[] {
                mBlackTrainButton,
                mBlueTrainButton,
                mGreenTrainButton,
                mRedTrainButton,
                mYellowTrainButton
        };

        for (int i = 0; i < mButtons.length; i++){

            final int index = i;

            mButtons[i].setOnClickListener(new View.OnClickListener() {

                private final int myIndex =  index;

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mButtons.length; i++) {
                        mButtons[i].setSelected(false);
                    }
                    mButtons[myIndex].setSelected(true);
                    mActiveIndex = myIndex;
                }
            });
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.join_game_dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int color = mActiveIndex;

                        sendResult(Activity.RESULT_OK, color);
                    }
                })
                .create();
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
