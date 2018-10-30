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

    private ImageView imageView, stickman_walk, stickman_sit, stickman_lay;
    private ImageButton profile_button;
    private TextView textView;
    private ProgressBar walk, stand, sit;
    private int walk_prog = 0;

    private Handler progHandle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patient,container, false);

        imageView = view.findViewById(R.id.imageView2);
        stickman_walk = view.findViewById(R.id.walking_stickman);
        stickman_sit = view.findViewById(R.id.sitting_stickman);
        stickman_lay = view.findViewById(R.id.laying_stickman);

        profile_button = view.findViewById(R.id.knap_profil);

        textView = view.findViewById(R.id.textView);

        stand = view.findViewById(R.id.progbar_stand);
        sit = view.findViewById(R.id.progbar_sit);
        walk = view.findViewById(R.id.progbar_walk);

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

    }


}
