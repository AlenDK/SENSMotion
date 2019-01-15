package e.android.sensmotion.views;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import e.android.sensmotion.R;
import e.android.sensmotion.views.PageFragment.PageAdapter;
import me.kaelaela.verticalviewpager.VerticalViewPager;

public class PatientActivity extends AppCompatActivity {

    PagerAdapter adapter;
    VerticalViewPager verticalViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_viewpager);
        /*setContentView(R.layout.activity_patient);


        if (savedInstanceState == null) {
            Fragment fragment = new Patient_start_frag();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }

        */
        verticalViewPager = (VerticalViewPager) findViewById(R.id.viewpager);
        adapter = new PageAdapter(getSupportFragmentManager());
        verticalViewPager.setAdapter(adapter);


    }
}
