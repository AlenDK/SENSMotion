package e.android.sensmotion.views;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

public class NyPatient_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button opret;
    EditText patientID, patientName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ny_patient_frag, container, false);

        opret = (Button) view.findViewById(R.id.Opret_P);
        opret.setOnClickListener(this);

        patientID = view.findViewById(R.id.patientID);
        patientName = view.findViewById(R.id.editText);

        return view;
    }

    @Override
    public void onClick(View view) {

        Patient p = new Patient(patientID.getText().toString(), patientName.getText().toString(), null, null, null, null, null, "k5W2uX", null);

        ControllerRegistry.getUserController().savePatient(p);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentindhold, new Patientliste_frag())
                .commit();

    }
}
