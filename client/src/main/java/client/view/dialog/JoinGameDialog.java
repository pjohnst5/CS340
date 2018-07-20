package client.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pjohnst5icloud.tickettoride.R;

import client.model.ClientModel;
import client.server.communication.poll.GameListPoller;
import shared.model.Game;

public class JoinGameDialog extends DialogFragment {

    private Button[] mButtons;

    private Game mCurrentGame;
    private EditText mDisplayName;
    private EditText mGameRoomName;

    private Button mCancelButton;
    private Button mJoinButton;


    private int mActiveColorIndex;

    public static final String EXTRA_DISPLAY_NAME = "client.server.JoinGameDialog.displayName";
    public static final String EXTRA_PLAYER_COLOR = "client.server.JoinGameDialog.playerColor";

    public static JoinGameDialog newInstance() {
        return new JoinGameDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_join_game, null);

        Button mBlackTrainButton = v.findViewById(R.id.join_game_train_color_black_button);
        Button mBlueTrainButton = v.findViewById(R.id.join_game_train_color_blue_button);
        Button mGreenTrainButton = v.findViewById(R.id.join_game_train_color_green_button);
        Button mRedTrainButton = v.findViewById(R.id.join_game_train_color_red_button);
        Button mYellowTrainButton = v.findViewById(R.id.join_game_train_color_yellow_button);

        mDisplayName = v.findViewById(R.id.join_game_display_name);
        String gameId = getArguments().getString("GameId");

        mCurrentGame = ClientModel.getInstance().getGames().get(gameId);

        mGameRoomName = v.findViewById(R.id.join_game_room_number);
        mGameRoomName.setText(mCurrentGame.getGameName());

        mCancelButton = v.findViewById(R.id.join_game_cancel_dialog);
        mJoinButton = v.findViewById(R.id.join_game_call_join);


        mBlackTrainButton.setSelected(true);
        mActiveColorIndex = 0;

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
                    mActiveColorIndex = myIndex;
                }
            });
        }


        AlertDialog ad =  new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.join_game_dialog_title)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameListPoller.instance().start();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String displayName = mDisplayName.getText().toString();
                        sendResult(Activity.RESULT_OK, displayName, mActiveColorIndex);
                    }
                })
                .create();

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.getButton(Dialog.BUTTON_NEGATIVE).performClick();
            }
        });

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.getButton(Dialog.BUTTON_POSITIVE).performClick();
            }
        });

        // TODO: Refactor this portion, you should be able to hide buttons without calling show because it creates two objects to show
        ad.show();
        ad.getButton(Dialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        ad.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        ad.hide();

        return ad;
    }


    private void sendResult(int resultCode,
                            String displayName,
                            int color) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLAYER_COLOR, color);
        intent.putExtra(EXTRA_DISPLAY_NAME, displayName);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
