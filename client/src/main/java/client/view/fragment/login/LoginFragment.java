package client.view.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import client.presenter.login.LoginPresenter;
import client.view.activity.GameListActivity;
import client.presenter.login.ILoginPresenter;
import client.view.dialog.ServerSettingsDialog;

public class LoginFragment extends Fragment implements ILoginView {

    private static final String SERVER_SETTINGS_DIALOG_TAG = "ServerSettingsDialog";
    private static final int SERVER_SETTINGS_DIALOG_CODE = 0;

    private TextView _confirmPasswordLabel;
    private EditText _usernameField;
    private EditText _passwordField;
    private EditText _confirmPasswordField;
    private Button _settingsButton;
    private ILoginPresenter _presenter;

    private boolean _registerSelected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Simple Members
        _registerSelected = false;
        _presenter = new LoginPresenter(this);

        // Initialize View Members
        Switch mSubmitOptionSwitch = v.findViewById(R.id.login_submit_option_switch);
        Button mSubmitButton = v.findViewById(R.id.login_submit_button);
        _confirmPasswordLabel = v.findViewById(R.id.login_confirm_password_label);
        _usernameField = v.findViewById(R.id.login_username);
        _passwordField = v.findViewById(R.id.login_password);
        _confirmPasswordField = v.findViewById(R.id.login_confirm_password);
        _settingsButton = v.findViewById(R.id.login_server_settings_button);

        // Modify View Members
        mSubmitOptionSwitch.setChecked(false);
        _confirmPasswordField.setVisibility(View.GONE);
        _confirmPasswordLabel.setVisibility(View.GONE);
        _passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        _confirmPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Set View OnClickListeners
        mSubmitOptionSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            _registerSelected = b;
            _confirmPasswordField.setEnabled(b);

            if (_registerSelected) {
                _confirmPasswordField.setVisibility(View.VISIBLE);
                _confirmPasswordLabel.setVisibility(View.VISIBLE);
            } else {
                _confirmPasswordField.setVisibility(View.GONE);
                _confirmPasswordLabel.setVisibility(View.GONE);
            }
        });

        mSubmitButton.setOnClickListener((view) -> onSubmit());
        _settingsButton.setOnClickListener((view) -> {
            FragmentManager manager = getFragmentManager();
            ServerSettingsDialog dialog = ServerSettingsDialog.newInstance();
            dialog.setTargetFragment(LoginFragment.this, SERVER_SETTINGS_DIALOG_CODE);
            dialog.show(manager, SERVER_SETTINGS_DIALOG_TAG);
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        _presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.pause();
    }

    @Override
    public void switchToGameList() {
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


        if (_registerSelected){
            _presenter.register(username, password, checkPassword);
        } else {
            _presenter.login(username, password);
        }

    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }
}
