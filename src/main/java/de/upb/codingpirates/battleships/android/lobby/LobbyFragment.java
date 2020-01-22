package de.upb.codingpirates.battleships.android.lobby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LobbyFragmentBinding;
import de.upb.codingpirates.battleships.client.network.ClientConnector;
import de.upb.codingpirates.battleships.logic.Game;

import java.util.ArrayList;


public class LobbyFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeContainer;
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

        //set up recyclerView
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(new ArrayList<Game>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        //set up recyclerView swipe down updater
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewmodel.sendLobbyRequest();
            }
             });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final Observer<ArrayList<Game> > gamesObserver = new Observer<ArrayList<Game> >() {
            @Override
            public void onChanged(@Nullable final ArrayList<Game>  newGames) {
                initGamesOnServer(newGames);
                swipeContainer.setRefreshing(false);
            }
        };
        viewmodel.getGamesOnServer().observe(this.getViewLifecycleOwner(),gamesObserver);

        final Observer<Boolean> goToSpectatorScreen = new Observer<Boolean >() {
            @Override
            public void onChanged(@Nullable final Boolean  newSpectatorScreen) {
                if(newSpectatorScreen != null && newSpectatorScreen) {
                    Navigation.findNavController(getView()).navigate(R.id.action_lobbyFragment_to_spectatorWaitingFragment);
                }
            }
        };
        viewmodel.getGoToSpectatorScreen().observe(this.getViewLifecycleOwner(),goToSpectatorScreen);

        final Observer<Boolean> goToGameViewObserver = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newGoToGameView) {
                if(newGoToGameView != null && newGoToGameView) {
                    try {
                        Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_gameFragment);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        viewmodel.getGoToGameView().observeForever(goToGameViewObserver);

        //handles the behavior when the phones back button is pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                try {
                    //closes the connection to the server and return to the loginView
                    viewmodel.getClientConnector().disconnect();
                    Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_loginFragment);
                }
                catch (IOException e) {

                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return databinding.getRoot();
    }

    /**
     * Sets a new Recycling Adapter to the recycler view
     * @param gamesOnServer
     */
    public void initGamesOnServer(ArrayList<Game> gamesOnServer){
        mAdapter = new RecyclerAdapter(gamesOnServer);
        recyclerView.setAdapter(mAdapter);
    }

}
