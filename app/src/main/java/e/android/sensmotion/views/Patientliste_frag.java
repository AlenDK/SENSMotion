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

import e.android.sensmotion.R;

public class Patientliste_frag extends Fragment implements AdapterView.OnClickListener{

    Button newPat;
    Button Pat1, Pat2, Pat3, Pat4, Pat5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patientliste_frag, container, false);

        String[] navne = {"Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test"};

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.patient_list_item, R.id.name, navne) {

            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);


                TextView rank = view.findViewById(R.id.position);
                rank.setText("1" + 1 + "." + position);
                TextView navntv = view.findViewById(R.id.name);

                ImageView baggrund = view.findViewById(R.id.bagground);
                baggrund.setImageResource(R.drawable.patient_list_bar);

                return view;
            }
        };

        ListView listView = (ListView) findViewById(R.id.patientliste);
        listView.setOnItemClickListener(getActivity());
        listView.setAdapter(adapter);

    }

}
