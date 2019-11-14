package de.upb.codingpirates.battleships.android.startscreen;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;

import android.view.View;

import de.upb.codingpirates.battleships.android.R;


public class StartScreenViewModel extends BaseObservable {
    private String title = "Test";

    @Bindable
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        // Avoids infinite loops.
            this.title = title;
            // Notify observers of a new value.
            notifyPropertyChanged(BR.title);
    }

    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_startScreenFragment_to_loginFragment);
    }
}
