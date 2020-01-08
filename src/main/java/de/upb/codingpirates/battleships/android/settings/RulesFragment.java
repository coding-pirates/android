package de.upb.codingpirates.battleships.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import de.upb.codingpirates.battleships.android.R;
public class RulesFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyCustomTheme);
            builder.setTitle(R.string.rulesStr)
                    .setMessage(R.string.spielregelnText);
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
