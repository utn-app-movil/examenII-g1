package cr.ac.utn.appmovil.containers

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import Service.pau_Repository
import android.content.Intent
import cr.ac.utn.appmovil.containers.pau_view.pau_ContainerActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.pau_LoginRequest
import util.util

const val PAU_PREFS_NAME = "pau_AppPrefs"
const val PAU_LOGGED_USER_EMAIL_KEY = "pau_loggedUserEmail"

class pau_LoginActivity : AppCompatActivity() {

    private lateinit var pau_sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pau_login)

        pau_sharedPrefs = getSharedPreferences(PAU_PREFS_NAME, Context.MODE_PRIVATE)

        val pau_email = findViewById<EditText>(R.id.pau_email_login)
        val pau_password = findViewById<EditText>(R.id.pau_password_login)
        val pau_login_button = findViewById<Button>(R.id.pau_login_button)
        val pau_progress_bar = findViewById<ProgressBar>(R.id.pau_progress_bar)

        pau_login_button.setOnClickListener {
            val email = pau_email.text.toString().trim()
            val password = pau_password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pau_authenticate(email, password, pau_progress_bar)
        }
    }

    private fun pau_authenticate(email: String, password: String, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = pau_Repository.pau_login(pau_LoginRequest(email, password))
                withContext(Dispatchers.Main) {
                    if (response.pau_responseCode == "200" && response.data != null) {
                        pau_sharedPrefs.edit()
                            .putString(PAU_LOGGED_USER_EMAIL_KEY, response.data.pau_email)
                            .apply()

                        val intent =
                            Intent(this@pau_LoginActivity, pau_ContainerActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        util.showDialogCondition(
                            this@pau_LoginActivity,
                            "Error",
                            response.pau_message ?: "Credenciales incorrectas",
                            "OK", "Cancelar", {}, {}
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    util.showDialogCondition(
                        this@pau_LoginActivity,
                        "Error de red",
                        e.message ?: "No se pudo conectar",
                        "OK", "Cancelar", {}, {}
                    )
                }
            } finally {
                withContext(Dispatchers.Main) { progressBar.visibility = View.GONE }
            }
        }
    }
}