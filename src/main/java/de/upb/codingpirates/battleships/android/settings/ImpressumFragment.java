package de.upb.codingpirates.battleships.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import de.upb.codingpirates.battleships.android.R;

/**
 * The class for showing the impressum dialog.
 */
public class ImpressumFragment extends DialogFragment {

    /**
     * created dialog
     * @param savedInstanceState
     * @return the created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyCustomTheme);
        builder.setTitle(R.string.impressum)
                .setIcon(R.drawable.ic_coding_pirates_app_icon)
                .setMessage(R.string.impressumText);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
