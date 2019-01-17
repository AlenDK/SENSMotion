package e.android.sensmotion.views;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Achievement_adapter;
import e.android.sensmotion.views.Achievement.Achievement;
import io.fabric.sdk.android.Fabric;


public class Achievement_frag extends Fragment  {

    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    GridView gridView;
    Achievement_adapter adapter;
    int numberIcons[] = {R.drawable.baseline_sentiment_very_satisfied_black_48, R.drawable.stickman_walking, R.drawable.login_knap,
    R.drawable.patient_ikon};
    ArrayList<Achievement> achievements= new ArrayList <>();
    Achievement marathon, stribe, keepgoing,betatester;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  View view = inflater.inflate(R.layout.achievement, container, false);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.achievement, container, false);


        if (!EMULATOR) {
            Fabric.with(getActivity(), new Crashlytics());
        }

        gridView = (GridView) view.findViewById(R.id.gridview);

        adapter = new Achievement_adapter(getActivity(), achievements);

        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCreateDialog(savedInstanceState, position);

            }

            });



/*

        view.setOnTouchListener(new MotionDetection(getActivity()) {
            @Override
            public void onSwipeDown() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Patient_start_frag())
                        .commit();
            }
        });

  */




        return view;
    }



    public void onCreateDialog(Bundle savedInstanceState, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle(achievements.get(position).getName())
                .setMessage(achievements.get(position).getText());
        if (achievements.get(position).getComplete() == false) {
            builder.setIcon(R.drawable.iconfinder_lock_299105);
        }else {
            builder.setIcon(achievements.get(position).getImage());
        }
        builder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getFragmentManager().beginTransaction()
                                .commit();
                    }
                });
         builder.create().show();

    }

    public ArrayList<Achievement> createAchievement(){
        marathon = new Achievement("Marathon","Du har ikke gjort det endnu");
        marathon.setComplete(true);
        stribe = new Achievement("7 på stribe", "Du har ikke klaret det");
        keepgoing = new Achievement("Keep going","Nej tak");
        betatester = new Achievement("2018 Beta Tester", "Testede app'en i 2018");

        achievements.add(marathon);
        achievements.add(stribe);
        achievements.add(keepgoing);
        achievements.add(betatester);

        return achievements;
    }

    public void checkCompletion(){
        //tjek om condition for en achievment er opnået.


    }
}
