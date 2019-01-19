package e.android.sensmotion.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.user.Patient;


public class MainActivity extends FragmentActivity {

    private SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (mPrefs.getBoolean("remember", false)) {
            setContentView(R.layout.activity_patient);

            Fragment fragment = new PatientViewpager();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        } else {
            setContentView(R.layout.activity_main);

            if (savedInstanceState == null) {
                android.support.v4.app.Fragment fragment = new Login_frag();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentindhold, fragment)
                        .commit();
            }
        }
    }
}
