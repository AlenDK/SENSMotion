package e.android.sensmotion.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.bruger.Patient;

public class MainActivity extends Activity {

    TextView tv6;
    IDataController service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //service = new DataController(this);

    //    service = ControllerRegistry.getDataController();

        //tv6 = findViewById(R.id.textView6);


   //     Patient patient = new Patient("test", null, null, null, null, null, "k5W2uX", "6rT39u");

    //    service.refreshPatient(patient, "2018-10-01");

     //   System.out.println();

        if (savedInstanceState == null) {
            Fragment fragment = new Login_frag();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }




    }

}
