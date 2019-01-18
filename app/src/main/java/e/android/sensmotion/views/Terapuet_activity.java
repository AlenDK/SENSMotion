package e.android.sensmotion.views;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Locale;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
//import e.android.sensmotion.views.Patientliste_frag;

public class Terapuet_activity extends FragmentActivity {

    private IFirebaseController fbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terapeut_activity);

        /*
        fbc = ControllerRegistry.getFirebaseController();
        fbc.setValueListener();
        */

        if (savedInstanceState == null) {
            android.support.v4.app.Fragment fragment = new Patientliste_frag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
    }

}
