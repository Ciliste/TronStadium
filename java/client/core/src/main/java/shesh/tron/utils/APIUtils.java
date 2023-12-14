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
    private static final String LOGIN_URL = AUTH_URL + "/login.php";
    private static final String VALIDATE_TOKEN_URL = AUTH_URL + "/isValidToken.php";

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

    public static String login(String username, String password) throws Exception {

        URL url = new URL(LOGIN_URL + "?username=" + username + "&password=" + password);
        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Enable input/output streams
        connection.setDoOutput(true);

        // Read the response from the server
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);
            }

            // Close the connection
            connection.disconnect();

            System.out.println(response.toString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.toString());

            return jsonNode.get("token").asText();
        }
    }

    public static boolean isValidToken(String token) throws Exception {

        URL url = new URL(VALIDATE_TOKEN_URL + "?token=" + token);
        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Enable input/output streams
        connection.setDoOutput(true);

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

            return jsonNode.get("valid").asBoolean();
        }
    }
}
