package e.android.sensmotion.serviceTest;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import e.android.sensmotion.dataTest.JSONPopulator;
import e.android.sensmotion.dataTest.Values;

public class SENSservice {
    private SENScallback callback;
    private String patient;
    private Exception error;

    public SENSservice(SENScallback callback) {
        this.callback = callback;
    }

    public String getPatient() {
        return patient;
    }

    public void refreshPatient(final String patient_key){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String query = String.format("SELECT * FROM ... WHERE patient_key = %s", patient_key);

                String endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?project_key=k5W2uX&patient_key=6rT39u&day_count=7", Uri.encode(query));

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
                    error.printStackTrace();
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
                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");
                    if(count == 0){
                        callback.serviceFailure(new LocalPatientException("Ingen patienter fundet for følgende nøgle: " + patient_key));
                        return;
                    }

                    Values values = new Values();
                    values.populate(queryResults.optJSONObject("value").optJSONObject("values"));

                    callback.serviceSuccess(values);

                } catch (JSONException e) {
                    callback.serviceFailure(e);
                }
            }

        }.execute(patient_key);
    }

    //Lokal error
    public class LocalPatientException extends Exception{
        public LocalPatientException(String detailMessage) {
            super(detailMessage);
        }
    }
}
