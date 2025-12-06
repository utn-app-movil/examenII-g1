package cr.ac.utn.appmovil.containers.kevinv

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.containers.R
import kotlinx.coroutines.*
import util.util

class kevinv_authenticationActivity : AppCompatActivity() {

    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kevinv_authentication)

        txtUsername = findViewById(R.id.kevinv_txtUsername)
        txtPassword = findViewById(R.id.kevinv_txtPassword)
        btnLogin = findViewById(R.id.kevinv_btnLogin)
        progress = findViewById(R.id.kevinv_progressAuth)

        btnLogin.setOnClickListener {
            kevinv_doLogin()
        }
    }

    private fun kevinv_doLogin() {
        val username = txtUsername.text.toString().trim()
        val password = txtPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password are required", Toast.LENGTH_LONG).show()
            return
        }

        progress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = kevinv_ApiClient.api.kevinv_authenticate(
                    kevinv_AuthRequest(username, password)
                )

                withContext(Dispatchers.Main) {
                    progress.visibility = View.GONE

                    if (response.responseCode == "INFO_FOUND" || response.responseCode == "SUCESSFUL") {
                        val userData = response.data!!

                        kevinv_SessionManager.loggedUserId = userData.user
                        kevinv_SessionManager.loggedUserName = userData.name
                        kevinv_SessionManager.loggedUserLastName = userData.lastName
                        kevinv_SessionManager.loggedUserEmail = userData.email

                        Toast.makeText(
                            this@kevinv_authenticationActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()

                        // go to main
                        util.openActivity(
                            this@kevinv_authenticationActivity,
                            kevinv_mainActivity::class.java
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            this@kevinv_authenticationActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@kevinv_authenticationActivity,
                        ex.message ?: "Unexpected error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
