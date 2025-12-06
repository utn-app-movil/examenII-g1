package Caleb.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Caleb.ServiceC.ApiServiceCaleb
import Caleb.modelC.AuthRequest
import Caleb.modelC.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivityCaleb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cr.ac.utn.appmovil.containers.R.layout.activity_login_caleb)

        val etUsername = findViewById<EditText>(cr.ac.utn.appmovil.containers.R.id.etusername)
        val etPassword = findViewById<EditText>(cr.ac.utn.appmovil.containers.R.id.etpassword)
        val btnLogin = findViewById<Button>(cr.ac.utn.appmovil.containers.R.id.btnlogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString()?.trim().orEmpty()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authenticate(username, password)
        }
    }

    private fun authenticate(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiServiceCaleb.api.authenticate(AuthRequest(username, password))
                }

                if (response.isSuccessful) {
                    val body: AuthResponse? = response.body()
                    if (body?.data != null) {
                        // Guardar datos en SharedPreferences para uso posterior en menuCaleb
                        val prefs = getSharedPreferences("caleb_prefs", MODE_PRIVATE)
                        prefs.edit()
                            .putString("user", body.data?.user)
                            .putString("name", body.data?.name)
                            .putString("lastName", body.data?.lastName)
                            .apply()

                        Toast.makeText(this@LoginActivityCaleb, body.message ?: "Login exitoso", Toast.LENGTH_SHORT).show()
                        // Ir a menuCaleb
                        startActivity(Intent(this@LoginActivityCaleb, menuCaleb::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivityCaleb, body?.message ?: "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivityCaleb, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivityCaleb, "Fallo de red: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}