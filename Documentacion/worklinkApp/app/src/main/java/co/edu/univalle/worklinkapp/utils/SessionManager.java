package co.edu.univalle.worklinkapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "MiSesion";
    private static final String KEY_IS_LOGGED = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_ROL = "userRol";
    private static final String KEY_TOKEN = "token";

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createSession(String userEmail, String name, String token, String rol) {
        editor.putBoolean(KEY_IS_LOGGED, true);
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_ROL, rol);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return prefs.getBoolean(KEY_IS_LOGGED, false);
    }

    public String getUserEmail() { return prefs.getString(KEY_USER_EMAIL, null); }
    public String getUserName() { return prefs.getString(KEY_USER_NAME, null); }
    public String getUserRol() { return prefs.getString(KEY_USER_ROL, null); }
    public String getToken() { return prefs.getString(KEY_TOKEN, null); }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
