package e.android.sensmotion.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.entities.Value;
import e.android.sensmotion.controller.interfaces.IDataController;

public class MainActivity extends Activity {

    TextView tv6;
    IDataController service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //service = new DataController(this);

        service = ControllerRegistry.getDataController();

        tv6 = findViewById(R.id.textView6);
        service.refreshPatient("k5W2uX", "6rT39u", "2018-10-01");

        System.out.println();
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
