package e.android.sensmotion.views;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.data.Firebase;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;


public class Login_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    EditText brugernavn;
    CheckBox dataHandling;
    Intent act;
    IDataController dc;
    IUserController bc;
    String jsonString;
    Firebase firebasee = new Firebase();
    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");

    Patient patient;
    Sensor sensor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        if (!EMULATOR) {

            Fabric.with(getActivity(), new Crashlytics());
        }
/*
        forceCrash(view);
*/

        brugernavn = view.findViewById(R.id.brugernavn);
        dc = ControllerRegistry.getDataController();
        bc = ControllerRegistry.getUserController();

        TextView opret = (TextView) view.findViewById(R.id.opret);
        TextView glemt = (TextView) view.findViewById(R.id.glemtLogin);
        Button login = (Button) view.findViewById(R.id.logIndKnap);
        dataHandling = (CheckBox) view.findViewById(R.id.dataHandling);

        login.setOnClickListener(this);
        opret.setOnClickListener(this);
        glemt.setOnClickListener(this);

        return view;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.logIndKnap:
                if (brugernavn.getText().toString().matches("admin")) {
                    act = new Intent(getActivity(), Terapuet_activity.class);
                    startActivity(act);
                    break;
                } else {

                    if (!dataHandling.isChecked()) {
                        Toast.makeText(getActivity(), "Du skal acceptere SENSmotion\'s vilkår " +
                                "for håndtering af personfølsomme data", Toast.LENGTH_LONG).show();

                        break;
                    }

                    try {
                        String hentDataResult = new HentDataAsyncTask().execute().get();
                        sensor = patient.getSensor("s1");
                        dc.saveData(hentDataResult, sensor);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    System.out.println("/////////////////////////////// REST /////////////////////////////////////");
                    //System.out.println(bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getRest());

                    System.out.println("/////////////////////////////// Other /////////////////////////////////////");
                    //System.out.println(bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getOther());

                    Patient p1 = ControllerRegistry.getUserController().getPatient("p1");
                    firebasee.newPatient(p1);

                    act = new Intent(getActivity(), PatientActivity.class);
                    startActivity(act);

                    break;
                }

            case R.id.opret:
                Toast.makeText(getActivity(), "Ikke implementeret endnu", Toast.LENGTH_LONG);
                break;
            case R.id.glemtLogin:
                Toast.makeText(getActivity(), "Ikke implementeret endnu", Toast.LENGTH_LONG);
                break;
        }
    }

    public static class IkkeImplementeret_frag extends Fragment {
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.ikke_implementeret, container, false);


            return view;
        }
    }

    class HentDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            patient = bc.getPatient("p1");
            jsonString = dc.getDataString(patient, null);

            return jsonString;
        }
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }


}


