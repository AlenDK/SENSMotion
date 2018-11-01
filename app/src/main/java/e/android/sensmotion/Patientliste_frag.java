package e.android.sensmotion;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Patientliste_frag extends Fragment implements View.OnClickListener{

    Button newPat;
    Button Pat1, Pat2, Pat3, Pat4, Pat5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patientliste_frag, container, false);

        newPat = view.findViewById(R.id.NyPatient);
        newPat.setOnClickListener(this);

        Pat1 = view.findViewById(R.id.button1);
        Pat1.setOnClickListener(this);

        Pat2 = view.findViewById(R.id.button2);
        Pat2.setOnClickListener(this);

        Pat3 = view.findViewById(R.id.opret_P);
        Pat3.setOnClickListener(this);

        Pat4 = view.findViewById(R.id.button4);
        Pat4.setOnClickListener(this);

        Pat5 = view.findViewById(R.id.button5);
        Pat5.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        if(view == newPat)
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new NyPatient_frag())
                    .addToBackStack(null)
                    .commit();

        if(view != newPat) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new PatientData_frag())
                    .addToBackStack(null)
                    .commit();
        }
    }

}
