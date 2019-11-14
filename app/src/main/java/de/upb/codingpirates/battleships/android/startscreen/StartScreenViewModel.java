package de.upb.codingpirates.battleships.android.startscreen;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import android.view.View;


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

    public void onButtonClicked(View view){
        //do something
        setTitle("Title changed");
    }
}
