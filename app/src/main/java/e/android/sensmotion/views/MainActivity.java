package e.android.sensmotion.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import e.android.sensmotion.R;
import e.android.sensmotion.dataTest.Value;
import e.android.sensmotion.dataTest.Values;
import e.android.sensmotion.serviceTest.SENScallback;
import e.android.sensmotion.serviceTest.SENSservice;

public class MainActivity extends Activity implements SENScallback {

    TextView tv6;
    SENSservice service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new SENSservice(this);


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

    @Override
    public void serviceSuccess(Value value) {
        tv6.setText(service.getValues());
    }

    @Override
    public void serviceFailure(Exception exception) {
        tv6.setText("Error");
    }
}
