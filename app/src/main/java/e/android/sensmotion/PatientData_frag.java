package e.android.sensmotion;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;

public class PatientData_frag extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_data_frag, container, false);

        Button periode = view.findViewById(R.id.periode);
        Button graf = view.findViewById(R.id.grafer);


        periode.setOnClickListener(this);
        graf.setOnClickListener(this);

        return view;
    }


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
