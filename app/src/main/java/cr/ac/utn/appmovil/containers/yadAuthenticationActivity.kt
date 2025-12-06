package cr.ac.utn.appmovil.containers // Asegura tu paquete base

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import Controller.yadAuthViewModel
import util.yadSessionManager

class yadAuthenticationActivity : AppCompatActivity() {

    // ... (Inicialización de viewModel y sessionManager) ...
    private lateinit var viewModel: yadAuthViewModel
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var sessionManager: yadSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yad_authentication)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(yadAuthViewModel::class.java)
        sessionManager = yadSessionManager(applicationContext)

        emailInput = findViewById(R.id.yad_input_email) // Mapeo del campo email
        passwordInput = findViewById(R.id.yad_input_password) // Mapeo del campo password
        loginButton = findViewById(R.id.yad_button_login)

        // 3. Listener del Botón (Donde se capturan los datos)
        loginButton.setOnClickListener {

            // 🎯 OBTENCIÓN DE DATOS INGRESADOS:
            val email = emailInput.text.toString().trim() // Captura el texto del email
            val password = passwordInput.text.toString().trim() // Captura el texto del password

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Llama al ViewModel con los datos capturados
                viewModel.authenticateUser(email, password)
            } else {
                Toast.makeText(this, "Debe ingresar email y contraseña.", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Observación del Resultado...
        viewModel.authResult.observe(this) { response ->
            if (response.responseCode == "INFO_FOUND" && response.data != null) {
                navigateToContainerList()
            } else {
                Toast.makeText(this, "Error: ${response.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToContainerList() {
        val intent = Intent(this, yadContainerListActivity::class.java)
        startActivity(intent)
        finish()
    }
}