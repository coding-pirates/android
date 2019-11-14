package de.upb.codingpirates.battleships.android.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.SettingsFragmentBinding;


public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SettingsFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment,container,false);
        databinding.setViewmodel(new SettingsViewModel());
        return databinding.getRoot();
    }
}
