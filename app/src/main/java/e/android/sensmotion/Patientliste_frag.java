package e.android.sensmotion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Patientliste_frag extends Fragment implements View.OnClickListener{

    Button newPat;
    Button Pat1;
    Button Pat2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patientliste_frag, container, false);

        newPat = (Button) view.findViewById(R.id.NyPatient);
        newPat.setOnClickListener(this);
        Pat1 = (Button) view.findViewById(R.id.button1);
        Pat1.setOnClickListener(this);
        Pat2 = (Button) view.findViewById(R.id.button2);
        Pat2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        if(view == newPat)
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new NyPatient_frag())
                    .addToBackStack(null)
                    .commit();

        if(view != newPat)
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new PatientData_frag())
                    .addToBackStack(null)
                    .commit();

    }

}
