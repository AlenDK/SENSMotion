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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login_frag extends android.support.v4.app.Fragment implements View.OnClickListener {

    private EditText brugernavn;
    private CheckBox dataHandling, rememberUser;
    private Intent act;
    private boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor prefsEditor;
    private DatabaseReference database;

    public Patient patient;

    private int pressed;
    private boolean remember;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefsEditor = mPrefs.edit();


        if (!EMULATOR) {

            Fabric.with(getActivity(), new Crashlytics());
        }
/*
        forceCrash(view);
*/

        brugernavn = view.findViewById(R.id.brugernavn);
        TextView glemt = (TextView) view.findViewById(R.id.glemtLogin);
        Button login = (Button) view.findViewById(R.id.logIndKnap);
        dataHandling = (CheckBox) view.findViewById(R.id.dataHandling);
        rememberUser = (CheckBox) view.findViewById(R.id.HuskBruger);

        pressed = 0;
        login.setOnClickListener(this);
        glemt.setOnClickListener(this);

        return view;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {
        userID = brugernavn.getText().toString();

            switch (view.getId()) {
                case R.id.logIndKnap:
                    if (pressed == 0) {
                        if (userID.equals("admin")) {
                            act = new Intent(getActivity(), Terapuet_activity.class);
                            pressed++;
                            getActivity().finish();
                            startActivity(act);
                        } else {

                            //Agree to consent
                            if (!dataHandling.isChecked()) {
                                Toast.makeText(getActivity(), "Du skal acceptere SENSmotion\'s vilkår " +
                                        "for håndtering af personfølsomme data", Toast.LENGTH_LONG).show();
                                break;
                            }
                            getFirebasePatient();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Henter data", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.glemtLogin:
                    Toast.makeText(getActivity(), "Ikke implementeret endnu", Toast.LENGTH_LONG);
                    break;
            }
    }


    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    private void getFirebasePatient() {
        database = FirebaseDatabase.getInstance().getReference("Patients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {
                        patient = snapshot.getValue(Patient.class);

                        //Remember User
                        if (rememberUser.isChecked()) {
                            remember = true;
                            prefsEditor.putBoolean("remember", remember);
                        }

                        //Save id and possibly remember state
                        prefsEditor.putString("userID", userID);
                        prefsEditor.apply();
                        prefsEditor.commit();

                        act = new Intent(getActivity(), PatientActivity.class);
                        getActivity().finish();
                        startActivity(act);
                    }
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
                Toast.makeText(getActivity(), "Noget gik galt prøv igen...", Toast.LENGTH_LONG);
            }
        });
    }
}


