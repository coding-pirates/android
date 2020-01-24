package de.upb.codingpirates.battleships.android.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LoginFragmentBinding;


/**
 * LoginFragment represents the LoginView. This class initializes the view.
 */
public class LoginFragment extends Fragment {
    private LoginViewModel viewmodel;

    /**
     * creates view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewmodel = new ViewModelProvider(this).get(LoginViewModel.class);
        LoginFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment,container,false);
        databinding.setViewmodel(viewmodel);

        final Observer<Boolean> progressBarShow = newState -> {
            // Update the UI, in this case, a TextView.
            if(newState){
                getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }
            else{
                getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        };
        viewmodel.getProgressbarShow().observe(this.getViewLifecycleOwner(), progressBarShow);
        final Observer<Boolean> serverConnectionComplete = newServerConnection -> {
            if(newServerConnection) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_lobbyFragment);
            }
        };
        viewmodel.getServerJoin().observe(this.getViewLifecycleOwner(), serverConnectionComplete);

        //navigates to the startscreenView when the phones back button is pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_startScreenFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // Inflate the layout for this fragment
        return databinding.getRoot();
    }

}
