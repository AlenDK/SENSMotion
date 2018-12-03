package e.android.sensmotion.views;

import android.os.Bundle;
import android.app.Fragment;
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

import java.util.List;

import e.android.sensmotion.R;

public class Patientliste_frag extends Fragment implements AdapterView.OnClickListener {

    Button newPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patientliste_frag, container, false);

        String[] navne = {"Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test"};

        final ListView listView = (ListView) view.findViewById(R.id.patientliste);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.patient_list_item, R.id.name, navne) {

            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);


                TextView rank = view.findViewById(R.id.position);
                rank.setText(position + 1 + ".");
                TextView navntv = view.findViewById(R.id.name);

                ImageView baggrund = view.findViewById(R.id.bagground);
                baggrund.setImageResource(R.drawable.patient_list_bar);

                return view;
            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new PatientData_frag())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //Set seperation between list elements
        listView.setDivider(null);
        listView.setDividerHeight(15);

        listView.setAdapter(adapter);


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
}