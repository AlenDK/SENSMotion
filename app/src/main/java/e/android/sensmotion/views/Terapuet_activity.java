package e.android.sensmotion.views;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import e.android.sensmotion.R;
//import e.android.sensmotion.views.Patientliste_frag;

public class Terapuet_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terapeut_activity);


        if (savedInstanceState == null) {
            Fragment fragment = new Patientliste_frag();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
    }
}
