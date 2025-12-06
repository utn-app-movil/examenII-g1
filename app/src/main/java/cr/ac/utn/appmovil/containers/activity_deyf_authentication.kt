package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import cr.ac.utn.appmovil.containers.model.deyf_AuthRequest
import cr.ac.utn.appmovil.containers.model.deyf_AuthResponse
import Service.deyf_Prefs
import Service.deyf_RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class activity_deyf_authentication : AppCompatActivity() {

    private lateinit var deyf_etEmail: TextInputEditText
    private lateinit var deyf_etPassword: TextInputEditText
    private lateinit var deyf_btnLogin: MaterialButton

    private val deyf_api by lazy { deyf_RetrofitClient.deyf_apiService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.deyf_authentication)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        deyf_Prefs.init(this)

        if (deyf_Prefs.getLoggedEmail() != null) {
            Toast.makeText(this, "Ya iniciaste sesión como ${deyf_Prefs.getLoggedEmail()}", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        deyf_initViews()
        deyf_setupClickListeners()
    }

    private fun deyf_initViews() {
        deyf_etEmail = findViewById(R.id.deyf_emailEditText)
        deyf_etPassword = findViewById(R.id.deyf_passwordEditText)
        deyf_btnLogin = findViewById(R.id.deyf_loginButton)
    }

    private fun deyf_setupClickListeners() {
        deyf_btnLogin.setOnClickListener {
            val email = deyf_etEmail.text.toString().trim()
            val password = deyf_etPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    deyf_etEmail.error = "Ingresa tu correo"
                    deyf_etEmail.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    deyf_etPassword.error = "Ingresa tu contraseña"
                    deyf_etPassword.requestFocus()
                    return@setOnClickListener
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    deyf_etEmail.error = "Correo inválido"
                    deyf_etEmail.requestFocus()
                    return@setOnClickListener
                }
            }

            deyf_btnLogin.isEnabled = false
            deyf_btnLogin.text = "Iniciando sesión..."

            deyf_login(email, password)
        }
    }

    private fun deyf_login(email: String, password: String) {
        val request = deyf_AuthRequest(email, password)

        deyf_api.deyf_login(request).enqueue(object : Callback<deyf_AuthResponse> {
            override fun onResponse(call: Call<deyf_AuthResponse>, response: Response<deyf_AuthResponse>) {
                deyf_btnLogin.isEnabled = true
                deyf_btnLogin.text = "Iniciar Sesión"

                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    deyf_Prefs.saveLoggedEmail(user.email)

                    Toast.makeText(
                        this@activity_deyf_authentication,
                        "¡Bienvenido ${user.name ?: user.email}!",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    Toast.makeText(this@activity_deyf_authentication, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<deyf_AuthResponse>, t: Throwable) {
                deyf_btnLogin.isEnabled = true
                deyf_btnLogin.text = "Iniciar Sesión"
                Toast.makeText(this@activity_deyf_authentication, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}