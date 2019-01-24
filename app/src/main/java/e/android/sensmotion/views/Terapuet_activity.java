package e.android.sensmotion.views;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import e.android.sensmotion.R;

public class Terapuet_activity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terapeut_activity);

        if (savedInstanceState == null) {
            android.support.v4.app.Fragment fragment = new Patientliste_frag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
    }

}
