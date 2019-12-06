package de.upb.codingpirates.battleships.android.settings;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class SettingsViewModel extends BaseObservable {
    //TODO Add functionality to the volume seekbar
    /**
     * Navigates to the game exit dialog, in which the user can select, if he really wants to exit.
     * @param view
     */
    public void rules_buttonClicked(View view){
        Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_rulesFragment);
    }
}
