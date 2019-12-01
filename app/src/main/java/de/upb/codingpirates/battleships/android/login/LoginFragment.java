package de.upb.codingpirates.battleships.android.login;

import android.os.Bundle;
import android.view.*;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LoginFragmentBinding;



public class LoginFragment extends Fragment {
    private LoginViewModel viewmodel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewmodel = new ViewModelProvider(this).get(LoginViewModel.class);
        LoginFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment,container,false);
        databinding.setViewmodel(viewmodel);

        final Observer<Boolean> progressBarShow = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newState) {
                // Update the UI, in this case, a TextView.
                if(newState){
                    getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                }
                else{
                    getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
                }
            }
        };

        final Observer<Boolean> serverConnectionComplete = new Observer<Boolean>(){
            @Override
            public void onChanged(@Nullable final Boolean newServerConnection) {
                // Update the UI, in this case, a TextView.
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_lobbyFragment);
            }
        };
        viewmodel.getServerJoin().observe(this.getViewLifecycleOwner(), serverConnectionComplete);
        viewmodel.getProgressbarShow().observe(this.getViewLifecycleOwner(), progressBarShow);

        // Inflate the layout for this fragment
        return databinding.getRoot();
    }

}
