package shesh.tron.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class AppDataUtils {

    private static final String FILE_NAME = "TronStatiumAppData";
    private static final String ENCRYPTION_KEY = "blablabla";

    private static final char SEPARATOR = '$';

    private static Map<String, String> dataMap = new HashMap<>();

    private AppDataUtils() {

    }

    public static String get(String key) {

        return dataMap.get(key);
    }

    public static void set(String key, String value) {

        dataMap.put(key, value);
        updateFile();
    }

    private static void updateFile() {

        String data = dataMapToString();

        String base64EncodedData = Base64.getEncoder().encodeToString(data.getBytes());

        getFileHandle().writeString(base64EncodedData, false);
    }

    private static String dataMapToString() {

        Iterable<Map.Entry<String, String>> entries = dataMap.entrySet();
        String separator;
        int separatorLength = 0;
        do {

            separator = generateSeparator(++separatorLength);
        }
        while (doEntriesContains(entries, separator));

        StringBuilder dataMapSb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : entries) {

            if (first) {

                first = false;
            }
            else {

                dataMapSb.append(separator);
            }
            dataMapSb.append(entry.getKey());
            dataMapSb.append(separator);
            dataMapSb.append(entry.getValue());
        }

        return separator + SEPARATOR + dataMapSb.toString();
    }

    private static boolean doEntriesContains(Iterable<Map.Entry<String, String>> entries, String value) {

        for (Map.Entry<String, String> entry : entries) {

            if (entry.getKey().contains(value) || entry.getValue().contains(value)) {

                return true;
            }
        }

        return false;
    }

    private static String generateSeparator(int length) {

        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {

            int index = (int) (Math.random() * CHARS.length());
            stringBuilder.append(CHARS.charAt(index));
        }

        return stringBuilder.toString();
    }

    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);

        return keyGenerator.generateKey();
    }

    private static String encrypt(String data, SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedData = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    private static String decrypt(String input, SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(input));

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static void load() {

        if (getFileHandle().exists()) {

            String base64EncodedData = getFileHandle().readString();

            String data = new String(Base64.getDecoder().decode(base64EncodedData));

            try {

                dataMap = stringToDataMap(data);
            }
            catch (Exception e) {

                dataMap = new HashMap<>();
            }
        }
    }

    private static Map<String, String> stringToDataMap(String data) {

        String separator = data.substring(0, data.indexOf(SEPARATOR));
        String dataMapString = data.substring(data.indexOf(SEPARATOR) + 1);

        Map<String, String> dataMap = new HashMap<>();

        String[] dataMapEntries = dataMapString.split(separator);
        boolean odd = true;
        String key = null;
        for (String dataMapEntry : dataMapEntries) {

            if (odd) {

                key = dataMapEntry;
            }
            else {

                dataMap.put(key, dataMapEntry);
            }

            odd = !odd;
        }

        return dataMap;
    }

    private static FileHandle getFileHandle() {

        return Gdx.files.local(FILE_NAME);
    }

    public static void main(String[] args) {

        try {

            System.out.println("AppDataUtils TEST");

            String data = dataMap.toString();
            System.out.println("data = " + data);

            SecretKey secretKey = generateSecretKey();
            System.out.println("secretKey = " + secretKey);

            String encryptedData = encrypt(data, secretKey);
            System.out.println("encryptedData = " + encryptedData);

            String decryptedData = decrypt(encryptedData, secretKey);
            System.out.println("decryptedData = " + decryptedData);

            System.out.println("AppDataUtils TEST END");
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
