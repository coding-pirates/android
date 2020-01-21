package de.upb.codingpirates.battleships.android.startscreen;

import android.view.View;
import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;
import de.upb.codingpirates.battleships.android.R;


public class StartScreenViewModel extends BaseObservable {
    /**
     * Navigates to the LoginView
     * @param view Button which calls the function.
     */
    public void playButtonClicked(View view){
        Navigation.findNavController(view).navigate(R.id.action_startScreenFragment_to_loginFragment);
    }
}
