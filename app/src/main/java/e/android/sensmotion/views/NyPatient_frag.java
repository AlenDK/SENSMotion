package e.android.sensmotion.views;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import e.android.sensmotion.R;

public class NyPatient_frag extends Fragment implements View.OnClickListener {

    Button opret;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ny_patient_frag, container, false);

        opret = (Button) view.findViewById(R.id.Opret_P);
        opret.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentindhold, new Patientliste_frag())
                .commit();

    }
}
