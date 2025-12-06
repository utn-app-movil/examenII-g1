package util

import android.content.Context
import android.content.SharedPreferences

class yadSessionManager(private val context: Context) {

    private val PREF_NAME = "yad_session_pref"

    private val KEY_USER_EMAIL = "yad_user_email"

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()


    fun saveUserEmail(email: String) {
        editor.putString(KEY_USER_EMAIL, email)

        editor.apply()
    }


    fun getUserEmail(): String? {

        return sharedPref.getString(KEY_USER_EMAIL, null)
    }


    fun isLoggedIn(): Boolean {

        return getUserEmail() != null
    }


    fun logout() {
        editor.clear()
        editor.apply()
    }
}