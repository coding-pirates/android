package de.upb.codingpirates.battleships.android.spectatorWaitingscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.SpectatorwaitingFragmentBinding;

public class SpectatorWaitingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SpectatorwaitingFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.spectatorwaiting_fragment,container,false);
        databinding.setViewmodel(new SpectatorWatingViewModel());
        // Inflate the layout for this fragment
        return databinding.getRoot();
    }
}
