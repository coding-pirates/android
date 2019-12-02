package de.upb.codingpirates.battleships.android.lobby;

import android.os.Bundle;
import android.view.*;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LobbyFragmentBinding;
import de.upb.codingpirates.battleships.logic.Game;


public class LobbyFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LobbyViewModel viewmodel;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LobbyFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.lobby_fragment,container,false);
        viewmodel = new ViewModelProvider(this).get(LobbyViewModel.class);
        databinding.setViewmodel(viewmodel);
        view = databinding.getRoot();
        recyclerView = (RecyclerView) view.findViewById(R.id.lobbyList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(new ArrayList<Game>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        final Observer<ArrayList<Game> > gamesObserver = new Observer<ArrayList<Game> >() {
            @Override
            public void onChanged(@Nullable final ArrayList<Game>  newGames) {
                initGamesOnServer(newGames);
            }
        };
        viewmodel.getGamesOnServer().observe(this.getViewLifecycleOwner(),gamesObserver);

        final Observer<Boolean> goToSpectatorScreen = new Observer<Boolean >() {
            @Override
            public void onChanged(@Nullable final Boolean  newSpectatorSceen) {
                Navigation.findNavController(getView()).navigate(R.id.action_lobbyFragment_to_spectatorWaitingFragment);
            }
        };
        viewmodel.getGoToSpectatorScreen().observe(this.getViewLifecycleOwner(),goToSpectatorScreen);


        // Inflate the layout for this fragment
        return databinding.getRoot();
    }

    public void initGamesOnServer(ArrayList<Game> gamesOnServer){

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(gamesOnServer);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


}
