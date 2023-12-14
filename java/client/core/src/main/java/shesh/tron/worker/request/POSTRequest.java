package shesh.tron.worker.request;

import shesh.tron.worker.response.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class POSTRequest extends GETRequest {

    protected String postData;

    public POSTRequest(String url, String postData) {

        super(url);
        this.postData = postData;
    }

    @Override
    public void execute() {

        try {

            URL url = new URL(this.url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {

                writer.write(postData);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {

                    response.append(line);
                }

                notifyConsumers(new Response(response.toString()));
            }
        }
        catch (Exception e) {

            notifyErrorConsumers(e);
        }
    }
}
