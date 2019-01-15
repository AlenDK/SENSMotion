package e.android.sensmotion.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Patientliste_adapter;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.data.Firebase;
import e.android.sensmotion.entities.user.Patient;

public class Patientliste_frag extends android.support.v4.app.Fragment implements AdapterView.OnClickListener {


    Firebase firebase = new Firebase();
    Button newPatient;
    List<Patient> patientList; //=  ControllerRegistry.getUserController().getPatientList();
    Patientliste_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patientliste_frag, container, false);

        ListView listView = (ListView) view.findViewById(R.id.patientliste);
        //Set seperation between list elements
        listView.setDivider(null);
        listView.setDividerHeight(15);

        patientList = new ArrayList<>();

        adapter = new Patientliste_adapter(getActivity(), patientList);

        listView.setAdapter(adapter);

        new AsyncTaskBackground().execute();

  /*

        String[] navne = new String[patientList.size()];

        for(int i = 0; i < ControllerRegistry.getUserController().getPatientList().size(); i++){
            navne[i] = patientList.get(i).getId();
        }


*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String id = patientList.get(i).getId();

                PatientData_frag pdf = new PatientData_frag();
                Bundle pdf_args = new Bundle();

                pdf_args.putString("id", id);

                pdf.setArguments(pdf_args);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, pdf)
                        .addToBackStack(null)
                        .commit();
            }
        });





        newPatient = view.findViewById(R.id.NyPatient);
        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new NyPatient_frag())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        //dunno
    }

    private class AsyncTaskBackground extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Object doInBackground(Object... arg0) {
            try {
                firebase.dTest().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot brugerSnapshot : dataSnapshot.getChildren()) {
                            Patient patient = brugerSnapshot.getValue(Patient.class);

                            patientList.add((patient));

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
                return "HighScore listen er loaded";
            } catch (Exception e) {
                e.printStackTrace();
                return "Noget gik galt";
            }
        }

        @Override
        protected void onPostExecute(Object arg0) {
        }

    }















}