package e.android.sensmotion.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.entities.user.Patient;

public class Patientliste_adapter extends ArrayAdapter<Patient> {



    private Activity context;
    private List<Patient> patientList;

    public Patientliste_adapter(Activity context, List<Patient> patientList) {
        super(context, R.layout.patient_list_item, patientList);
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View view = inflater.inflate(R.layout.patient_list_item, null, true);

        TextView nameView = view.findViewById(R.id.name);
        TextView idView = view.findViewById(R.id.patient_id);

        Patient patient = patientList.get(position);


        nameView.setText(patient.getName());
        idView.setText(patient.getId());

        return view;

    }


}


