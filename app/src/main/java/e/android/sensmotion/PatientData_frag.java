package e.android.sensmotion;

import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

public class PatientData_frag extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_data_frag, container, false);


        Button periode = (Button) view.findViewById(R.id.periode);
        Button graf = (Button) view.findViewById(R.id.grafer);

        periode.setOnClickListener(this);
        graf.setOnClickListener(this);

        return view;
    }


   /* @Override  SKAL MÅSKE BRUEGS, HVIS DEN SKAL LAVES TIL EN ACTIVITY
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_data_frag);

        Button periode = (Button) findViewById(R.id.periode);
        Button graf = (Button) findViewById(R.id.grafer);

        periode.setOnClickListener(this);
        graf.setOnClickListener(this);
    }

*/

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.periode:

                getFragmentManager().beginTransaction()
                        .replace(R.id.kalender, new Kalender_frag())
                        .addToBackStack(null)
                        .commit();
                break;


        }
    }
}
