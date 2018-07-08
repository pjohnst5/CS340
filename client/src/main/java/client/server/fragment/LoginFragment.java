package client.server.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.pjohnst5icloud.tickettoride.R;

/**
 * Created by jtyler17 on 7/7/18.
 */

public class LoginFragment extends Fragment {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private Switch mSubmitOptionSwitch;
    private Button mSubmitButton;

    private boolean isRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameField = v.findViewById(R.id.login_username);
        mPasswordField = v.findViewById(R.id.login_password);
        mConfirmPasswordField = v.findViewById(R.id.login_confirm_password);
        mSubmitOptionSwitch = v.findViewById(R.id.login_submit_option_switch);
        mSubmitOptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRegister = b;
            }
        });
        mSubmitButton = v.findViewById(R.id.login_submit_button);

        isRegister = false;

        return v;
    }
}
