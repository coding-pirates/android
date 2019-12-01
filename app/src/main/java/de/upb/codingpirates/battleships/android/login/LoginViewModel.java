package de.upb.codingpirates.battleships.android.login;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.io.IOException;

import de.upb.codingpirates.battleships.android.Model.Model;
import com.google.android.material.textfield.TextInputEditText;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.logic.ClientType;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> progressBarShow;
    private MutableLiveData<Boolean> serverJoin;
    private Model model = Model.getInstance();

public class LoginViewModel extends ViewModel {

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
    public LoginViewModel(){
        /*final Observer<Boolean> connected = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newConnected) {
                progressBarShow.setValue(newConnected);
            }
        };
        model.getConnected().observeForever(connected); */

        final Observer<Boolean> serverJoinResponse = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newServerJoinResponse) {
                serverJoin.setValue(newServerJoinResponse);
                model.sendLobbyRequest();
            }
        };
        model.getServerJoinRequestSuccess().observeForever(serverJoinResponse);
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

    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }

    public void loginButtonClicked(View view){
        progressBarShow.setValue(true);
        model.connectToServer("192.168.178.42", "Goofie", ClientType.SPECTATOR);
       // Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobbyFragment);
    }
}
