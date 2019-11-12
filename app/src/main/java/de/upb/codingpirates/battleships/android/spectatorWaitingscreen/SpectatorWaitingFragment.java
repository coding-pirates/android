package de.upb.codingpirates.battleships.android.spectatorWaitingscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;

public class SpectatorWaitingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.spectatorwaiting_fragment, container, false);
    }
}
