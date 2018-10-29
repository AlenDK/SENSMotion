package e.android.sensmotion;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Login_frag extends Fragment implements View.OnClickListener {

    String brugernavn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);


        Button login = (Button) view.findViewById(R.id.logIndKnap);

        login.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

        brugernavn = view.findViewById(R.id.logIndKnap).toString();
        if (brugernavn == "admin") {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new NyPatient_frag())
                    .addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Patientliste_frag())
                    .addToBackStack(null)
                    .commit();
        }

    }
}
