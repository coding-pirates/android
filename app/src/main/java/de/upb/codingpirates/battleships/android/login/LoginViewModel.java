package de.upb.codingpirates.battleships.android.login;

import android.content.Context;
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
        // get the context of the view
        Context context = view.getContext();

        //get the current values of the input fields
        usernameField = view.getRootView().findViewById(R.id.usernameInputView);
        serverIpField = view.getRootView().findViewById(R.id.serverIpInputView);
        serverPortField = view.getRootView().findViewById(R.id.serverPortInputView);

        //TODO remove dummy placeholder
        usernameField.setText("Peter Pan");
        serverIpField.setText("123.123.123.123");
        serverPortField.setText("123");

        String username = usernameField.getText().toString();
        String serverIP = serverIpField.getText().toString();
        String serverPort = serverPortField.getText().toString();

        //get the insances of the input layouts
        usernameLayout = view.getRootView().findViewById(R.id.usernameInputLayout);
        serverIpLayout = view.getRootView().findViewById(R.id.serverIpInputLayout);
        serverPortLayout = view.getRootView().findViewById(R.id.serverPortInputLayout);


        //reset all possible errorStates of the TextEditInputViews
        usernameLayout.setErrorEnabled(false);
        serverIpLayout.setErrorEnabled(false);
        serverPortLayout.setErrorEnabled(false);
        //only login if all inputs are valid
        if (isUsernameValid(username,context) && isServerIpValid(serverIP,context) && isServerPortValid(serverPort,context)) {
            //TODO pass the username to the model and validate the username
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
        }

    }

    /**
     * checks whether the username input is empty (can be changed if something else shall happen when validating the input)
     * if the input is invalid, then the username TextInputEditText field will be set to an error state
     * @author Fynn Ruppel
     * @param username
     * @param context
     * @return true, if input is not empty; false, if input is empty
     */
    private Boolean isUsernameValid(String username, Context context) {
        if(username.isEmpty()) {
            usernameLayout.setErrorEnabled(true);
            String str = context.getString(R.string.usernameMissingStr);
            usernameLayout.setError(str);
            return false;
        }
        return true;
    }

    /**
     * checks whether the serverIp input is empty (can be changed if something else shall happen when validating the input)
     * if the input is invalid, then the serverIp TextInputEditText field will be set to an error state
     * @author Fynn Ruppel
     * @param serverIp
     * @param context
     * @return true, if input is not empty; false, if input is empty
     */
    private Boolean isServerIpValid(String serverIp, Context context) {
        if(serverIp.isEmpty()) {
            serverIpLayout.setErrorEnabled(true);
            String str = context.getString(R.string.serverIpMissingStr);
            serverIpLayout.setError(str);
            return false;
        }
        return true;
    }

    /**
     * checks whether the serverPort input is empty (can be changed if something else shall happen when validating the input)
     * if the input is invalid, then the serverPort TextInputEditText field will be set to an error state
     * @author Fynn Ruppel
     * @param username
     * @param context
     * @return true, if input is not empty; false, if input is empty
     */
    private Boolean isServerPortValid(String username, Context context) {
        if(username.isEmpty()) {
            serverPortLayout.setErrorEnabled(true);
            String str = context.getString(R.string.serverPortMissingStr);
            serverPortLayout.setError(str);
            return false;
        }
        return true;
    }
}
