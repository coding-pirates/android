package de.upb.codingpirates.battleships.android.login;

import android.content.Context;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.logic.ClientType;

/**
 * @author Lukas Kröger, Fynn Ruppel
 */
public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> progressBarShow;
    private MutableLiveData<Boolean> serverJoin;
    private Model model = Model.getInstance();

    private TextInputEditText usernameField;
    private TextInputEditText serverIpField;
    private TextInputEditText serverPortField;

    private TextInputLayout usernameLayout;
    private TextInputLayout serverIpLayout;
    private TextInputLayout serverPortLayout;


    /**
     * Constructor for the LoginViewModel. Sets up the Observers.
     */
    public LoginViewModel(){

        final Observer<Boolean> serverJoinResponse = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newServerJoinResponse) {
                serverJoin.setValue(newServerJoinResponse);
                model.sendLobbyRequest();
            }
        };
        model.getServerJoinRequestSuccess().observeForever(serverJoinResponse);

        final Observer<Boolean> connectionTookTooLongResponse = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean tookTooLong) {
                if (tookTooLong) {
                    serverIpLayout.setErrorEnabled(true);
                    progressBarShow.setValue(false);
                    serverIpLayout.setError("Verbindung konnte nicht hergestellt werden");
                }
            }
        };
        model.getConnectionTookTooLong().observeForever(connectionTookTooLongResponse);
    }

    public MutableLiveData<Boolean> getProgressbarShow(){
        if(progressBarShow == null){
            progressBarShow = new MutableLiveData<Boolean>();
        }
        return progressBarShow;
    }

    public MutableLiveData<Boolean> getServerJoin(){
        if(serverJoin == null){
            serverJoin = new MutableLiveData<Boolean>();
        }
        return serverJoin;
    }

    /**
     * OnClick Method for the Login Button. Gets all input data, validates the input and calls the connectToServer() method of the model, if the input was correct.
     * @param view The button which called the function.(Automatically delivered by the button)
     */
    public void loginButtonClicked(View view){
        usernameField = view.getRootView().findViewById(R.id.usernameInputView);
        serverIpField = view.getRootView().findViewById(R.id.serverIpInputView);
        serverPortField = view.getRootView().findViewById(R.id.serverPortInputView);

        //get the insances of the input layouts
        usernameLayout = view.getRootView().findViewById(R.id.usernameInputLayout);
        serverIpLayout = view.getRootView().findViewById(R.id.serverIpInputLayout);
        serverPortLayout = view.getRootView().findViewById(R.id.serverPortInputLayout);


        //reset all possible errorStates of the TextEditInputViews
        usernameLayout.setErrorEnabled(false);
        serverIpLayout.setErrorEnabled(false);
        serverPortLayout.setErrorEnabled(false);

        String currUsername = usernameField.getText().toString();
        String serverIP = serverIpField.getText().toString();
        String serverPort = serverPortField.getText().toString();



        //only login if all inputs are valid
        if (isUsernameValid(currUsername,view.getContext()) && isServerIpValid(serverIP,view.getContext()) && isServerPortValid(serverPort,view.getContext())) {
            progressBarShow.setValue(true);
            model.connectToServer(serverIP, currUsername, ClientType.SPECTATOR, Integer.parseInt(serverPort));
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

    /**
     * Change the Fragment when the Settingsbutton is clicked
     * @param view
     */
    public void settingsButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_settingsFragment);
    }
}
