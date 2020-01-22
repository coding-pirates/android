package de.upb.codingpirates.battleships.android.settings;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.SettingsFragmentBinding;

public class SettingsFragment extends Fragment {
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SettingsFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment,container,false);
        databinding.setViewmodel(new SettingsViewModel());
        super.onCreate(savedInstanceState);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls(databinding);
        return databinding.getRoot();
    }

    /**
     * this method is used to initiate the control function of the volume bar
     * @param databinding
     */
    private void initControls(SettingsFragmentBinding databinding)
    {
        try
        {
            volumeSeekbar = databinding.getRoot().findViewById(R.id.volumeseekBar);
            audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

            volumeSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
