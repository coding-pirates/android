package de.upb.codingpirates.battleships.android.startscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.databinding.DataBindingUtil;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.StartscreenFragmentBinding;

public class StartScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //StartscreenFragmentBinding is automatically created by Android
        StartscreenFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.startscreen_fragment,container,false);
        databinding.setViewmodel(new StartScreenViewModel());


        return databinding.getRoot();
        // StartScreenViewModel viewModel = new ViewModelProvider(this).get(StartScreenViewModel.class);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.startscreen_fragment, container, false);
    }



}
