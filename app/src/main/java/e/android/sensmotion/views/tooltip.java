package e.android.sensmotion.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;

import e.android.sensmotion.R;

public class tooltip  extends android.support.v4.app.Fragment implements View.OnClickListener  {

    Button ok;
    TextView text;
    private SharedPreferences Prefs;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Transparent);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.swipe_tip, container, false);
        Prefs = PreferenceManager.getDefaultSharedPreferences(getContext());



        text = view.findViewById(R.id.okKnap);
        ok = view.findViewById(R.id.okKnap);
        ok.setOnClickListener(this);

        view.getBackground().setAlpha(250);
        ok.getBackground().setAlpha(210);


        return view;
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.okKnap){
            Prefs.edit().putInt("pop_up", 1).apply();
            Intent act = new Intent(getActivity(), PatientActivity.class);
            getActivity().finish();
            startActivity(act);
        }
    }
}
