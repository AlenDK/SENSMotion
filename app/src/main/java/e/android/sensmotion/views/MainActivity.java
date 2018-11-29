package e.android.sensmotion.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.bruger.Patient;

public class MainActivity extends Activity {

    TextView tv6;
    IDataController dc;
    IBrugerController bc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dc = ControllerRegistry.getDataController();
        bc = ControllerRegistry.getBrugerController();

        //dc.refreshPatient(bc.getPatient("p1"), bc.getPatient("p1").getSensor("s1"), "1");



        if (savedInstanceState == null) {
            Fragment fragment = new Login_frag();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }





    }

}
