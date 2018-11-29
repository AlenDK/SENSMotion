package e.android.sensmotion.views;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.impl.BrugerController;
import e.android.sensmotion.controller.impl.DataController;
import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.Value;

public class Patient_start_frag extends Fragment implements View.OnClickListener{

    private ImageView actionbar_image, today_smiley, stickman_walk, stickman_stand, stickman_bike, stickman_train, stickman_other;
    private ImageButton profile_button;
    private TextView textView;
    private ProgressBar circlebar, walk,stand,bike,train,other;
    int overallProgress;
    private int walk_prog = 0;
    double dailyProgress = 80;
    int circleDailyProgress;
    IDataController data = ControllerRegistry.getDataController();
    IBrugerController bruger = ControllerRegistry.getBrugerController();
    ImageView imageView;

    private Handler progHandle = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);


        LinearLayout dates = view.findViewById(R.id.dates);

        System.out.println(bruger.getPatient("p1"));
        System.out.println(bruger.getPatient("p1").getSensor("s1"));

        data.refreshPatient(bruger.getPatient("p1"),bruger.getPatient("p1").getSensor("s1"),"10");
/*
        if(data != null) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("Alen ");
            System.out.println(data);
            System.out.println("Alen ");
            System.out.println(" ");
            System.out.println(data);
            System.out.println("test");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("Burhan");
            System.out.println(data.getPeriode().getValuesList().size());
            System.out.println("Buran");

        } else {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("DRÆB MIG");
            System.out.println(" ");
            System.out.println(" ");

        }

*/


      //  for (int i = 0; i <data.getPeriode().getValuesList().size(); i++) {
            for (int i = 0; i <6; i++) {

            View views = inflater.inflate(R.layout.array_adapter, dates, false);
            TextView textview = views.findViewById(R.id.facetoday_text);
            textview.setText("Dag: " + 1);


            imageView = views.findViewById(R.id.facetoday_image);
            imageView.setImageResource(R.drawable.baseline_sentiment_very_satisfied_black_48);

            dates.addView(views);
        }




        //Burde måske have sin egen klasse
        circleDailyProgress = (int)(270-dailyProgress/100*360);

        createText(view);
        createImages(view);
        createButtons(view);
        createProgressbar(view);


        final Toast akt_klaret =  Toast.makeText(getActivity(), "Godt klaret. Du har nået en af dine" +
                "daglige mål for i dag!", Toast.LENGTH_LONG);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (walk_prog < 100){
                    walk_prog++;
                    android.os.SystemClock.sleep(50);
                    progHandle.post(new Runnable() {
                        @Override
                        public void run() {
                            walk.setProgress(walk_prog);
                        }
                    });
                }

                if(walk_prog == 100){
                   akt_klaret.show();
                }}
        }).start();


        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == profile_button){
            getFragmentManager().beginTransaction()
            .replace(R.id.fragmentindhold, new patient_setting_frag())
            .commit();

        }
    }
/*
    private void changeSmiley(overallProgess) {
        if(overallProgress <= 33.33) {
            imageView.setImageResource(R.drawable.baseline_sentiment_very_unsatisfied_red_48);
        } else if (33.33 < overallProgress | overallProgress <= 66.66) {
            imageView.setImageResource(R.drawable.baseline_sentiment_very_moderat_yellow_48);
        } else if (66.66 < overallProgress | overallProgress <= 100) {
            imageView.setImageResource(R.drawable.baseline_sentiment_very_satisfied_black_48);
        }
    }
*/
    private void createProgressbar(View view){
        circlebar = (ProgressBar) view.findViewById(R.id.circlebar);
        walk = (ProgressBar) view.findViewById(R.id.progbar_walk);
        stand = (ProgressBar) view.findViewById(R.id.progbar_stand);
        bike = (ProgressBar) view.findViewById(R.id.progbar_bike);
        train = (ProgressBar) view.findViewById(R.id.progbar_train);
        other = (ProgressBar) view.findViewById(R.id.progbar_other);

        circlebar.setRotation(circleDailyProgress);
    }

    private void createImages(View view){
        actionbar_image = (ImageView) view.findViewById(R.id.actionbar_image);
        today_smiley = (ImageView) view.findViewById(R.id.facetoday_image);
        stickman_walk = (ImageView) view.findViewById(R.id.walking_stickman);
        stickman_stand = (ImageView) view.findViewById(R.id.standing_stickman);
        stickman_bike = (ImageView) view.findViewById(R.id.biking_stickman);
        stickman_train = (ImageView) view.findViewById(R.id.training_stickman);
        stickman_other = (ImageView) view.findViewById(R.id.other_stickman);
    }

    private void createText(View view){
        textView = (TextView) view.findViewById(R.id.nameText);
    }

    private void createButtons(View view){
        profile_button = (ImageButton) view.findViewById(R.id.knap_profil);
        profile_button.setOnClickListener(this);
    }
}
