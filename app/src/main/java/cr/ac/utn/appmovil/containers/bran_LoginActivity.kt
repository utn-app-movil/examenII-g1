package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cr.ac.utn.appmovil.containers.models.BranLoginRequest
import kotlinx.coroutines.launch

class BranLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bran_activity_login)

        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = BranLoginRequest(username, password)
                loginUser(loginRequest)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(loginRequest: BranLoginRequest) {
        lifecycleScope.launch {
            try {
                val response = bran_RetrofitClient.apiService.login(loginRequest)

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    if (loginResponse.responseCode == "INFO_FOUND") {

                        // Guarda el email del usuario logueado
                        val prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                        prefs.edit().putString("USER_EMAIL", loginRequest.username).apply()

                        Toast.makeText(this@BranLoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@BranLoginActivity, bran_MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this@BranLoginActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@BranLoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@BranLoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
