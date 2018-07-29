package client.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.pjohnst5icloud.tickettoride.R;

import client.server.communication.poll.GameListPoller;

/**
 * Created by jtyler17 on 7/10/18.
 */

public class CreateGameDialog extends DialogFragment {

    private Button[] mTrainColorButtons;
    private EditText mGameNameView;
    private EditText mDisplayNameView;
    private NumberPicker mNumPlayers;

    private int mActiveColorIndex;
    private boolean mGameNameTextEntered;
    private boolean mDisplayNameTextEntered;

    public static final String EXTRA_GAME_NAME = "client.server.CreateGameDialog.gameName";
    public static final String EXTRA_DISPLAY_NAME = "client.server.CreateGameDialog.displayName";
    public static final String EXTRA_PLAYER_COLOR = "client.server.CreateGameDialog.playerColor";
    public static final String EXTRA_MAX_PLAYERS = "client.server.CreateGameDialog.maxPlayers";

    public static CreateGameDialog newInstance() {
        return new CreateGameDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_create_game, null);

        Button mBlackTrainButton = v.findViewById(R.id.create_game_train_color_black_button);
        Button mBlueTrainButton = v.findViewById(R.id.create_game_train_color_blue_button);
        Button mGreenTrainButton = v.findViewById(R.id.create_game_train_color_green_button);
        Button mRedTrainButton = v.findViewById(R.id.create_game_train_color_red_button);
        Button mYellowTrainButton = v.findViewById(R.id.create_game_train_color_yellow_button);

        Button mCancelButton = v.findViewById(R.id.create_game_cancel_dialog);
        Button mCreateButton = v.findViewById(R.id.create_game_call_create);

        mGameNameView = v.findViewById(R.id.create_game_room_name);
        mDisplayNameView = v.findViewById(R.id.create_game_display_name);
        mNumPlayers = v.findViewById(R.id.dialog_max_players);
        mNumPlayers.setMinValue(2);
        mNumPlayers.setMaxValue(5);
        mNumPlayers.setValue(2);
        mNumPlayers.setWrapSelectorWheel(false);

        mTrainColorButtons = new Button[] {
                mBlackTrainButton,
                mBlueTrainButton,
                mGreenTrainButton,
                mRedTrainButton,
                mYellowTrainButton
        };

        mActiveColorIndex = 0;
        mTrainColorButtons[mActiveColorIndex].setSelected(true);

        // Add the Click Listeners to the buttons
        for (int i = 0; i < mTrainColorButtons.length; i++){
            final int index = i;

            mTrainColorButtons[i].setOnClickListener(new View.OnClickListener(){
                private final int myIndex = index;

                @Override
                public void onClick(View v){
                    mTrainColorButtons[mActiveColorIndex].setSelected(false);
                    mTrainColorButtons[myIndex].setSelected(true);
                    mActiveColorIndex = myIndex;
                }
            });
        }

        // Enable and disable buttons based on input
        mGameNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0){
                    mGameNameTextEntered = false;
                } else {
                    mGameNameTextEntered = true;
                }
            }
        });

        mDisplayNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0){
                    mDisplayNameTextEntered = false;
                    // Disable the button
                } else {
                    mDisplayNameTextEntered = true;
                    if (mGameNameTextEntered)
                        // Enable the button
                        return;
                }
            }
        });

        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.create_game_dialog_title)
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
                        String gameName = mGameNameView.getText().toString();
                        String displayName = mDisplayNameView.getText().toString();

                        // TODO: Move validation code to presenter - Nope, change submit button to show or not based
                        // On what input is given.
//                        if (gameName.equals("")) {
//                            Toast.makeText(getActivity(), "Game name can't be empty", Toast.LENGTH_SHORT).show();
//
//                        } else if (displayName.equals("")) {

//                            Toast.makeText(getActivity(), "Display name can't be empty", Toast.LENGTH_SHORT).show();
//
//                        }

                        int maxPlayers = mNumPlayers.getValue();

                        sendResult(Activity.RESULT_OK, gameName, displayName, maxPlayers, mActiveColorIndex);
                    }
                })
                .create();

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.getButton(Dialog.BUTTON_NEGATIVE).performClick();
            }
        });

        mCreateButton.setOnClickListener(new View.OnClickListener() {
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
                            String gameName,
                            String displayName,
                            int maxPlayers,
                            int color) {

        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_GAME_NAME, gameName);
        intent.putExtra(EXTRA_DISPLAY_NAME, displayName);
        intent.putExtra(EXTRA_MAX_PLAYERS, maxPlayers);
        intent.putExtra(EXTRA_PLAYER_COLOR, color);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
