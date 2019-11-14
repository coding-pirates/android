package de.upb.codingpirates.battleships.android.login;

import android.os.Bundle;
import android.view.*;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.databinding.LoginFragmentBinding;



public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LoginFragmentBinding databinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment,container,false);
        databinding.setViewmodel(new LoginViewModel());
        // Inflate the layout for this fragment
        return databinding.getRoot();
    }

}
