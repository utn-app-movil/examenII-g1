package Service

import android.content.Context

object deyf_Prefs {
    private const val PREFS_NAME = "deyf_prefs"
    private const val KEY_EMAIL = "deyf_logged_email"

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun saveLoggedEmail(email: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_EMAIL, email)
            .apply()
    }

    fun getLoggedEmail(): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_EMAIL, null)
    }

    fun clear() {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}