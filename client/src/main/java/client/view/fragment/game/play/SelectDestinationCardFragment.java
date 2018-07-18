package client.view.fragment.game.play;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import client.view.activity.GameListActivity;
import client.presenter.ILoginPresenter;

public class SelectDestinationCardFragment extends Fragment implements ISelectDestinationCardView {
//    private EditText mUsernameField;
//    private EILoginViewditText mPasswordField;
//    private EditText mConfirmPasswordField;
//    private Switch mSubmitOptionSwitch;
//    private Button mSubmitButton;
//    private ILoginPresenter mPresenter;
//
//    private boolean isRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_card_select, container, false);

        return v;
    }


//    @Override
//    public void setPresenter(ILoginPresenter presenter) {
//        mPresenter = presenter;
//    }

//
//    @Override
//    public void showToast(String message) {
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }
}
