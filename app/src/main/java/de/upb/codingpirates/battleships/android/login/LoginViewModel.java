package de.upb.codingpirates.battleships.android.login;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.Model.Model;

public class LoginViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private TextInputEditText usernameField;
    private TextInputEditText serverIpField;
    private TextInputEditText serverPortField;

    public void loginButtonClicked(View view){
        //get the current values of the input fields
        usernameField = view.getRootView().findViewById(R.id.usernameInputView);
        serverIpField = view.getRootView().findViewById(R.id.serverIpInputView);
        serverPortField = view.getRootView().findViewById(R.id.serverPortInputView);

        String currUsername = usernameField.getText().toString();
        String serverIP = serverIpField.getText().toString();
        String serverPort = serverPortField.getText().toString();

        //TODO pass the username to the model and validate the username
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }
}
