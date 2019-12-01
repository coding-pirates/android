package de.upb.codingpirates.battleships.android.lobby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import de.upb.codingpirates.battleships.android.Model.Model;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.logic.Game;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<Game> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public View listItemViw;
        public MyViewHolder(View v) {
            super(v);
            listItemViw = v;
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
           Model.getInstance().sendGameJoinSpectatorRequest((int)listItemViw.getTag());
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(ArrayList<Game> myDataset) {
        mDataset = myDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView)holder.listItemViw.findViewById(R.id.lobbyGameName)).setText(mDataset.get(position).getName());
        ((TextView)holder.listItemViw.findViewById(R.id.lobbyFiledSize)).setText(mDataset.get(position).getConfig().getHeight() +"x" +mDataset.get(position).getConfig().getWidth());
        ((TextView)holder.listItemViw.findViewById(R.id.lobbyGameStatus)).setText(mDataset.get(position).getState().toString());
        holder.listItemViw.setTag(mDataset.get(position).getId());
       //TODO ((TextView)holder.listItemViw.findViewById(R.id.lobbyPlayerCount)).setText(mDataset.get(position).getCurrentPlayerCount());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
