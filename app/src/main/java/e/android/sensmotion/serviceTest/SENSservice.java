package e.android.sensmotion.serviceTest;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import e.android.sensmotion.controller.interfaces.SENScallback;
import e.android.sensmotion.dataTest.Value;

public class SENSservice {
    private SENScallback callback;
    private String values;
    private Exception error;

    public SENSservice(SENScallback callback) {
        this.callback = callback;
    }

    public String getPeriode() {
        return values;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshPatient(final String PROJECT_KEY, final String PATIENT_KEY, final String DAY_COUNT){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                //String query = String.format("SELECT * FROM ... WHERE patient_key = %s", patient_key);

                // project_key = k5W2uX
                // patient_key = 6rT39u
                // day_count = 7

                String endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?project_key="+ PROJECT_KEY +"&patient_key="+ PATIENT_KEY +"&date="+ DAY_COUNT);

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
                    callback.serviceFailure(error);
                    return;
                }

                try{
                    JSONObject data = new JSONObject(s);


                    System.out.println("----");
                    System.out.println(data.toString(2));
                    System.out.println("----");


                    JSONObject queryResults = data.optJSONObject("values");
                    System.out.println(queryResults);

                    /*
                    if(queryResults == null){
                        System.out.println("fuck mig?");
                        callback.serviceFailure(new LocalPatientException("Ingen patienter fundet for følgende nøgle: " + PATIENT_KEY));
                        return;
                    }
                    */

                    Value value = new Value();
                    value.populate(data);
                    values = value.getValues();

                    /*
                    System.out.println("---");
                    System.out.println(value.toString());
                    System.out.println("---");
                    */

                    callback.serviceSuccess(value);

                } catch (JSONException e) {
                    callback.serviceFailure(e);
                }
            }

        }.execute(PATIENT_KEY);
    }

    //Lokal error exception
    public class LocalPatientException extends Exception{
        public LocalPatientException(String detailMessage) {
            super(detailMessage);
        }
    }
}
