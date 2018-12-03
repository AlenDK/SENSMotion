package e.android.sensmotion.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import e.android.sensmotion.R;


public class Terapeut_setting extends Fragment implements View.OnClickListener {

    Button LogOut, editInfo, conKlient, editUnit, addUnit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.terapuet_setting_frag, container, false);


        // HUSK AT NAVNGIVE KNAPPERNE KORREKT I XML-FILERNE.
        // btw der er en stavefejl i layoutets navn: "terapuet" i stedet for "terapeut"
        /*
        LogOut = (Button) view.findViewById(R.id.logud);
        LogOut.setOnClickListener(this);

        editInfo = (Button) view.findViewById(R.id.Klient_rediger);
        editInfo.setOnClickListener(this);

        conKlient = (Button) view.findViewById(R.id.Klient_kontakt);
        conKlient.setOnClickListener(this);

        editUnit = (Button) view.findViewById(R.id.Enhed_rediger);
        editUnit.setOnClickListener(this);

        addUnit = (Button) view.findViewById(R.id.Enhed_tilføj);
        addUnit.setOnClickListener(this);
        */


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == LogOut) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Login_frag())
                    .addToBackStack(null)
                    .commit();
        } else if (view != LogOut) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Login_frag.IkkeImplementeret_frag())
                    .addToBackStack(null)
                    .commit();
        }

    }
}
