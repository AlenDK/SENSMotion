package e.android.sensmotion.controller.impl;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Period;
import e.android.sensmotion.entities.user.Patient;


public class DataController implements IDataController {

    private String project_key, patient_key, endpoint;
    private Exception error;
    private String period_name;

    private Sensor sensor;
    private Patient patient;

    private IUserController uc;

    private DatabaseReference database;

    public DataController() {

    }

    public String getApiDATA(Patient patient, String date) {
        project_key = patient.getProject_key();
        patient_key = patient.getPatient_key();

        String endpoint;

        if (!Strings.isEmptyOrWhitespace(date)) {
            endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?" +
                    "project_key=" + project_key +
                    "&patient_key=" + patient_key +
                    "&date=" + date);
            System.out.println("den her metoder BURDE funke");
        } else {
            endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?" +
                    "project_key=" + project_key +
                    "&patient_key=" + patient_key +
                    //"&day_count="  + 10);
                    "&date=2018-10-02");
            System.out.println("den her metode funker ikke");
        }

        try {
            URL url = new URL(endpoint);
            URLConnection connection = url.openConnection();

            InputStream stream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveData(String s, Sensor sensor) {
        if (s == null) {
            serviceFailure();
            return;
        }

        try {
            JSONObject data = new JSONObject(s);

            uc = ControllerRegistry.getUserController();

            sensor.populate(data);

            serviceSuccess(sensor.getCurrentPeriod());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serviceSuccess(Period period) {

        System.out.println("serviceSuccess!");
    }

    @Override
    public void serviceFailure() {
        System.out.println("serviceFailure!");
    }

    public int checkPeriode(String periode) {
        if (periode.length() > 2) {
            System.out.println("---\n" + periode + "\n---");
            period_name = "&date=";
            return 1;
        }
        System.out.println("---\nLængden af perioden: " + periode + " er: " + Integer.parseInt(periode) + "\n---");
        period_name = "&day_count=";
        return Integer.parseInt(periode);
    }
    }

