package shesh.tron.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class APIUtils {

    private static final String API_URL = "http://tronstadium.ciliste.games";

    private static final String AUTH_URL = API_URL + "/auth";

    private static final String REGISTER_URL = AUTH_URL + "/register.php";

    private APIUtils() {

    }

    public static String register(String username, String password) throws Exception {

        String postData = "username=" + username + "&password=" + password;

        URL url = new URL(REGISTER_URL);
        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");

        // Enable input/output streams
        connection.setDoOutput(true);

        // Write data to the output stream
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {

            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            wr.write(postDataBytes);
        }

        // Read the response from the server
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);
            }

            // Close the connection
            connection.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.toString());

            return jsonNode.get("token").asText();
        }
    }
}
