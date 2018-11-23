package e.android.sensmotion.connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connector {

    public void connectURL(String urlInput)  throws IOException {
        URL url = new URL(urlInput);
        String readline = null;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("", "");
    }
}
