package e.android.sensmotion.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
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
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;


public class Login_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    private EditText brugernavn;
    private CheckBox dataHandling, rememberUser;
    private Intent act;
    private IDataController dc;
    private IUserController uc;
    private String jsonString;
    private IFirebaseController fbc;
    private boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    SharedPreferences mPrefs;

    private Patient patient;
    private Sensor sensor;

    private int pressed;
    private boolean remember;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        dc = ControllerRegistry.getDataController();
        uc = ControllerRegistry.getUserController();
        fbc = ControllerRegistry.getFirebaseController();

        SharedPreferences.Editor prefsEditor1 = mPrefs.edit();
        prefsEditor1.clear();



        if(mPrefs.getBoolean("remember", false) == true){
            userID = mPrefs.getString("userID", "p1");
            fbc.getPatientFirebase(userID);
            patient = uc.getPatient(userID);

            System.out.println("FIIIIIIIIIIIIIIIIIIIIIIIIIIIIISSE");

            //act = new Intent(getActivity(), PatientActivity.class);
            //startActivity(act);
        }

        if (!EMULATOR) {

            Fabric.with(getActivity(), new Crashlytics());
        }
/*
        forceCrash(view);
*/

        brugernavn = view.findViewById(R.id.brugernavn);
        TextView opret = (TextView) view.findViewById(R.id.opret);
        TextView glemt = (TextView) view.findViewById(R.id.glemtLogin);
        Button login = (Button) view.findViewById(R.id.logIndKnap);
        dataHandling = (CheckBox) view.findViewById(R.id.dataHandling);
        rememberUser = (CheckBox) view.findViewById(R.id.HuskBruger);

        pressed = 0;
        login.setOnClickListener(this);
        opret.setOnClickListener(this);
        glemt.setOnClickListener(this);

        return view;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {
        userID = brugernavn.getText().toString();
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

            switch (view.getId()) {
                case R.id.logIndKnap:
                    if (pressed == 0) {
                        if (userID.equals("admin")) {
                            act = new Intent(getActivity(), Terapuet_activity.class);
                            startActivity(act);
                            pressed++;
                            getActivity().finish();
                        } else {

                            //Agree to consent
                            if (!dataHandling.isChecked()) {
                                Toast.makeText(getActivity(), "Du skal acceptere SENSmotion\'s vilkår " +
                                        "for håndtering af personfølsomme data", Toast.LENGTH_LONG).show();
                                break;
                            }

                            //Remember User
                            if (rememberUser.isChecked()) {
                                remember = true;
                                prefsEditor.putBoolean("remember", remember);
                            }

                            //Get data
                            try {
                                String hentDataResult = new HentDataAsyncTask().execute().get();
                                sensor = patient.getSensor("s1");
                                dc.saveData(hentDataResult, sensor);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            prefsEditor.putString("userID", userID);
                            prefsEditor.apply();
                            prefsEditor.commit();

                            Patient p1 = ControllerRegistry.getUserController().getPatient(userID);
                            fbc.newPatient(p1);

                            act = new Intent(getActivity(), PatientActivity.class);
                            startActivity(act);
                            pressed++;
                            getActivity().finish();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Henter data", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.opret:
                    Toast.makeText(getActivity(), "Ikke implementeret endnu", Toast.LENGTH_LONG);
                    break;
                case R.id.glemtLogin:
                    Toast.makeText(getActivity(), "Ikke implementeret endnu", Toast.LENGTH_LONG);
                    break;
            }
    }

    class HentDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            patient = uc.getPatient(userID);
            jsonString = dc.getApiDATA(patient, null);

            return jsonString;
        }
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }


}


