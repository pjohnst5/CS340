package client.view.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import client.view.activity.GameListActivity;
import client.presenter.login.ILoginPresenter;

/**
 * Created by jtyler17 on 7/7/18.
 */

public class LoginFragment extends Fragment implements ILoginView {
    private TextView mConfirmPasswordLabel;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private Switch mSubmitOptionSwitch;
    private Button mSubmitButton;
    private ILoginPresenter mPresenter;

    private boolean isRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mConfirmPasswordLabel = v.findViewById(R.id.login_confirm_password_label);
        mUsernameField = v.findViewById(R.id.login_username);
        mPasswordField = v.findViewById(R.id.login_password);
        mConfirmPasswordField = v.findViewById(R.id.login_confirm_password);
        mSubmitOptionSwitch = v.findViewById(R.id.login_submit_option_switch);
        mSubmitOptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRegister = b;
                mConfirmPasswordField.setEnabled(b);

                if (isRegister) {
                    mConfirmPasswordField.setVisibility(View.VISIBLE);
                    mConfirmPasswordLabel.setVisibility(View.VISIBLE);
                } else {
                    mConfirmPasswordField.setVisibility(View.GONE);
                    mConfirmPasswordLabel.setVisibility(View.GONE);
                }
            }
        });
        isRegister = false;
        mSubmitOptionSwitch.setChecked(false);
        mConfirmPasswordField.setVisibility(View.GONE);
        mConfirmPasswordLabel.setVisibility(View.GONE);

        mSubmitButton = v.findViewById(R.id.login_submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        isRegister = false;

        return v;
    }

    @Override
    public void changeViewGameList() {
        // when the presenter observes a successful sign-in, changes to the game list view
        Intent intent = GameListActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void setPresenter(ILoginPresenter presenter) {
        mPresenter = presenter;
    }

    private void onSubmit() {
        if (mPresenter == null) {
            return;
        }
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        String checkPassword = mConfirmPasswordField.getText().toString();

        if (isRegister){
            mPresenter.register(username, password, checkPassword);
        } else {
            mPresenter.login(username, password);
        }

    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }
}
