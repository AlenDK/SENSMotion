package e.android.sensmotion;


import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Patient_start_frag extends Fragment implements View.OnClickListener{

    private ImageView imageView, stickman_walk, stickman_stand, stickman_bike, stickman_train, stickman_other;
    private ImageButton profile_button;
    private TextView textView;
    private ProgressBar circlebar, walk,stand,bike,train,other;
    private int walk_prog = 0;
    double dailyProgress = 80;
    int circleDailyProgress;
    Toast toast;

    private Handler progHandle = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        createText(view);
        createImages(view);
        createButtons(view);
        createProgressbar(view);

        //Burde måske have sin egen klasse
        circleDailyProgress = (int)(-dailyProgress/100*360);


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
                    Toast.makeText(getActivity(), "Godt klaret. Du har nået en af dine" +
                            "daglige mål for i dag!", Toast.LENGTH_LONG).show();
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


    public void createProgressbar(View view){
        circlebar = (ProgressBar) view.findViewById(R.id.circlebar);
        walk = (ProgressBar) view.findViewById(R.id.progbar_walk);
        stand = (ProgressBar) view.findViewById(R.id.progbar_stand);
        bike = (ProgressBar) view.findViewById(R.id.progbar_bike);
        train = (ProgressBar) view.findViewById(R.id.progbar_train);
        other = (ProgressBar) view.findViewById(R.id.progbar_other);

        circlebar.setRotation(circleDailyProgress);
    }

    public void createImages(View view){
        imageView = (ImageView) view.findViewById(R.id.actionbar_image);
        stickman_walk = (ImageView) view.findViewById(R.id.walking_stickman);
        stickman_stand = (ImageView) view.findViewById(R.id.standing_stickman);
        stickman_bike = (ImageView) view.findViewById(R.id.biking_stickman);
        stickman_train = (ImageView) view.findViewById(R.id.training_stickman);
        stickman_other = (ImageView) view.findViewById(R.id.other_stickman);
    }

    public void createText(View view){
        textView = (TextView) view.findViewById(R.id.nameText);
    }

    public void createButtons(View view){
        profile_button = (ImageButton) view.findViewById(R.id.knap_profil);
        profile_button.setOnClickListener(this);
    }
}
