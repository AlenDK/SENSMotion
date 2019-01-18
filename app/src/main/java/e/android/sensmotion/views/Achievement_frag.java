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
    ArrayList<Achievement> achievements= new ArrayList <>();
    Achievement facebook, stribe, keepgoing,betatester, comeon, test1, test2, test3, test4;
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
        //stribe = new Achievement("7 på stribe","For at få dette trofæ skal du have åbnet appen 7 dage i streg!", R.drawable.sevendaystreak);
        //stribe.setComplete(prefs.getInt("streakCounter", 0) == 7);
        facebook = new Achievement("7 på stribe", "Du har ikke klaret det", R.drawable.facebook_logo);
      /*  keepgoing = new Achievement("Keep going","Nej tak");
        betatester = new Achievement("2018 Beta Tester", "Testede app'en i 2018");
        comeon = new Achievement("Test", "test");
        test1 = new Achievement("Test", "test");
        test2 = new Achievement("Test", "test");
        test3 = new Achievement("Test", "test");
      */

    //    achievements.add(marathon);
        achievements.add(stribe);
      /*  achievements.add(keepgoing);
        achievements.add(betatester);
        achievements.add(comeon);
        achievements.add(test1);
        achievements.add(test2);
        achievements.add(test3);
        achievements.add(test4);
*/



        return achievements;
    }

    public void checkCompletion(){
        //tjek om condition for en achievment er opnået.


    }


    }


