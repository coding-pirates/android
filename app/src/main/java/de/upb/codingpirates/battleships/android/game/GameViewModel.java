package de.upb.codingpirates.battleships.android.game;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import de.upb.codingpirates.battleships.android.R;

public class GameViewModel extends BaseObservable implements AdapterView.OnItemSelectedListener {

    public int fieldWidth = 5;
    public int fieldHeight = 5;

    public void nextButtonClicked(View view){
        //do something
        GridLayout gameField = view.getRootView().findViewWithTag("gameField");
       // gameField.setColumnCount(fieldWidth);
        //gameField.setRowCount(fieldHeight);
        //i = counter,  c = current colum, r = current row
        for(int i=0, c=0, r =0; i<fieldHeight*fieldWidth; i++) {
            //if one row is filled
            if (c == fieldWidth) {
                c = 0;
                r++;
            }
            Button btn = new Button(view.getContext());
            btn.setLayoutParams(new GridLayout.LayoutParams());
            //btn.setBackgroundColor(Color.BLUE);
            btn.setBackground(btn.getContext().getResources().getDrawable(R.drawable.border));
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = (gameField.getHeight()-24) /fieldHeight; //TODO get margins (24 and 20) dynamically
            param.width = (gameField.getWidth()-20)/ fieldWidth;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            gameField.addView(btn, param);
            c++;
        }

        Spinner playerSpinner = (Spinner) view.getRootView().findViewWithTag("playerSpinner");
        List<String> players = new ArrayList<>();
        players.add("Raphael");
        players.add("Fynn");
        players.add("Paul");
        players.add("Carolin");
        players.add("Roman");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                players
        );

        playerSpinner.setAdapter(adapter);

                //Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_endScreenFragment);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        this.initShips();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    //initialize Gamefield
    public void init(View view){
        //TODO init gamefield
    }

    //initialize Ships
    public void initShips(){
        //TODO init Ships for selected Player
    }
    public void exitGameButtonClicked(View view){
        //TODO exit Game and go Back to lobby
    }

}
