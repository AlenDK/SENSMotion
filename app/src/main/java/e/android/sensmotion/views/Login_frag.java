package e.android.sensmotion.views;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import e.android.sensmotion.R;

public class Login_frag extends Fragment implements View.OnClickListener {

    EditText brugernavn;
    Intent act;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        brugernavn = view.findViewById(R.id.brugernavn);

        TextView opret = (TextView) view.findViewById(R.id.opret);
        TextView glemt = (TextView) view.findViewById(R.id.glemtLogin);
        Button login = (Button) view.findViewById(R.id.logIndKnap);

        login.setOnClickListener(this);
        opret.setOnClickListener(this);
        glemt.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.logIndKnap:
                if (brugernavn.getText().toString().matches("admin")) {
                    act = new Intent(getActivity(), Terapeut_activity.class);
                    startActivity(act);
                    break;
                } else {
                    act = new Intent(getActivity(), PatientActivity.class);
                    startActivity(act);
                    break;
                }

            case R.id.opret:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new IkkeImplementeret_frag())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.glemtLogin:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new IkkeImplementeret_frag())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public static class IkkeImplementeret_frag extends Fragment {
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.ikke_implementeret, container, false);


            return view;
        }
    }
}