package cr.ac.utn.appmovil.containers

import Service.ricar_APIService
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import model.ricar_AuthRequest
import java.io.IOException

class ricar_authentication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ricar_authentication)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val authRequest = ricar_AuthRequest(username, password)
                        val authResponse = ricar_APIService.api.authenticateUser(authRequest)

                        if (authResponse.isSuccessful && authResponse.body()?.responseCode == "INFO_FOUND") {
                            val user = authResponse.body()!!.data
                            fetchTechnicianEmailAndProceed(user.user)
                        } else {
                            Toast.makeText(this@ricar_authentication, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: IOException) {
                        Toast.makeText(this@ricar_authentication, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@ricar_authentication, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchTechnicianEmailAndProceed(userId: String) {
        lifecycleScope.launch {
            try {
                val techniciansResponse = ricar_APIService.api.getTechnicians()
                if (techniciansResponse.isSuccessful && techniciansResponse.body() != null) {
                    val technician = techniciansResponse.body()!!.data.find { it.id == userId }
                    if (technician != null) {
                        val intent = Intent(this@ricar_authentication, ricar_ContainersActivity::class.java)
                        intent.putExtra("TECHNICIAN_EMAIL", technician.email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@ricar_authentication, "Technician email not found for user", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ricar_authentication, "Failed to fetch technicians", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this@ricar_authentication, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}