package de.upb.codingpirates.battleships.android.lobby;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Collection;

import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.logic.Game;

import java.util.ArrayList;
import java.util.Collection;

public class LobbyViewModel extends ViewModel {

    private Model model;
    private MutableLiveData<ArrayList<Game>> gamesOnServer = new MutableLiveData<>();;
    public MutableLiveData<ArrayList<Game>> getGamesOnServer(){
        return gamesOnServer;
    }
    private MutableLiveData<Boolean> goToSpectatorScreen= new MutableLiveData<>();;

    private MutableLiveData<Boolean> goToGameView= new MutableLiveData<>();;
    public MutableLiveData<Boolean> getGoToGameView(){
        return goToGameView;
    }

    public MutableLiveData<Boolean> getGoToSpectatorScreen(){
        return goToSpectatorScreen;
    }

    /**
     * Constructor for the LobbyViewModel binds the Model and sets up the Observer.
     */
    public LobbyViewModel(){
        model = Model.getInstance();
        final Observer<Collection<Game> > gamesObserver = new Observer<Collection<Game> >() {
            @Override
            public void onChanged(@Nullable final Collection<Game>  newGames) {
                gamesOnServer.setValue((ArrayList<Game>)newGames);
            }
        };
        model.getGamesOnServer().observeForever(gamesObserver);
        final Observer<Boolean> goToSpectatorWaiting = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newGoToSpectatorWaiting) {
                goToSpectatorScreen.setValue(newGoToSpectatorWaiting);
            }
        };
        model.getGoToSpectatorWaiting().observeForever(goToSpectatorWaiting);

        final Observer<Boolean> goToGameViewObserver = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newGoToGameView) {
                goToGameView.setValue(newGoToGameView);
            }
        };
        model.getGoToGameView().observeForever(goToGameViewObserver);
    }

    /**
     * Calls the sendLobbyRequest() method of the Model
     */
    public void sendLobbyRequest(){
        model.sendLobbyRequest();
    }

    /**
     * Change the Fragment when the Settingsbutton is clicked
     * @param view
     */
    public void settingsButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_settingsFragment);
    }

}

