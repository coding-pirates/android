package de.upb.codingpirates.battleships.android.game;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collection;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.logic.util.Client;
import de.upb.codingpirates.battleships.logic.util.Point2D;

public class GameFragment extends Fragment {

    //GameFragmentBinding databinding;

    GameViewModel viewModel;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //databinding= DataBindingUtil.inflate(inflater, R.layout.game_fragment,container,false);
        //databinding.setViewmodel(new GameViewModel());
        //databinding.getViewmodel().init(databinding.getRoot());
        // Inflate the layout for this fragment
        //return databinding.getRoot();
         viewModel = new ViewModelProvider(this).get(GameViewModel.class);
         view = inflater.inflate(R.layout.game_fragment, container, false);
         this.initSpinner(viewModel.getPlayers());
         this.initGameFild(viewModel.getFieldHeight(), viewModel.getFieldWidth());
         this.initShips();
         return view;
    }

    public void initGameFild(int fieldHeight, int fieldWidth) {
        GridLayout gameField = view.getRootView().findViewById(R.id.gameField);
        //gameField.setColumnCount(fieldWidth);
        //gameField.setRowCount(fieldHeight);
        //i = counter,  c = current colum, r = current row
        for (int i = 0, c = 0, r = 0; i < fieldHeight * fieldWidth; i++) {
            //if one row is filled
            if (c == fieldWidth) {
                c = 0;
                r++;
            }
            Button btn = new Button(view.getContext());
            btn.setBackground(btn.getContext().getResources().getDrawable(R.drawable.borderfield));
            btn.setMinHeight(0);
            btn.setMinWidth(0);
            btn.setPadding(0,0,0,0);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.setGravity(Gravity.CENTER);
            param.height = 150; //TODO variable size
            param.width = 150;
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            gameField.addView(btn,param);
            c++;
        }


    }

    public void initSpinner(Collection<Client> players){
        Spinner playersSpinner = (Spinner) view.findViewWithTag("playerSpinner");

        ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(
                this.getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<Client>(players)
        );
        playersSpinner.setAdapter(adapter);
        viewModel.setCurrentPlayer((Client) playersSpinner.getSelectedItem());

        playersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCurrentPlayer((Client)parent.getItemAtPosition(position));
                initShips();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void initShips(){
        Collection<Point2D> shipPoints = viewModel.getPointsOfShips();
        //TODO show ships
        GridLayout gameField = view.findViewById(R.id.gameField);
        for(int i = 0; i<gameField.getChildCount();i++){
            gameField.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.borderfield));
        }
        for(Point2D point: shipPoints){
            try {
                Button cell = (Button) gameField.getChildAt(point.getX() + point.getY() * viewModel.getFieldWidth());
                cell.setBackground(getResources().getDrawable(R.drawable.bordership));
            }
            catch (Exception e){}
        }
    }


}
