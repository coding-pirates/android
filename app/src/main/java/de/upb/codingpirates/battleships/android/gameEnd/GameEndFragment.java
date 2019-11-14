package de.upb.codingpirates.battleships.android.gameEnd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.GameendFragmentBinding;

public class GameEndFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GameendFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.gameend_fragment,container,false);
        databinding.setViewmodel(new GameEndViewModel());


        return databinding.getRoot();
    }
}
