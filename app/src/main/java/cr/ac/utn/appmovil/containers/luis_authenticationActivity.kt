package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Controller.luis_UserController
import kotlinx.coroutines.launch

class luis_authenticationActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var userController: luis_UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luis_activity_authentication)

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        userController = luis_UserController(this)

        btnLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val username = edtUsername.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        // basic input validation
        if (username.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.error_username_required),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.error_password_required),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // deactivate login button to prevent multiple clicks
        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = userController.login(username, password)

                Toast.makeText(
                    this@luis_authenticationActivity,
                    getString(
                        R.string.welcome_user,
                        response.Data?.Name ?: username
                    ),
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(
                    this@luis_authenticationActivity,
                    luis_containersOptionsActivity::class.java
                )
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Toast.makeText(
                    this@luis_authenticationActivity,
                    getString(
                        R.string.error_login_generic_toast,
                        e.message ?: getString(R.string.error_login_general)
                    ),
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnLogin.isEnabled = true
            }
        }
    }
}
