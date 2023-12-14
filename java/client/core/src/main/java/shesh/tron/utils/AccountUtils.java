package shesh.tron.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public final class AccountUtils {

    private AccountUtils() {

    }

    private static FileHandle getTokenFile() {

        return Gdx.files.local("token.txt");
    }

    public static String getToken() {

        if (tokenFileExists()) {

            return getTokenFile().readString();
        }
        else {

            return null;
        }
    }

    public static void setToken(String token) {

        getTokenFile().writeString(token, false);
    }

    private static boolean tokenFileExists() {

        return getTokenFile().exists();
    }
}
