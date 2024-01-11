package shesh.tron.server.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public final class AuthUtils {

    private static final int DEFAULT_TOKEN_LENGTH = 20;
    private static final int DEFAULT_SALT_LENGTH = 20;

    private static final String SALT_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String TOKEN_CHARS = SALT_CHARS + "?$%&@#^*()!~`-=_+[]{}|;:,./<>";

    private static final int DEFAULT_HASH_ITERATIONS = 12;

    private static final int PASSWORD_HASH_MAX_LENGTH = 60;

    private static final MessageDigest HASH_ALGORITHM;
    static {

            try {

                HASH_ALGORITHM = MessageDigest.getInstance("SHA-256");
            }
            catch (NoSuchAlgorithmException e) {

                throw new RuntimeException(e);
            }
    }

    private AuthUtils() {

    }

    public static String generateToken() {

        return generateToken(DEFAULT_TOKEN_LENGTH);
    }

    public static String generateToken(int length) {

        StringBuilder sb = new StringBuilder();

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < length; i++) {

            int randomIndex = random.nextInt(TOKEN_CHARS.length());
            sb.append(TOKEN_CHARS.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static String hash(String password) {

        return hash(password, DEFAULT_HASH_ITERATIONS);
    }

    public static String hash(String password, int iterations) {

        String salt = generateSalt();

        return hash(password, salt, iterations);
    }

    public static String hash(String password, String salt, int iterations) {

        String hash = password;

        for (int i = 0; i < iterations; i++) {

            hash = hashIteration(hash, salt, HASH_ALGORITHM);
        }

        hash = iterations + "." + salt + "." + hash;

        return hash.substring(0, Math.min(hash.length(), PASSWORD_HASH_MAX_LENGTH));
    }

    private static String hashIteration(String password, String salt, MessageDigest md) {

        md.update(password.getBytes());
        md.update(salt.getBytes());

        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {

            sb.append(Integer.toHexString(b & 0xff));
        }

        return sb.toString();
    }

    public static boolean verify(String password, String hash) {

        String[] parts = hash.split("\\.");

        if (parts.length != 3) {

            return false;
        }

        int iterations = Integer.parseInt(parts[0]);
        String salt = parts[1];

        String hash1 = hash(password, salt, iterations);

        return hash.equals(hash1);
    }

    private static String generateSalt() {

        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    private static String generateSalt(int length) {

        StringBuilder sb = new StringBuilder();

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < length; i++) {

            int randomIndex = random.nextInt(SALT_CHARS.length());
            sb.append(SALT_CHARS.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        String password = "shesh";

        System.out.println("password = " + password);

        String hash = hash(password);

        System.out.println("hash = " + hash);

        System.out.println("verify(password, hash) = " + verify(password, hash));
    }
}
