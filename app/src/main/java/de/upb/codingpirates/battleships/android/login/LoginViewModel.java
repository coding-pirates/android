package de.upb.codingpirates.battleships.android.login;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import java.io.IOException;

import de.upb.codingpirates.battleships.android.Model.Model;
import de.upb.codingpirates.battleships.android.R;

public class LoginViewModel extends BaseObservable {
    private Model model = new Model();
    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }

    public void loginButtonClicked(View view){
        try {
            model.test();
        }
        catch(Exception e){

        }
    }
}
