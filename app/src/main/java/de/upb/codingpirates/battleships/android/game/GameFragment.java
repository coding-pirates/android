package de.upb.codingpirates.battleships.android.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.GameFragmentBinding;
import de.upb.codingpirates.battleships.android.startscreen.StartScreenViewModel;

public class GameFragment extends Fragment {

    GameFragmentBinding databinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databinding= DataBindingUtil.inflate(inflater, R.layout.game_fragment,container,false);
        databinding.setViewmodel(new GameViewModel());
        // Inflate the layout for this fragment
        return databinding.getRoot();
    }


}
