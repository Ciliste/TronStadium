package shesh.tron.worker.request;

import shesh.tron.worker.response.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GETRequest extends Request {

    protected String url;

    public GETRequest(String url) {

        this.url = url;
    }

    @Override
    public void execute() {

        try {

            URL url = new URL(this.url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

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
