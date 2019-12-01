package de.upb.codingpirates.battleships.android.lobby;

import android.os.Bundle;
import android.view.*;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LobbyFragmentBinding;



public class LobbyFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LobbyFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.lobby_fragment,container,false);
        databinding.setViewmodel(new LobbyViewModel());

        recyclerView = (RecyclerView) databinding.getRoot().findViewById(R.id.lobbyList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = new String[5]; //TODO make LiveData to gamesOnServer from ViewModel
        myDataset[0] = "1. Item";
        myDataset[1] = "2. Item";
        myDataset[2] = "3. Item";
        myDataset[3] = "4. Item";
        myDataset[4] = "5. Item";


        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // Inflate the layout for this fragment
        return databinding.getRoot();
    }
}
