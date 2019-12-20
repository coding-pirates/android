package de.upb.codingpirates.battleships.android.gameEnd;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.GameendFragmentBinding;

public class GameEndFragment extends Fragment {

    private View view;
    TableLayout layout;

    /**
     * //TODO Beschreibung
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GameendFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.gameend_fragment,container,false);
        databinding.setViewmodel(new GameEndViewModel());
        view = databinding.getRoot();
        layout = view.findViewById(R.id.playerRankingTableLayout);
        //fillTable(50);
        return databinding.getRoot();
    }

    /**
     * author Fynn Ruppel
     * //TODO Beschreibung
     * @param amount amount of rows that need to be added to the table
     */
    private void fillTable(int amount) {
        for(int i = 0; i< amount; i++) {
            addRow(i+1,"Peter",300-i+10,i+1, i+1);
        }
    }

    /**
     * author : Fynn Ruppel
     * This method adds one row to the EndScreen Player ranking table. The params define the attributes of the player added to the table
     * @param p the place in which the player is ranked
     * @param n name of the player being added
     * @param s score of the player being added
     * @param id id of the row
     */
    private void addRow(int p, String n, int s, int id, int clrCount) {
        //TODO actually fill the table with live data
        //create a new row and define the layout style
        TableRow row = new TableRow(this.getContext());
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);

        //create the 3 textViews and add the needed layout styles to be displayed correctly
        row.setLayoutParams(rowParams);
       TextView place = new TextView(this.getContext());
       place.setLayoutParams(textViewParams);
       place.setGravity(Gravity.CENTER);
       TextView name = new TextView(this.getContext());
        name.setLayoutParams(textViewParams);
        name.setGravity(Gravity.CENTER);
       TextView score = new TextView(this.getContext());
        score.setLayoutParams(textViewParams);
        score.setGravity(Gravity.CENTER);
       /*place.setId(id+1);
       name.setId(id+2);
       score.setId(id+3);*/
       place.setText("" + p);
       name.setText(n);
       score.setText("" + s);

       //add the textViews accordingly to the created row
       row.addView(place, 0);
       row.addView(name,1);
       row.addView(score,2);

       //two colored playerRankingTable in the End
       if (clrCount%2==0) {
           row.setBackgroundColor(getResources().getColor(R.color.color_light_brown_2_translucent));
        }
       else {
           row.setBackgroundColor(getResources().getColor(R.color.color_light_brown_translucent));
       }
       
       //add the new row to the existing tableLayout
       layout.addView(row, rowParams);
    }
}
