package de.upb.codingpirates.battleships.android.settings;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class SettingsViewModel extends BaseObservable {
    //TODO Add functionality to the volume seekbar
    /**
     * Navigates to the rules dialog, in which the user can read the rules for the play.
     * @param view The button which calls the function
     */
    public void rules_buttonClicked(View view){
        Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_rulesFragment);
    }
    /**
     * Navigates to the impressum dialog, in which the user can read the impressum.
     * @param view The button which calls the function
     */
    public void impressum_buttonClicked(View view){
        Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_impressumFragment);
    }
}
