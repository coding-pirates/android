package de.upb.codingpirates.battleships.android.spectatorWaitingscreen;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.Model.Model;
import de.upb.codingpirates.battleships.android.R;

public class SpectatorWaitingViewModel extends ViewModel {

    private MutableLiveData<Boolean> goToGameView= new MutableLiveData<>();;
    public MutableLiveData<Boolean> getGoToGameView(){
        return goToGameView;
    }
    private Model model;

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

    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_spectatorWaitingFragment_to_gameFragment);
    }
}
