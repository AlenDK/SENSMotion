package e.android.sensmotion.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import e.android.sensmotion.R;

public class Popop_confirmation extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button accept, decline;
    private Intent act;
    private SharedPreferences mPrefs;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Transparent);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.pop_up_sammentykke, container, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        accept = view.findViewById(R.id.accept);
        decline = view.findViewById(R.id.decline);
        accept.setOnClickListener(this);
        decline.setOnClickListener(this);

        view.getBackground().setAlpha(250);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
           case R.id.accept:
               mPrefs.edit().putInt("confirmation", 1).apply();
                act = new Intent(getActivity(), PatientActivity.class);
                getActivity().finish();
                startActivity(act);

                break;
               case R.id.decline:
                android.support.v4.app.Fragment fragment = new Login_frag();
                getFragmentManager().beginTransaction()
                        .add(R.id.fragmentindhold, fragment)
                        .commit();

        }

    }
}
