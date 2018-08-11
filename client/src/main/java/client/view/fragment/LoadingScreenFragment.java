package client.view.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.pjohnst5icloud.tickettoride.R;

public class LoadingScreenFragment extends Fragment implements ILoadingScreenFragment {

    private ProgressDialog _processDialog;

    public void showLoadScreen(boolean show){
        try {
            if (show) {
                getActivity().runOnUiThread(() -> {
                    if (_processDialog != null && _processDialog.isShowing()) return;
                    _processDialog = new ProgressDialog(getActivity(), R.style.LoadingDialogTheme);
                    _processDialog.setCancelable(false);
                    _processDialog.show();
                });
            } else {
                getActivity().runOnUiThread(() -> {
                    if (_processDialog != null && _processDialog.isShowing())
                        _processDialog.dismiss();
                });
            }
        } catch (NullPointerException e){
            // Null Pointer Reference for runOnUiThread, but we will be sure to
            // Close the dialog if it is open in the onDestroyView
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (_processDialog != null && _processDialog.isShowing())
            _processDialog.dismiss();
    }
}
