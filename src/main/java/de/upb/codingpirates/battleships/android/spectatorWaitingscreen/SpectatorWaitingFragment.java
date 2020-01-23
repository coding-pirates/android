package de.upb.codingpirates.battleships.android.spectatorWaitingscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.SpectatorwaitingFragmentBinding;

public class SpectatorWaitingFragment extends Fragment {

    private SpectatorWaitingViewModel viewModel;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SpectatorwaitingFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.spectatorwaiting_fragment,container,false);
        viewModel = new ViewModelProvider(this).get(SpectatorWaitingViewModel.class);
        view = databinding.getRoot();
        databinding.setViewmodel(viewModel);

        final Observer<Boolean> goToGameViewObserver = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newGoToGameView) {
                if(newGoToGameView != null && newGoToGameView) {
                    try {
                        Navigation.findNavController(view).navigate(R.id.action_spectatorWaitingFragment_to_gameFragment);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        viewModel.getGoToGameView().observeForever(goToGameViewObserver);

        // Inflate the layout for this fragment
        return databinding.getRoot();
    }
}
