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

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collection;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.GameFragmentBinding;
import de.upb.codingpirates.battleships.logic.util.Client;
import de.upb.codingpirates.battleships.logic.util.Point2D;

/**
 * GameFragment represents the GameView for the App. This class initializes the view and
 * manages all UI related actions
 *
 * @author Lukas Kröger
 */

public class GameFragment extends Fragment {


    private GameViewModel viewModel;
    private View view;
    private GameFragmentBinding databinding;

    /**
     * Creates the GamesView if it should be displayed on the screen
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return The created View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        databinding = DataBindingUtil.inflate(inflater, R.layout.game_fragment,container,false);
        databinding.setViewmodel(viewModel);
        view = databinding.getRoot();
        //view = inflater.inflate(R.layout.game_fragment, container, false);
        this.initSpinner(viewModel.getPlayers());
        this.initGameFild(viewModel.getFieldHeight(), viewModel.getFieldWidth());

        // Create the observer which updates the Ships.
        final Observer<ArrayList<Point2D>> shipPointObserver = new Observer<ArrayList<Point2D>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Point2D> newShipPoints) {
                // Update the UI, in this case, a TextView.
                initShips(newShipPoints);
            }
        };

        viewModel.getPointsOfShips().observe(this.getViewLifecycleOwner(),shipPointObserver);

        return view;
    }

    /**
     * Initializes the gameField
     *
     * @param fieldHeight The game field height specified in the Configruation
     * @param fieldWidth  The game field width specified in the Configruation
     */
    private void initGameFild(int fieldHeight, int fieldWidth) {
        GridLayout gameField = view.getRootView().findViewById(R.id.gameField);

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
            btn.setPadding(0, 0, 0, 0);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.setGravity(Gravity.CENTER);
            param.height = 150; //TODO variable size
            param.width = 150;
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            gameField.addView(btn, param);
            c++;
        }


    }

    /**
     * Initialises the Spinner for selecting displayed players
     *
     * @param players The players of the game
     */
    private void initSpinner(Collection<Client> players) {
        Spinner playersSpinner = (Spinner) view.findViewWithTag("playerSpinner");

        ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(
                this.getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<Client>(players)
        );
        playersSpinner.setAdapter(adapter);
        //viewModel.setCurrentPlayer((Client) playersSpinner.getSelectedItem());

        playersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCurrentPlayer((Client) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Initialises the ships for the selected Player
     */
    private void initShips(Collection<Point2D> shipPoints) {
        GridLayout gameField = view.findViewById(R.id.gameField);
        for (int i = 0; i < gameField.getChildCount(); i++) {
            gameField.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.borderfield));
        }
        for (Point2D point : shipPoints) {

            Button cell = (Button) gameField.getChildAt(point.getX() + point.getY() * viewModel.getFieldWidth());
            cell.setBackground(getResources().getDrawable(R.drawable.bordership));
        }
    }


}
