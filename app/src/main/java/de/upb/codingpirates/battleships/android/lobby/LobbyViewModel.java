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

import de.upb.codingpirates.battleships.android.Model.Model;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.logic.Game;

public class LobbyViewModel extends ViewModel {

    private Model model;
    private ArrayList<Game> gamesOnServer;
    private MutableLiveData<Boolean> goToSpectatorScreen;

    public MutableLiveData<Boolean> getGoToSpectatorScreen(){
        if(goToSpectatorScreen == null){
            goToSpectatorScreen = new MutableLiveData<>();
        }
        return goToSpectatorScreen;
    }

    public LobbyViewModel(){
        model = Model.getInstance();
        gamesOnServer = new ArrayList<Game>();
        final Observer<Collection<Game> > gamesObserver = new Observer<Collection<Game> >() {
            @Override
            public void onChanged(@Nullable final Collection<Game>  newGames) {
                gamesOnServer.clear();
                gamesOnServer.addAll(newGames);
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
    }

    public ArrayList<Game> getGamesOnServer(){
        return gamesOnServer;
    }

    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_spectatorWaitingFragment);
    }

}
