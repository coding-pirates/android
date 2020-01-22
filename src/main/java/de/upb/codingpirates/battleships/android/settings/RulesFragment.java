package de.upb.codingpirates.battleships.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;
public class RulesFragment extends DialogFragment {

        private int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.RulesDialogTheme);
            builder.setTitle(R.string.rulesStr)
                    .setIcon(R.drawable.ic_codex_icon_colored)
                    .setMessage(R.string.spielregelnText);
            // Create the AlertDialog object and return it
            return builder.create();
        }

        @Override
        public void onResume() {
            super.onResume();
            Window window = getDialog().getWindow();
            if(window == null) return;
            WindowManager.LayoutParams params = window.getAttributes();

            //sets the width of the AlertDialog to 90% of Screen-Width
            params.width = (int) (width * 0.9f);
            window.setAttributes(params);
        }
    }
