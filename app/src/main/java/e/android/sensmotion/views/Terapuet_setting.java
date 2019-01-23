package e.android.sensmotion.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import e.android.sensmotion.R;


public class Terapuet_setting extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button LogOut;
    ImageButton backArrow;
    TextView editInfo, conKlient, editUnit, addUnit;
    ImageView EditInfo, ConClient, EditUnit, AddUnit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.terapuet_setting_frag, container, false);

        LogOut = view.findViewById(R.id.logUd);
        LogOut.setOnClickListener(this);

        backArrow = view.findViewById(R.id.backarrow);
        backArrow.setOnClickListener(this);

        editInfo = view.findViewById(R.id.editInfo);
        editInfo.setOnClickListener(this);

        conKlient = view.findViewById(R.id.contact);
        conKlient.setOnClickListener(this);

        editUnit =view.findViewById(R.id.editSensor);
        editUnit.setOnClickListener(this);

        addUnit = view.findViewById(R.id.addSensor);
        addUnit.setOnClickListener(this);

        EditInfo = view.findViewById(R.id.columnT1);
        EditInfo.setOnClickListener(this);

        ConClient = view.findViewById(R.id.columnT2);
        ConClient.setOnClickListener(this);

        EditUnit = view.findViewById(R.id.columnT3);
        EditUnit.setOnClickListener(this);

        AddUnit = view.findViewById(R.id.columnT4);
        AddUnit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == LogOut) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Login_frag())
                    .addToBackStack(null)
                    .commit();
        } else if (view == backArrow){
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), "Denne funktion er ikke implementeret", Toast.LENGTH_SHORT).show();
        }
    }
}
