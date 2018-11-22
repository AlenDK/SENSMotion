package e.android.sensmotion.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import e.android.sensmotion.R;
import e.android.sensmotion.views.Patient_start_frag;

public class PatientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        if (savedInstanceState == null) {
            Fragment fragment = new Patient_start_frag();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }

    }
}
