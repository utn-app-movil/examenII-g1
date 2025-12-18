package cr.ac.utn.appmovil.containers

import Service.bray_APIService
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class bray_LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bray_activity_login)

        emailEditText = findViewById(R.id.edit_text_email)
        passwordEditText = findViewById(R.id.edit_text_password)
        loginButton = findViewById(R.id.button_login)

        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, getString(R.string.message_authenticating), Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = bray_LoginRequest(email, password)
                val response = bray_APIService.api.login(request)
                
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val loginResponse = response.body()!!
                        if (loginResponse.responseCode == "200" || 
                            loginResponse.responseCode == "INFO_FOUND" || 
                            loginResponse.responseCode == "SUCESSFUL" || 
                            loginResponse.responseCode == "SUCCESSFUL") {
                            
                            Toast.makeText(this@bray_LoginActivity, String.format(getString(R.string.login_success), loginResponse.data?.name), Toast.LENGTH_LONG).show()

                            val intent = Intent(this@bray_LoginActivity, bray_ContainerListActivity::class.java)
                            intent.putExtra("USER_EMAIL", email)
                            startActivity(intent)
                            finish()
                        } else {

                             Toast.makeText(this@bray_LoginActivity, "${loginResponse.message} (Code: ${loginResponse.responseCode})", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@bray_LoginActivity, String.format(getString(R.string.login_failed_code), response.code()), Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@bray_LoginActivity, String.format(getString(R.string.error_connection), e.message), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}