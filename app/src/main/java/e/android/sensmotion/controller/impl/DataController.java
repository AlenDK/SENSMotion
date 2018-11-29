package e.android.sensmotion.controller.impl;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.Sensor;
import e.android.sensmotion.entities.Value;
import e.android.sensmotion.entities.Values;
import e.android.sensmotion.entities.bruger.Patient;



public class DataController implements IDataController {

    private String project_key, patient_key;
    private Exception error;
    private String period_name;
    private Sensor sensor;

    public DataController() {

        }

    @SuppressLint("StaticFieldLeak")
    public void refreshPatient(final Patient patient, Sensor sensor, final String DAY_COUNT){

        this.sensor = sensor;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {

                project_key = patient.getProject_key();
                patient_key = patient.getPatient_key();

                //String query = String.format("SELECT * FROM ... WHERE patient_key = %s", patient_key);

                // project_key = k5W2uX
                // patient_key = 6rT39u
                // day_count = 7

                checkPeriode(DAY_COUNT);
                String endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?" +
                        "project_key="+ project_key +
                        "&patient_key="+ patient_key +
                        period_name + DAY_COUNT);

                try{
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();

                    InputStream stream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    /*
                    System.out.println();
                    System.out.println();
                    System.out.println("----");
                    System.out.println(result.toString());
                    System.out.println("----");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    */

                    return result.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s == null && error != null){
                    serviceFailure(error);
                    return;
                }

                try{
                    JSONObject data = new JSONObject(s);


                    System.out.println("----");
                    System.out.println(data.toString(2));
                    System.out.println("----");


                    Value value = new Value(checkPeriode(DAY_COUNT));
                    value.populate(data);



                    serviceSuccess(value);

                } catch (JSONException e) {
                    serviceFailure(e);
                }
            }

        }.execute(patient_key);
    }

    @Override
    public void serviceSuccess(Value value) {

        sensor.setCurrentValue(value);

        System.out.println("serviceSuccess!");
    }

    @Override
    public void serviceFailure(Exception exception) {
        System.out.println("serviceFailure!");
    }


    public int checkPeriode(String periode){
        if(periode.length()>2){
            System.out.println("---\n" +periode+ "\n---");
            period_name = "&date=";
            return 1;
        }
        System.out.println("---\nLængden af perioden: " +periode+ " er: " +Integer.parseInt(periode)+ "\n---");
        period_name = "&day_count=";
        return Integer.parseInt(periode);
    }
}
