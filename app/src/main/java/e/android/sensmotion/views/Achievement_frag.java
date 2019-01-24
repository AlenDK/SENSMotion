package e.android.sensmotion.views;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Achievement_adapter;
import e.android.sensmotion.views.Achievement.Achievement;
import io.fabric.sdk.android.Fabric;

import static e.android.sensmotion.views.Patient_start_frag.totalProgressGoal;
import static e.android.sensmotion.views.Patient_start_frag.totalcycling;
import static e.android.sensmotion.views.Patient_start_frag.totalexercise;
import static e.android.sensmotion.views.Patient_start_frag.totalother;
import static e.android.sensmotion.views.Patient_start_frag.totalstand;
import static e.android.sensmotion.views.Patient_start_frag.totalwalk;


public class Achievement_frag extends Fragment  {

    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    GridView gridView;
    ImageView seperator;
    Achievement_adapter adapter;
    ArrayList<Achievement> achievements= new ArrayList <>();
    Achievement facebook, stribe,betatester, walking, onegoal, allgoals, halfway;
    SharedPreferences prefs;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  View view = inflater.inflate(R.layout.achievement, container, false);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.achievement, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        if (!EMULATOR) {
            Fabric.with(getActivity(), new Crashlytics());
        }

        ArrayList<Achievement> achievements = getAchievement();
        gridView = (GridView) view.findViewById(R.id.gridview);
        seperator = view.findViewById(R.id.seperatorAchievements);

        adapter = new Achievement_adapter(getActivity(), achievements);

        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCreateDialog(savedInstanceState, position);

            }

        });








        return view;
    }



    public void onCreateDialog(Bundle savedInstanceState, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle(achievements.get(position).getName());
        if (achievements.get(position).getComplete() == false) {
            builder.setMessage(achievements.get(position).getText());
            builder.setIcon(achievements.get(position).getImage());
        }else {
            builder.setIcon(achievements.get(position).getImage());
            builder.setMessage(achievements.get(position).getText());
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

    public ArrayList<Achievement> getAchievement(){
        stribe = new Achievement("7 på stribe","For at få dette trofæ skal du have åbnet appen 7 dage i streg!", R.drawable.sevendaystreak);
        stribe.setComplete(prefs.getInt("streakCounter", 0) == 7);

        facebook = new Achievement("Del med dine venner", "For at opnå dette trofæ, skal man dele sine resultater på facebook via del", R.drawable.facebook_logo);
        facebook.setComplete(prefs.getInt("facebookCheck", 0) == 1);

        betatester = new Achievement("Betatester 2019","Et trofæ for at have været beta tester i 2019", R.drawable.beta_tester);
        betatester.setComplete(true);

        walking = new Achievement("500 meter", "Man har gået i 500..", R.drawable.running_achivement);
        walking.setComplete(prefs.getInt("walkamount",0) == 1);

        onegoal = new Achievement("1 mål klaret 100&", "Man skal klare et mål 100%", R.drawable.achivedonegoal);
        onegoal.setComplete(prefs.getFloat("walk", 0) >= totalwalk || prefs.getFloat("stand", 0) >= totalstand || prefs.getFloat("cycle", 0) >= totalcycling
                            && prefs.getFloat("exercise", 0) >= totalexercise || prefs.getFloat("other", 0) >= totalother);

        allgoals = new Achievement("Klaret alle målene", "Du har klaret alle de opsatte mål", R.drawable.achivedallgoals);
        allgoals.setComplete(prefs.getFloat("walk", 0) >= totalwalk && prefs.getFloat("stand", 0) >= totalstand && prefs.getFloat("cycle", 0) >= totalcycling
                            && prefs.getFloat("exercise", 0) >= totalexercise && prefs.getFloat("other", 0) >= totalother);

        halfway = new Achievement("Halvejs", "Du har opået 50% af dine samlede mål", R.drawable.achived50percent);
        halfway.setComplete(prefs.getFloat("walk", 0)+prefs.getFloat("stand", 0)+prefs.getFloat("cycle",0)+prefs.getFloat("exercise",0)
                            +prefs.getFloat("other",0)>= totalProgressGoal/2);

        achievements.add(facebook);
        achievements.add(stribe);
        achievements.add(betatester);
        achievements.add(walking);
        achievements.add(onegoal);
        achievements.add(allgoals);
        achievements.add(halfway);




        return achievements;
    }

    public void checkCompletion(){
        //tjek om condition for en achievment er opnået.


    }


}
