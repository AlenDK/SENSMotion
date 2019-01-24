package e.android.sensmotion.views;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;

public class NyPatient_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Button opret, fortryd;
    private EditText patientID, patientName;
    private TextView mobilityValue;
    private SeekBar mobilityBar;
    private DatabaseReference database;
    private String mobility, json, id, name;
    private Patient p;
    private IDataController dataController;
    private Calendar c = Calendar.getInstance();
    private SharedPreferences prefs;
    private ArrayList<String> ids;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ny_patient_frag, container, false);

        if (getArguments() != null) {
            ids = getArguments().getStringArrayList("ids");
        }

        database = FirebaseDatabase.getInstance().getReference("Patients");
        dataController = ControllerRegistry.getDataController();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

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
            } else if(ids.contains(patientID.getText().toString())) {
                Toast.makeText(view.getContext(), "En patient med dette ID findes allerede. Indtast venligst et gyldtigt ID.",
                        Toast.LENGTH_LONG).show();
            }
            else{
                    List<Sensor> list = new ArrayList<>();
                    Sensor s = new Sensor("s1", 0);
                    list.add(s);

                    id = patientID.getText().toString();
                    name = patientName.getText().toString();

                    p = new Patient(id, name, null, null,
                            null, list, mobilityValue.getText().toString(), "k5W2uX", "6rT39u");

                    //Save Patient to firebase
                    savePatientFirebase();
                }

        } else if (view == fortryd) {
            getFragmentManager().popBackStack();
        }
    }

    public void savePatientFirebase(){
        database.child(p.getId()).setValue(p);

        AsyncTask atask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String dato = sdf.format(c.getTime());

                    json = dataController.getApiDATA(p, dato);
                    return null;
                } catch (Exception e) {
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object titler) {
                JSONObject data = null;
                try {
                    data = new JSONObject(json);
                    Values values = new Values();
                    values.getAPIdata(data);
                    values.setMobility(p.getMobility());
                    values.setStatus(prefs.getString("status", "0"));

                    database.child(p.getId()).child("sensorer").child("0").child("currentPeriod").child("valuesList").child("0").setValue(values);
                    System.out.println("Data saved...");

                    String myFormat = "ddMM";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String dato = sdf.format(c.getTime());

                    database.child(p.getId()).child("sensorer").child("0").child("currentPeriod").child("startingDate").setValue(dato);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Patientliste_frag())
                        .commit();
            }
        }.execute();
    }
}
