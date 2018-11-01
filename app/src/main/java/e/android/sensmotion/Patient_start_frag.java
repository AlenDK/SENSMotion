package e.android.sensmotion;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Patient_start_frag extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView, stickman_walk, stickman_stand, stickman_bike, stickman_train, stickman_other;
    private ImageButton profile_button;
    private TextView textView;
    private ProgressBar circlebar, walk,stand,bike,train,other;
    private int walk_prog = 0;
    double dailyProgress = 80;
    int circleDailyProgress;

    private Handler progHandle = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient);

        //Burde måske have sin egen klasse
        circleDailyProgress = (int)(-dailyProgress/100*360);

        createText();
        createImages();
        createButtons();
        createProgressbar();


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
                    Popup popup = new Popup();
                    popup.show(getSupportFragmentManager(), "task complete!");
                }}
        }).start();

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
