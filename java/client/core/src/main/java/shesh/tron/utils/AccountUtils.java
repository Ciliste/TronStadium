package shesh.tron.utils;

import com.badlogic.gdx.Gdx;

public final class AccountUtils {

    private AccountUtils() {

    }

    public static String getToken() {

        if (tokenFileExists()) {

            return Gdx.files.local("token.txt").readString();
        }
        else {

            return null;
        }
    }

    private static boolean tokenFileExists() {

        return Gdx.files.local("token.txt").exists();
    }
}
