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

        //Burde måske have sin egen klasse
        circleDailyProgress = (int)(-dailyProgress/100*360);

        createText();
        createImages();
        createButtons();
        createProgressbar();

        textView = findViewById(R.id.nameText);

        walk = findViewById(R.id.progbar_walk);
        stand = findViewById(R.id.progbar_stand);
        bike = findViewById(R.id.progbar_bike);
        train = findViewById(R.id.progbar_train);
        other = findViewById(R.id.progbar_other);


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

        */

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.knap_profil:
                Intent i = new Intent(view.getContext(),patient_setting_frag.class);
                startActivity(i);
                break;


                /*
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new patient_setting_frag())
                        .commit();
                */
        }
    }


    public void createProgressbar(){
        circlebar = findViewById(R.id.circlebar);
        walk = findViewById(R.id.progbar_walk);
        stand = findViewById(R.id.progbar_stand);
        bike = findViewById(R.id.progbar_bike);
        train = findViewById(R.id.progbar_train);
        other = findViewById(R.id.progbar_other);

        circlebar.setRotation(circleDailyProgress);
    }

    public void createImages(){
        imageView = findViewById(R.id.actionbar_image);
        stickman_walk = findViewById(R.id.walking_stickman);
        stickman_stand = findViewById(R.id.standing_stickman);
        stickman_bike = findViewById(R.id.biking_stickman);
        stickman_train = findViewById(R.id.training_stickman);
        stickman_other = findViewById(R.id.other_stickman);
    }

    public void createText(){
        textView = findViewById(R.id.nameText);
    }

    public void createButtons(){
        profile_button = findViewById(R.id.knap_profil);
        profile_button.setOnClickListener(this);
    }
}
