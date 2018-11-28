package e.android.sensmotion.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ResourceBundle;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.bruger.Patient;

public class MainActivity extends Activity {

    TextView tv6;
    IDataController dataController;
    IBrugerController brugerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //service = new DataController(this);

        dataController = ControllerRegistry.getDataController();
        brugerController = ControllerRegistry.getBrugerController();

        tv6 = findViewById(R.id.textView6);

        Patient patient1 = brugerController.getPatient("p1");

        dataController.refreshPatient(patient1, "7");

        /*
        if (savedInstanceState == null) {
            Fragment fragment = new Login_frag();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
        */



    }

}
