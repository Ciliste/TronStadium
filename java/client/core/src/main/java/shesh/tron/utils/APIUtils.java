package shesh.tron.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import shesh.tron.worker.request.GETRequest;
import shesh.tron.worker.request.POSTRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class APIUtils {

    private static final String API_URL = "http://tronstadium.ciliste.games/api";

    private static final String AUTH_URL = API_URL + "/auth";

    private static final String REGISTER_URL = AUTH_URL + "/register.php";
    private static final String LOGIN_URL = AUTH_URL + "/login.php";
    private static final String VALIDATE_TOKEN_URL = AUTH_URL + "/isValidToken.php";

    private static final String PROFILE_URL = API_URL + "/profile";

    private static final String INFO_PROFILE_URL = PROFILE_URL + "/info.php";

    private static final String INFO_API_URL = API_URL + "/info";

    private static final String SERVER_INFO_URL = INFO_API_URL + "/server.php";

    private APIUtils() {

    }

    public static POSTRequest register(String username, String password) {

        return new POSTRequest(REGISTER_URL, "username=" + username + "&password=" + password);
    }

    public static GETRequest login(String username, String password) {

        return new GETRequest(LOGIN_URL + "?username=" + username + "&password=" + password);
    }

    public static GETRequest getUserInfo(String token) {

        return new GETRequest(INFO_PROFILE_URL + "?token=" + token);
    }

    public static GETRequest getServerInfo() {

        return new GETRequest(SERVER_INFO_URL);
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
