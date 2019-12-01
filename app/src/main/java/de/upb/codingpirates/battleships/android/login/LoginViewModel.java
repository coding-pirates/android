package de.upb.codingpirates.battleships.android.login;

import android.view.View;
import android.widget.EditText;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import de.upb.codingpirates.battleships.android.R;

public class LoginViewModel extends ViewModel {

    private TextInputEditText usernameField;

    public void loginButtonClicked(View view){
        usernameField = view.getRootView().findViewById(R.id.usernameInputView);
        String currUserName = usernameField.getText().toString();
        //TODO pass the username to the model and validate the username
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }
}
