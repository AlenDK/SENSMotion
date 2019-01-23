package e.android.sensmotion.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Patientliste_adapter;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.impl.FirebaseController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

public class Patientliste_frag extends android.support.v4.app.Fragment implements AdapterView.OnClickListener {

    private Button newPatient;
    private List<Patient> patientList;
    private Patientliste_adapter adapter;
    private ListView listView;
    private ProgressDialog loading;
    private DatabaseReference database;
    public View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.patientliste_frag, container, false);

        listView = view.findViewById(R.id.patientliste);
        listView.setDivider(null);
        listView.setDividerHeight(15);

        loading = new ProgressDialog(view.getContext());
        loading.setMessage("\t Henter data...");
        loading.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String id = patientList.get(i).getId();
                String name = patientList.get(i).getName();

                PatientData_frag pdf = new PatientData_frag();
                Bundle pdf_args = new Bundle();

                pdf_args.putString("id", id);
                pdf_args.putString("name", name);
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

                NyPatient_frag npf = new NyPatient_frag();
                Bundle npf_args = new Bundle();

                ArrayList<String> ids = new ArrayList<>();

                for(Patient p: patientList){
                    ids.add(p.getId());
                }

                npf_args.putStringArrayList("ids", ids);

                npf.setArguments(npf_args);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, npf)
                        .addToBackStack(null)
                        .commit();
            }
        });

        patientList = new ArrayList<>();

        getFirebasePatientList();


        return view;
    }

    @Override
    public void onClick(View view) {
        //dunno
    }

    public List<Patient> removeDuplicates(List<Patient> list){

        List<Patient> newList = new ArrayList<>();

        for(Patient p1: list){

            if(!newList.contains(p1)){
                newList.add(p1);
            }

        }

        return newList;
    }


    private void getFirebasePatientList() {
        database = FirebaseDatabase.getInstance().getReference("Patients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Patient patient = snapshot.getValue(Patient.class);

                    patientList.add(patient);

                }

                if(loading.isShowing()){
                    loading.dismiss();
                }

                adapter = new Patientliste_adapter(getActivity(), patientList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
                Toast.makeText(getActivity(), "Noget gik galt prøv igen...", Toast.LENGTH_LONG);
            }
        });
    }
}