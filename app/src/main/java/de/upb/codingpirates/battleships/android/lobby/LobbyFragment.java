package de.upb.codingpirates.battleships.android.lobby;

import android.os.Bundle;
import android.view.*;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LobbyFragmentBinding;



public class LobbyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LobbyFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.lobby_fragment,container,false);
        databinding.setViewmodel(new LobbyViewModel());
        // Inflate the layout for this fragment
        return databinding.getRoot();
    }
}
