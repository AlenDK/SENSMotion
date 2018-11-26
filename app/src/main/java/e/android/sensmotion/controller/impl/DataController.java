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
import e.android.sensmotion.entities.Value;



public class DataController implements IDataController {

    private String values;
    private Exception error;

    public DataController(){
        //tom contstructor
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshPatient(final String PROJECT_KEY, final String PATIENT_KEY, final String DAY_COUNT){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                /*
                String patientKey = "6rT39u";
                String projectKey = "k5W2uX";
                int dayCount = 7;
                */

                String endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?"
                        +"project_key="+PROJECT_KEY
                        +"&patient_key="+PATIENT_KEY
                        +"&day_count="+DAY_COUNT);

                try{
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();

                    InputStream stream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder restult = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null){
                        restult.append(line);
                    }

                    return restult.toString();

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

                    JSONObject queryResults = data.optJSONObject("values");

                    /*
                    int count = queryResults.optInt("count");
                    if(count == 0){
                        serviceFailure(new LocalPatientException("Ingen patienter fundet for følgende nøgle: " + PATIENT_KEY));
                        return;
                    }
                    */

                    Value value = new Value();
                    value.populate(data);
                    values = value.getValues();

                    serviceSuccess(value);

                } catch (JSONException e) {
                    serviceFailure(e);
                }
            }

        }.execute(PATIENT_KEY);
    }

    @Override
    public void serviceSuccess(Value value) {

    }

    @Override
    public void serviceFailure(Exception exception) {

    }
}
