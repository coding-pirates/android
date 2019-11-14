package de.upb.codingpirates.battleships.android.login;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class LoginViewModel extends BaseObservable {
    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }
}
