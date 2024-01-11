package shesh.tron.constants;

public final class ServerUrls {

    private static String MAIN_URL;

    private ServerUrls() {

    }

    public static void updateUrls(String baseUrl) {

        MAIN_URL = baseUrl;
    }
}
