package de.upb.codingpirates.battleships.android.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

/**
 * The class for showing the game exit dialog. If User exists, he returns to the lobby. If not, he stays in the current screen
 * @author: Lukas Kröger, Fynn Ruppel
 */
public class ExitGameFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.exitGameString)
                .setPositiveButton(R.string.exitButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_exitGameFragment_to_lobbyFragment);
                    }
                })
                .setNegativeButton(R.string.cancelButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_exitGameFragment_to_gameFragment);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
