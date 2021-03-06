package e.android.sensmotion.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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


import androidx.annotation.NonNull;
import e.android.sensmotion.R;
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
    private CheckBox rememberUser;
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


        brugernavn = view.findViewById(R.id.brugernavn);
        TextView glemt = (TextView) view.findViewById(R.id.glemtLogin);
        Button login = (Button) view.findViewById(R.id.logIndKnap);
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
                        getFirebasePatient();
                    }
                } else {
                    pressed++;
                    getFirebasePatient();
                }
                break;

            case R.id.glemtLogin:
                Toast.makeText(getActivity(), "Ikke implementeret endnu", Toast.LENGTH_LONG).show();
                break;
        }
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

                        //Save id and name
                        prefsEditor.putString("userID", userID);
                        prefsEditor.putString("name", patient.getName());
                        prefsEditor.apply();
                        prefsEditor.commit();


                        if (mPrefs.getInt("confirmation", 0) == 1) {
                            act = new Intent(getActivity(), PatientActivity.class);
                            getActivity().finish();
                            startActivity(act);
                        } else {

                            final DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            mPrefs.edit().putInt("confirmation", 1).apply();
                                            act = new Intent(getActivity(), PatientActivity.class);
                                            getActivity().finish();
                                            startActivity(act);

                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;

                                        default:
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(R.string.PersData)
                                    .setPositiveButton("Godkend", dialogListener)
                                    .setNegativeButton("Afvis", dialogListener).show();

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Noget gik galt prøv igen...", Toast.LENGTH_LONG);
            }
        });
    }
}


