package de.upb.codingpirates.battleships.android.lobby;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;


public class LobbyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lobby_fragment, container, false);
    }
}
