package e.android.sensmotion.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;


public class MainActivity extends FragmentActivity {

    TextView tv6;
    IDataController dc;
    IUserController bc;
    IFirebaseController fbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dc = ControllerRegistry.getDataController();
        bc = ControllerRegistry.getUserController();

        fbc = ControllerRegistry.getFirebaseController();
        fbc.setValueListener();

        //dc.refreshPatient(bc.getPatient("p1"), bc.getPatient("p1").getSensor("s1"), "1");



        if (savedInstanceState == null) {
            android.support.v4.app.Fragment fragment = new Login_frag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }





    }

}
