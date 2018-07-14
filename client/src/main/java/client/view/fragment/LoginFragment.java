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
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import client.view.activity.GameListActivity;
import client.presenter.ILoginPresenter;

/**
 * Created by jtyler17 on 7/7/18.
 */

public class LoginFragment extends Fragment implements ILoginView {
    private EditText _usernameField;
    private EditText _passwordField;
    private EditText _confirmPasswordField;
    private Switch _submitOptionSwitch;
    private Button _submitButton;
    private ILoginPresenter _presenter;

    private boolean _isRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        _usernameField = v.findViewById(R.id.login_username);
        _passwordField = v.findViewById(R.id.login_password);
        _confirmPasswordField = v.findViewById(R.id.login_confirm_password);
        _submitOptionSwitch = v.findViewById(R.id.login_submit_option_switch);
        _submitOptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _isRegister = b;
                _confirmPasswordField.setEnabled(b);
            }
        });
        _submitButton = v.findViewById(R.id.login_submit_button);
        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        _isRegister = false;

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
        _presenter = presenter;
    }

    private void onSubmit() {
        if (_presenter == null) {
            return;
        }
        String username = _usernameField.getText().toString();
        String password = _passwordField.getText().toString();
        String checkPassword = _confirmPasswordField.getText().toString();

        if (_isRegister){
            _presenter.register(username, password, checkPassword);
        } else {
            _presenter.login(username, password);
        }

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
