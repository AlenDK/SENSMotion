package e.android.sensmotion.views;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.github.jinatonic.confetti.CommonConfetti;

import e.android.sensmotion.R;

public class Task_complete_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button ok;
    Button del;
    SharedPreferences prefs;
    RelativeLayout RL;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Transparent);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.task_complete, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        RL = (RelativeLayout) view.findViewById(R.id.relativeLayout);

        ok = view.findViewById(R.id.okKnap);
        del = view.findViewById(R.id.delKnap);
        ok.setOnClickListener(this);
        del.setOnClickListener(this);

        view.getBackground().setAlpha(250);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                CommonConfetti.rainingConfetti(RL, new int[] { Color.BLACK, Color.BLUE, Color.RED, Color.WHITE, Color.YELLOW })
                        .infinite()
                        .animate();
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.okKnap){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Patient_start_frag())
                    .commit();
        }

        if(view.getId() == R.id.delKnap){
            prefs.edit().putInt("facebookCheck", 1).commit();
            del();
        }

    }

    private void del(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Test";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Del via"));
    }
}
