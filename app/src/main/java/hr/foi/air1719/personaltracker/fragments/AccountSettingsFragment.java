package hr.foi.air1719.personaltracker.fragments;

import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.content.SharedPreferences;

import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import hr.foi.air1719.database.entities.User;
import hr.foi.air1719.personaltracker.Helper;
import hr.foi.air1719.personaltracker.R;
import hr.foi.air1719.restservice.RestServiceCaller;
import hr.foi.air1719.restservice.RestServiceHandler;

/**
 * Created by Timotea on 13.1.2018..
 */

public class AccountSettingsFragment extends Fragment implements View.OnClickListener {

    EditText inputFullName = null;
    EditText inputUserName = null;
    EditText inputEmail = null;
    String logInUser = null;
    User korisnik = null;


    public AccountSettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        Button button = (Button) view.findViewById(R.id.actionCancelAccount);
        button.setOnClickListener(this);

        inputFullName = (EditText)view.findViewById(R.id.inputFullName);
        inputUserName = (EditText)view.findViewById(R.id.inputUserName);
        inputEmail = (EditText) view.findViewById(R.id.inputEmail);

        SharedPreferences settings = this.getActivity().getSharedPreferences("user", 0);
        logInUser = settings.getString("username", "");


        RestServiceHandler restServiceHandler =  new RestServiceHandler() {
            @Override
            public void onDataArrived(Object result, boolean ok) {

                korisnik = (User) result;
                inputFullName.setText(korisnik.getFullname().toString());
                inputUserName.setText(korisnik.getUsername().toString());
                inputEmail.setText(korisnik.getEmail().toString());

            }
        };

        RestServiceCaller restServiceCaller = new RestServiceCaller(restServiceHandler);
        restServiceCaller.getUser(logInUser.toString());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        Fragment fragment = new AccountSettingsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.layout_account_settings, fragment);
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }
}
