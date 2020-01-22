package de.upb.codingpirates.battleships.android.startscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.StartscreenFragmentBinding;

public class StartScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //StartscreenFragmentBinding is automatically created by Android
        StartscreenFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.startscreen_fragment,container,false);
        databinding.setViewmodel(new StartScreenViewModel());

        //when the phones back button is pressed the app is minimized and can be reopened at any time
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
               getActivity().moveTaskToBack(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return databinding.getRoot();
    }



}
