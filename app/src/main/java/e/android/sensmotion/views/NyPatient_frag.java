package e.android.sensmotion.views;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

public class NyPatient_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button opret, fortryd;
    EditText patientID, patientName;
    TextView mobilityValue;
    SeekBar mobilityBar;

    String mobility;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ny_patient_frag, container, false);

        opret = (Button) view.findViewById(R.id.Opret_P);
        opret.setOnClickListener(this);
        fortryd = (Button) view.findViewById(R.id.Fortryd);
        fortryd.setOnClickListener(this);

        patientID = view.findViewById(R.id.patientID);
        patientName = view.findViewById(R.id.nameEditText);
        mobilityValue = view.findViewById(R.id.mobilityValue);

        view.getBackground().setAlpha(250);

        mobilityBar = view.findViewById(R.id.mobilitySeekBar);
        mobilityBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        mobilityBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        mobilityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mobility = i+1 + "";
                mobilityValue.setText(mobility);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == opret) {
            if (patientID.getText().toString().equals("")) {
                Toast.makeText(view.getContext(), "Indtast venligst et patient ID", Toast.LENGTH_LONG).show();
            } else if (patientName.getText().toString().equals("")) {
                Toast.makeText(view.getContext(), "Indtast venligst patientens navn", Toast.LENGTH_LONG).show();
            } else {
                List<Sensor> list = new ArrayList<>();
                Sensor s = new Sensor("s1", 0);
                list.add(s);

                Patient p = new Patient(patientID.getText().toString(), patientName.getText().toString(), null, null,
                        null, list, mobility, "k5W2uX", "6rT39u");

                //Save Patient to firebase
                ControllerRegistry.getUserController().savePatient(p);


            }

        } else if (view == fortryd) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Patientliste_frag())
                    .commit();
        }
    }
}
