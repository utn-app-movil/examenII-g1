package cr.ac.utn.appmovil.containers

import Controller.lau_AuthController
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class lau_login : AppCompatActivity() {
    private lateinit var controller: lau_AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lau_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        controller = lau_AuthController(this)

        val btnLogin = findViewById<Button>(R.id.lau_btn_login)
        val txtUser = findViewById<EditText>(R.id.lau_username)
        val txtPass = findViewById<EditText>(R.id.lau_password)

        btnLogin.setOnClickListener {
            val user = txtUser.text.toString()
            val pass = txtPass.text.toString()

            login(user, pass)
        }
    }

    private fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = controller.authenticate(username, password)

                if (response == null) {
                    Toast.makeText(this@lau_login, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@lau_login, "Login exitoso", Toast.LENGTH_SHORT).show()

                    // Navegar a otra pantalla
                    startActivity(Intent(this@lau_login, lau_main::class.java))
                    finish()
                }

            } catch (e: Exception) {
                Toast.makeText(this@lau_login, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}