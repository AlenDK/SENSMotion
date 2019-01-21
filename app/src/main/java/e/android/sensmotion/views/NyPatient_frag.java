package e.android.sensmotion.views;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

public class NyPatient_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button opret;
    EditText patientID, patientName, mobilitet, sensorID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ny_patient_frag, container, false);

        opret = (Button) view.findViewById(R.id.Opret_P);
        opret.setOnClickListener(this);

        patientID = view.findViewById(R.id.patientID);
        patientName = view.findViewById(R.id.editText);
        mobilitet = view.findViewById(R.id.editText4);
        sensorID = view.findViewById(R.id.editText3);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(patientID.getText().toString().equals("")){
            Toast.makeText(view.getContext(), "Indtast venligst et patient ID", Toast.LENGTH_LONG).show();
        }
        else if(patientName.getText().toString().equals("")){
            Toast.makeText(view.getContext(), "Indtast venligst patientens navn", Toast.LENGTH_LONG).show();
        }
        else{
            List<Sensor> list = new ArrayList<>();
            Sensor s = new Sensor("s1", 0);
            list.add(s);

            Patient p = new Patient(patientID.getText().toString(), patientName.getText().toString(), null, null,
                    null, list, mobilitet.getText().toString(), "k5W2uX", "6rT39u");

            //Save Patient to firebase
            ControllerRegistry.getUserController().savePatient(p);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Patientliste_frag())
                    .commit();
        }

    }
}
