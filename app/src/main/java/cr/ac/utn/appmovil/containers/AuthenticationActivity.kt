package cr.ac.utn.appmovil.containers

import Service.sam_APIService
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.sam_LoginRequest

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPass: EditText
    private lateinit var btnAuth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authentication)

        txtUser = findViewById(R.id.txtUser)
        txtPass = findViewById(R.id.txtPass)
        btnAuth = findViewById(R.id.btnAuth)

        btnAuth.setOnClickListener {
            authenticate()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sam_auth_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Checks credentials and log in
    private fun authenticate() {
        val user = txtUser.text.toString().trim()
        val password = txtPass.text.toString().trim()

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val request = sam_LoginRequest(user, password)

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    sam_APIService.api.authUser(request)
                }

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.responseCode == "INFO_FOUND" && body.data != null) {

                        val userEmail = body.data.email
                        val intent = Intent(this@AuthenticationActivity, sam_ContainerListActivity::class.java)
                        intent.putExtra("EXTRA_TECH_EMAIL", userEmail)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@AuthenticationActivity,
                            body?.message ?: "Credenciales inválidas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@AuthenticationActivity,
                        "Error HTTP: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@AuthenticationActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
