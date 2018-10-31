package e.android.sensmotion;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Patient_start_frag extends Fragment implements View.OnClickListener{

    private ImageView imageView, stickman_walk, stickman_stand, stickman_bike, stickman_train, stickman_other;
    private ImageButton profile_button;
    private TextView textView;
    private ProgressBar walk, stand, bike, train, other;
    private int walk_prog = 0;

    private Handler progHandle = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patient,container, false);

        imageView = view.findViewById(R.id.actionbar_image);
        stickman_walk = view.findViewById(R.id.walking_stickman);
        stickman_stand = view.findViewById(R.id.standing_stickman);
        stickman_bike = view.findViewById(R.id.biking_stickman);
        stickman_train = view.findViewById(R.id.training_stickman);
        stickman_other = view.findViewById(R.id.other_stickman);

        profile_button = view.findViewById(R.id.knap_profil);

        textView = view.findViewById(R.id.nameText);

        walk = view.findViewById(R.id.progbar_walk);
        stand = view.findViewById(R.id.progbar_stand);
        bike = view.findViewById(R.id.progbar_bike);
        train = view.findViewById(R.id.progbar_train);
        other = view.findViewById(R.id.progbar_other);


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
            }
        }).start();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.knap_profil:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new patient_setting_frag())
                        .commit();
        }
    }


}
