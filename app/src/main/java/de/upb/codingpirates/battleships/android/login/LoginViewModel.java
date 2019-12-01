package de.upb.codingpirates.battleships.android.login;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.Model.Model;

/**
 * @author Lukas Kröger, Fynn Ruppel
 */
public class LoginViewModel extends ViewModel {

    private Model model = Model.getInstance();

    private TextInputEditText usernameField;
    private TextInputEditText serverIpField;
    private TextInputEditText serverPortField;

    private TextInputLayout usernameLayout;
    private TextInputLayout serverIpLayout;
    private TextInputLayout serverPortLayout;

    /**
     * TODO description
     * @param view
     */
    public void loginButtonClicked(View view){
        //get the current values of the input fields
        usernameField = view.getRootView().findViewById(R.id.usernameInputView);
        serverIpField = view.getRootView().findViewById(R.id.serverIpInputView);
        serverPortField = view.getRootView().findViewById(R.id.serverPortInputView);
        String currUsername = usernameField.getText().toString();
        String serverIP = serverIpField.getText().toString();
        String serverPort = serverPortField.getText().toString();

        //get the insances of the input layouts
        usernameLayout = view.getRootView().findViewById(R.id.usernameInputLayout);
        serverIpLayout = view.getRootView().findViewById(R.id.serverIpInputLayout);
        serverPortLayout = view.getRootView().findViewById(R.id.serverPortInputLayout);

        //TODO pass the username to the model and validate the username
        //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }
}
