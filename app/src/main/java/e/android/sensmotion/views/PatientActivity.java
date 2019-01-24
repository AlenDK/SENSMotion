package e.android.sensmotion.views;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import e.android.sensmotion.R;

public class PatientActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.patient_viewpager);
        setContentView(R.layout.activity_patient);


        if (savedInstanceState == null) {
            android.support.v4.app.Fragment fragment = new PatientViewpager();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
    }
}
