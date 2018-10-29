package e.android.sensmotion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Patientliste_frag extends Fragment implements View.OnClickListener{

    private Button newPat;
    private Button Pat1;
    private Button Pat2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newPat = (Button) getView().findViewById(R.id.NyPatient);
        newPat.setOnClickListener(this);
        Pat1 = (Button) getView().findViewById(R.id.button1);
        Pat1.setOnClickListener(this);
        Pat2 = (Button) getView().findViewById(R.id.button2);
        Pat2.setOnClickListener(this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patientliste_frag, container, false);
    }

    @Override
    public void onClick(View view) {

        if(view == newPat)
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new NyPatient_frag())
                    .addToBackStack(null)
                    .commit();

        if(view == Pat1 || view == Pat2)
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new BenytKnapperFragment())
                    .addToBackStack(null)
                    .commit();

    }

}
