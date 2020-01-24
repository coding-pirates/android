package de.upb.codingpirates.battleships.android.game;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.GameFragmentBinding;
import de.upb.codingpirates.battleships.logic.Client;
import de.upb.codingpirates.battleships.logic.Point2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * GameFragment represents the GameView for the App. This class initializes the view and
 * manages all UI related actions
 *
 * @author Lukas Kröger
 */
public class GameFragment extends Fragment {

    //get the width of the current device in pixel
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;

    private GameViewModel viewModel;
    private View view;
    private GameFragmentBinding databinding;
    private CountDownTimer timer;

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

        /**
         * Observer for updating Shots
         */
        final Observer<ArrayList<Point2D>> shotsObserver = new Observer<ArrayList<Point2D>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Point2D> newShots) {
               initShots(newShots);
            }
        };

        viewModel.getPointsOfShots().observe(this.getViewLifecycleOwner(), shotsObserver);

        /**
         * Observer for updating Points
         */
        final Observer<Integer> pointsObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newPoints) {
                initPoints(newPoints);
            }
        };

        final Observer<Boolean> newRound = new Observer<Boolean>(){
            @Override
            public void onChanged(@Nullable final Boolean newRound) {
                timer.cancel();
                timer.start();
            }
        };
        viewModel.getNewRound().observe(this.getViewLifecycleOwner(), newRound);


        /**
         * Observer for GoingToGameEnd
         */
        final Observer<Boolean> goToGameViewObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newGoToGameEnd) {
                if(newGoToGameEnd) {
                   Navigation.findNavController(getView()).navigate(R.id.action_gameFragment_to_gameEndFragment);
                }
            }
        };
        viewModel.getGoToGameEnd().observe(this.getViewLifecycleOwner(),goToGameViewObserver);

        //initialize the timer for the time left
        TextView timerView = view.findViewById(R.id.tf_timeLeft);
        initTimer(timerView,viewModel.getRoundTime(), view.getContext());

        //handles the phones back button pressed event to return to lobby view
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_gameFragment_to_lobbyFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return view;
    }

    /**
     * Initializes the game field
     *
     * @param fieldHeight The game field height specified in the Configuration
     * @param fieldWidth  The game field width specified in the Configuration
     */
    private void initGameFild(int fieldHeight, int fieldWidth) {
        GridLayout gameField = view.getRootView().findViewById(R.id.gameField);
        int minWidth = 100;
        //int minHeigth = 100;

        //i = counter,  c = current column, r = current row
        for (int i = 0, c = 0, r = 0; i < fieldHeight * fieldWidth; i++) {
            //if one row is filled
            if (c == fieldWidth) {
                c = 0;
                r++;
            }
            Button btn = new Button(view.getContext());
            btn.setBackground((getResources().getDrawable(R.drawable.ic_quadrat)));
            btn.setPadding(0, 0, 0, 0);
            btn.setTag("waterField");
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.setGravity(Gravity.CENTER);

            //Scaling the buttons according to the screen size. If the ratio is smaller than the min width then the height and width will be the minimum.
            param.height = width/fieldWidth < minWidth ? minWidth : width/fieldWidth;
            param.width = width/fieldWidth < minWidth ? minWidth : width/fieldWidth;

            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            gameField.addView(btn, param);
            c++;
        }


    }

    /**
     * Initialises the spinner for selecting displayed players
     *
     * @param players The players of the game
     */
    private void initSpinner(ArrayList<Client> players) {
        Spinner playersSpinner = (Spinner) view.findViewWithTag("playerSpinner");

        ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(
                this.getContext(),
                R.layout.spinner_item,
                players
        );
        playersSpinner.setAdapter(adapter);

        playersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCurrentPlayer((Client) parent.getItemAtPosition(position));
                cleanGameField();
                initShips(viewModel.getPointsOfShips());
                initShots(viewModel.getPointsOfShots().getValue());
                if(viewModel.getPointsOfCurrentPlayer().getValue()!=null) {
                    initPoints(viewModel.getPointsOfCurrentPlayer().getValue());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * cleans game field of the selsected player
     */
    private void cleanGameField() {
        GridLayout gameField = view.findViewById(R.id.gameField);
        for (int i = 0; i < gameField.getChildCount(); i++) {
            gameField.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.ic_quadratic));
            gameField.getChildAt(i).setTag("waterField");
        }
    }
    /**
     * Initialises the ships for the selected Player
     */
    private void initShips(Collection<Point2D> shipPoints) {
        GridLayout gameField = view.findViewById(R.id.gameField);

        for (Point2D point : shipPoints) {
            Button cell = (Button) gameField.getChildAt(point.getX() + point.getY() * viewModel.getFieldWidth());
            switch(Objects.hash(point.hashCode(), viewModel.getCurrentPlayer().getId()) % 3){
                case 0:
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_ship_1));
                    cell.setTag("ship1");
                    break;
                case 1:
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_ship_2));
                    cell.setTag("ship2");
                    break;
                case 2:
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_ship_3));
                    cell.setTag("ship3");
                    break;

            }
        }

    }

    /**
     * initialises the ships for the selected player
     * @param shotPoints
     */
    private void initShots(Collection<Point2D> shotPoints){
        for (Point2D point : shotPoints) {
            GridLayout gameField = view.findViewById(R.id.gameField);
            Button cell = (Button) gameField.getChildAt(point.getX() + point.getY() * viewModel.getFieldWidth());
            switch(cell.getTag().toString()){
                case "ship1":
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_ship_1_hit));
                    break;
                case "ship2":
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_ship_2_hit));
                    break;
                case "ship3":
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_ship_3_hit));
                    break;
                case "waterField":
                    cell.setBackground(getResources().getDrawable(R.drawable.ic_field_hit));
                    break;
            }
        }
    }

    /**
     * initialises the points of the selected player
     * @param pointsOfPlayer
     */
    private void initPoints(int pointsOfPlayer){
        TextView pointsView = view.findViewById(R.id.tf_points);
        double pointsConverted = ((double)pointsOfPlayer)/4;
        pointsView.setText("Punkte: " + pointsConverted);
    }

    /**
     * create a new timer which counts down from @param lengthInSeconds and displays the current time in the Fragment
     * @param textView the view the timer shall update
     * @param lengthInSeconds length in seconds the timer takes to finish
     * @param context context of the main activity so the ids can be called
     */
    public void initTimer(TextView textView, long lengthInSeconds, Context context) {
        timer = new CountDownTimer(lengthInSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText(context.getResources().getString(R.string.timeLeft) + " " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                textView.setText(context.getResources().getString(R.string.timeLeft) + " 0");
            }
        }.start();
    }

}
