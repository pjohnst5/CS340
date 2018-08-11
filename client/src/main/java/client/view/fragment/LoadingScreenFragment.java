package client.view.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.pjohnst5icloud.tickettoride.R;

public class LoadingScreenFragment extends Fragment implements ILoadingScreenFragment {

    private ProgressDialog _processDialog;

    public void showLoadScreen(boolean show){
        if (show){
            getActivity().runOnUiThread(() -> {
                if (_processDialog != null && _processDialog.isShowing()) return;
                _processDialog = new ProgressDialog(getActivity(), R.style.LoadingDialogTheme);
                _processDialog.setCancelable(false);
                _processDialog.show();
            });
        } else {
            getActivity().runOnUiThread(() -> {
                if (_processDialog != null && _processDialog.isShowing()) _processDialog.dismiss();
            });
        }

    }
}
