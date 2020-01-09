package de.upb.codingpirates.battleships.android.spectatorWaitingscreen;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import de.upb.codingpirates.battleships.android.model.Model;

public class SpectatorWaitingViewModel extends ViewModel {

    private MutableLiveData<Boolean> goToGameView= new MutableLiveData<>();;
    public MutableLiveData<Boolean> getGoToGameView(){
        return goToGameView;
    }
    private Model model;

    /**
     * Cosntructor for the SpectatorWaitingViewModel. Binds the Model and sets up the Observer for the goToGameView Boolean.
     */
    public SpectatorWaitingViewModel(){
        model = Model.getInstance();
        model.setGoToSpectatorWaiting(false);
        final Observer<Boolean> goToGameViewObserver = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newGoToGameView) {
                goToGameView.setValue(newGoToGameView);
            }
        };
        model.getGoToGameView().observeForever(goToGameViewObserver);
    }
}
